package com.wavesenterprise.app.app;

import com.wavesenterprise.app.api.IContract;
import com.wavesenterprise.app.domain.*;
import com.wavesenterprise.app.features.HashComponent;
import com.wavesenterprise.sdk.contract.api.annotation.ContractHandler;
import com.wavesenterprise.sdk.contract.api.domain.ContractCall;
import com.wavesenterprise.sdk.contract.api.state.ContractState;
import com.wavesenterprise.sdk.contract.api.state.TypeReference;
import com.wavesenterprise.sdk.contract.api.state.mapping.*;

import java.time.LocalDateTime;
import java.util.*;

import static com.wavesenterprise.app.api.IContract.Keys.*;

@ContractHandler
public class Contract implements IContract {

    private final ContractState contractState;
    private final ContractCall call;
    private final Mapping<User> userMapping;
    private final Mapping<List<Product>> usersProductMapping;
    private final Mapping<List<OrderProduction>> orderProductionMapping;
    private final Mapping<List<User>> newUsersMapping;
    private final Mapping<Company> companyMapping;

    List<Product> productList = new ArrayList<>();
    List<OrderProduction> orderList = new ArrayList<>();
    List<User> newUserList = new ArrayList<>();


    public Contract(ContractState contractState, ContractCall call) {
        this.contractState = contractState;
        this.call = call;
        this.userMapping = contractState.getMapping(User.class, USER_MAPPING);
        this.usersProductMapping = contractState.getMapping(new TypeReference<List<Product>>() {
        }, USER_PRODUCT_MAPPING);
        this.orderProductionMapping = contractState.getMapping(new TypeReference<>() {
        }, ORDER_PRODUCTION);
        this.newUsersMapping = contractState.getMapping(new TypeReference<List<User>>() {
        }, NEW_USERS);
        this.companyMapping = contractState.getMapping(Company.class, COMPANY_MAPPING);
    }

    @Override
    public void init() {
        contractState.put(CONTRACT_CREATOR, call.getCaller());
        this.newUsersMapping.put("USERS", this.newUserList);
        addUser(new User("admin", "admin", "admin", false, null, null, null, 0, null));
    }

    @Override
    public void approveCart(String company, int id, boolean status, int min, int max, String distributors, String sender) {
        this.userMapping.tryGet(sender).ifPresent(owner ->
                {
                    if (!Objects.equals(owner.getRole(), ADMIN_ROLE)) {
                        if (status) {
                            this.companyMapping.tryGet(company).ifPresent(elCompany -> {
                                elCompany.getCompanyShop().get(id).setMaxCount(max);
                                elCompany.getCompanyShop().get(id).setMinCount(min);
                                elCompany.getCompanyShop().get(id).setStatus(STATUS_ACCEPTED);
                                String[] distributorsArray = distributors.split(",");
                                for (String user : distributorsArray) {
                                    this.userMapping.tryGet(user).ifPresent(userMap -> {
                                        if (HashComponent.hasCommonElement(userMap.getSupplyRegions(), elCompany.getCompanyShop().get(id).getRegions())) {
                                            elCompany.getCompanyShop().get(id).addDistributors(user);
                                            this.companyMapping.put(company, elCompany);
                                        }
                                    });
                                }
                            });
                        }
                    } else {
                        throw new IllegalStateException("Вы не администратор");
                    }
                }
        );
    }

    @Override
    public void createShopCart(Product product, String regions, String sender) {
        this.userMapping.tryGet(sender).ifPresent(user -> {
            this.companyMapping.tryGet(user.getCompanyName()).ifPresent(company -> {
                        product.setRegions(Arrays.asList(regions.split(",")));
                        company.addCompanyShop(product);
                        this.companyMapping.put(user.getCompanyName(), company);
                    }
            );
        });
    }

    @Override
    public void approveCreateUser(int id, boolean status, String sender) {
        if (this.userMapping.tryGet(sender).get().getRole().equals(ADMIN_ROLE)) {
            if (status) {
                this.newUsersMapping.tryGet("USERS").ifPresent(el -> {
                    addUser(el.get(id));
                    if (this.companyMapping.tryGet(el.get(id).getCompanyName()).isEmpty() && !Objects.equals(el.get(id).getRole(), USER_ROLE)) {
                        List<String> users = new ArrayList<>();
                        users.add(el.get(id).getLogin());
                        this.companyMapping.put(el.get(id).getCompanyName(), new Company(el.get(id).getCompanyName(), users));
                    }
                    el.remove(id);
                    this.newUsersMapping.put("USERS", el);
                });
            } else {
                this.newUsersMapping.tryGet("USERS").ifPresent(el -> {
                    el.remove(id);
                    this.newUsersMapping.put("USERS", el);
                });
            }
        } else {
            throw new IllegalStateException("Вы не администратор");
        }
    }


