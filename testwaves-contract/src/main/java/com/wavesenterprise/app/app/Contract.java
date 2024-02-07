package com.wavesenterprise.app.app;

import com.wavesenterprise.app.api.IContract;
import com.wavesenterprise.app.domain.*;
import com.wavesenterprise.app.features.HashComponent;
import com.wavesenterprise.sdk.contract.api.annotation.ContractHandler;
import com.wavesenterprise.sdk.contract.api.domain.ContractCall;
import com.wavesenterprise.sdk.contract.api.state.ContractState;
import com.wavesenterprise.sdk.contract.api.state.TypeReference;
import com.wavesenterprise.sdk.contract.api.state.mapping.*;

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
        this.usersProductMapping = contractState.getMapping(new TypeReference<>() {
        }, USER_PRODUCT_MAPPING);
        this.orderProductionMapping = contractState.getMapping(new TypeReference<>() {
        }, ORDER_PRODUCTION);
        this.newUsersMapping = contractState.getMapping(new TypeReference<>() {
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
    public void approveCard(String company, int id, boolean status, int min, int max, String distributors, String sender) {
        this.userMapping.tryGet(sender).ifPresent(owner ->
                {
                    if (Objects.equals(owner.getRole(), ADMIN_ROLE)) {
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
                        throw new IllegalStateException("Вы не оператор");
                    }
                }
        );
    }

    @Override
    public void createShopCard(Product product, String regions, String sender) {
        if (this.userMapping.tryGet(sender).get().getRole().equals(SUPPLIER_ROLE)) {
            this.userMapping.tryGet(sender).ifPresent(user -> {
                this.companyMapping.tryGet(user.getCompanyName()).ifPresent(company -> {
                            product.setRegions(Arrays.asList(regions.split(",")));
                            company.addCompanyShop(product);
                            this.companyMapping.put(user.getCompanyName(), company);
                        }
                );
            });
        } else {
            throw new IllegalStateException("Только поставщик может добавлять карточки");
        }
    }

    @Override
    public void approveCreateUser(int id, boolean status, String sender) {
        if (this.userMapping.tryGet(sender).get().getRole().equals(ADMIN_ROLE)) {
            if (status) {
                this.newUsersMapping.tryGet("USERS").ifPresent(el -> {
                    addUser(el.get(id));
                    if (el.get(id).getCompanyName() != null && this.companyMapping.tryGet(el.get(id).getCompanyName()).isEmpty() && !Objects.equals(el.get(id).getRole(), USER_ROLE)) {
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
            throw new IllegalStateException("Вы не оператор");
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
    public void createOrderProduction(OrderProduction orderProduction) {
        this.orderProductionMapping.tryGet(orderProduction.getCustomer()).ifPresent(order -> {
            this.companyMapping.tryGet(orderProduction.getCompany()).ifPresent(shopMap -> {
                boolean found = false;
                for (String element : shopMap.getCompanyShop().get(orderProduction.getId()).getRegions()) {
                    System.out.println(element);
                    System.out.println(this.userMapping.tryGet(orderProduction.getCustomer()).get().getRegion());
                    if (this.userMapping.tryGet(orderProduction.getCustomer()).get().getRegion().equals(element)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    throw new IllegalStateException("В ваш регион не доставляется");
                }
            });
            orderProduction.setCompany(orderProduction.getCompany());
            order.add(orderProduction);
            this.orderProductionMapping.put(orderProduction.getCustomer(), order);
        });
    }

    //TODO: Исправить перевод трансфера. При добавлении продукта, product пустой.
    @Override
    public void transferProduct(int orderId, String to, String from) {
        System.out.println("TESTT");
        this.companyMapping.tryGet(from).ifPresent(company -> {
            System.out.println("TESTT@");
            this.userMapping.tryGet(to).ifPresent(user -> {
                System.out.println("TESSTSTST");
                System.out.println(company.getCompanyShop().get(0).getProductName());
                System.out.println(this.orderProductionMapping.get(to).get(orderId).getId());
                user.addProductList(company.getCompanyShop().get(0));
//                user.addProductList(company.getCompanyShop().get(this.orderProductionMapping.get(to).get(orderId).getId()));
                System.out.println("ASIDJLIJD");
                this.userMapping.put(to, user);
            });
        });
    }

    @Override
    public void acceptOrder(int order, boolean status, String sender) {
        this.orderProductionMapping.tryGet(sender).ifPresent(el -> {
            System.out.println("WORK");
            if (status) {
                System.out.println("WORK2");
                OrderProduction currentOrder = el.get(order);
                System.out.println(currentOrder.getId() + "ID" + sender + "sender" + currentOrder.getCompany() + "company");
                transferProduct(currentOrder.getId(), sender, currentOrder.getCompany());
                System.out.println("WORK3");

            } else {
                el.remove(order);
                this.orderProductionMapping.put(sender, el);
            }
        });
    }

    //TODO: Если вводить корректирующие данные для заказа, то проверка попадает в else, а не if.
    @Override
    public void formatOrder(String requester, int id, int amount, String date, String sender) {
        if (this.userMapping.tryGet(sender).get().getRole().equals(ADMIN_ROLE) || this.userMapping.tryGet(sender).get().getRole().equals(DISTRIBUTOR_ROLE)) {
            System.out.println("WORKING");
            this.orderProductionMapping.tryGet(requester).ifPresent(order -> {
                boolean found = false;
                for (String element : this.companyMapping.tryGet(order.get(id).getCompany()).get().getCompanyShop().get(order.get(id).getId()).getRegions()) {
                    if (this.userMapping.tryGet(requester).get().getRegion().equals(element)) {
                        System.out.println("FOUND");
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    throw new IllegalStateException("Вы не можете оформить этот заказ");
                }
            });

            //Если дистрибьютор не меняет данные в заказе, то в запросе отправляется -1
            if (amount != -1 || !Objects.equals(date, "-1")) {
                System.out.println("IF TEST");
                this.orderProductionMapping.tryGet(requester).ifPresent(order -> {
                    order.get(id).setStatus(STATUS_PROCESSING);
                    this.orderProductionMapping.put(requester, order);
                });
            } else {
                System.out.println("ELSE TEST");
                this.orderProductionMapping.tryGet(requester).ifPresent(order -> {
                            order.get(id).setStatus(STATUS_PROCESSING);
                            order.get(id).setDate(date);
                            order.get(id).setPrice(amount);
                            this.orderProductionMapping.put(requester, order);
                        }
                );
            }
        } else {
            throw new IllegalStateException("Вы не дистрибутор или оператор");
        }
    }
}