    @Override
    public void createAccount(User user, String supplyRegions) {
        String[] arrayRegions = supplyRegions.split(",");
        this.newUsersMapping.tryGet("USERS").ifPresent(el ->
        {
            user.setSupplyRegions(Arrays.asList(arrayRegions));
            el.add(user);
            this.newUsersMapping.put("USERS", el);
        });
    }

    @Override
    public void addUser(User user) {
        this.userMapping.put(user.getLogin(), user);
        this.usersProductMapping.put(user.getLogin(), this.productList);
        this.orderProductionMapping.put(user.getLogin(), this.orderList);
    }

    @Override
    public void blockUser(String name, boolean status, String sender) {
        String role = this.userMapping.tryGet(sender).get().getRole();
        if (Objects.equals(role, ADMIN_ROLE)) {
            this.userMapping.tryGet(name).ifPresent(user -> {
                user.setBlocked(status);
                this.userMapping.put(user.getLogin(), user);
            });
        } else {
            throw new IllegalStateException("У вас нет прав на использование");
        }
    }

    @Override
    public void createOrderProduction(OrderProduction orderProduction, String company) {
        this.orderProductionMapping.tryGet(orderProduction.getCustomer()).ifPresent(order -> {
            this.companyMapping.tryGet(company).ifPresent(shopMap -> {
                boolean found = false;
                for (String element : shopMap.getCompanyShop().get(orderProduction.getId()).getRegions()) {
                    if (this.userMapping.tryGet(orderProduction.getCustomer()).get().getRegion().equals(element)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    throw new IllegalStateException("В ваш регион не доставляется");
                }
            });
            orderProduction.setShop(company);
            order.add(orderProduction);
            this.orderProductionMapping.put(orderProduction.getCustomer(), order);
        });
    }

    @Override
    public void transferProduct(int id, String to, String from) {
        this.usersProductMapping.tryGet(from).ifPresent(el -> this.usersProductMapping.tryGet(to).ifPresent(prod -> {
            prod.add((Product) el);
            this.usersProductMapping.put(to, prod);
            el.remove(id);
            this.usersProductMapping.put(from, el);
        }));
    }

    @Override
    public void approveTransfer(String user, int order, boolean status, String sender) {
        this.orderProductionMapping.tryGet(user).ifPresent(el -> {
            if (status) {
                OrderProduction currentOrder = el.get(order);
                transferProduct(currentOrder.getId(), currentOrder.getCustomer(), sender);
            } else {
                el.remove(order);
                this.orderProductionMapping.put(user, el);
            }
        });
    }

    @Override
    public void processOrder(String requester, int id, int amount, String date, String sender) {
        if (this.userMapping.tryGet(sender).get().getRole().equals(ADMIN_ROLE) || this.userMapping.tryGet(sender).get().getRole().equals(DISTRIBUTOR_ROLE)) {
            this.orderProductionMapping.tryGet(requester).ifPresent(order -> {
                boolean found = false;
                for (String element : this.usersProductMapping.tryGet(order.get(id).getShop()).get().get(order.get(id).getId()).getRegions()) {
                    if (this.userMapping.tryGet(requester).get().getRegion().equals(element)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    throw new IllegalStateException("В ваш регион не доставляется");
                }
            });

            //Если дистрибьютор не меняет данные в заказе, то в запросе отправляется -1
            if (amount != -1 || !Objects.equals(date, "-1")) {
                this.orderProductionMapping.tryGet(requester).ifPresent(order -> {
                    order.get(id).setStatus(STATUS_PROCESSING);
                    this.orderProductionMapping.put(requester, order);
                });
            } else {
                this.orderProductionMapping.tryGet(requester).ifPresent(order -> {
                            order.get(id).setStatus(STATUS_PROCESSING);
                            order.get(id).setDate(date);
                            order.get(id).setPrice(amount);
                            this.orderProductionMapping.put(requester, order);
                        }
                );
            }
        } else {
            throw new IllegalStateException("Вы не дистрибутор или администратор");
        }
    }
}