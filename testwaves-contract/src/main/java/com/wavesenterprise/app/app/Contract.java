package com.wavesenterprise.app.app;

import com.wavesenterprise.app.api.IContract;
import com.wavesenterprise.app.domain.*;
import com.wavesenterprise.app.features.ChechStatus;
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
    private final Mapping<List<Product>> onCheckProductCardMapping;

    List<Product> productList = new ArrayList<>();
    List<OrderProduction> orderList = new ArrayList<>();
    List<User> newUserList = new ArrayList<>();
    List<Product> onCheckProductCardList = new ArrayList<>();


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
        this.onCheckProductCardMapping = contractState.getMapping(new TypeReference<>() {
        }, ON_CHECK);
    }

    @Override
    public void init() {
        contractState.put(CONTRACT_CREATOR, call.getCaller());
        this.newUsersMapping.put("USERS", this.newUserList);
        this.onCheckProductCardMapping.put(PRODUCT_MAPPING, this.onCheckProductCardList);
        addUser(new User("admin", "admin", "admin", null, null, null, 0, null));
    }

    @Override
    public void approveCard(String company, int id, boolean status, int min, int max, String distributors, String sender) {
        ChechStatus.onlyAdmin(this.userMapping.tryGet(sender).get());
        ChechStatus.acceptedStatus(status);
        this.onCheckProductCardMapping.tryGet(PRODUCT_MAPPING).ifPresent(card -> {
            card.get(id).setMaxCount(max);
            card.get(id).setMinCount(min);
            for (String user : distributors.split(",")) {
                this.userMapping.tryGet(user).ifPresent(userMap -> {
                    if (HashComponent.hasCommonElement(userMap.getSupplyRegions(), card.get(id).getRegions())) {
                        card.get(id).addDistributors(user);
                        card.get(id).setStatus(STATUS_APPROVED);
                        this.companyMapping.tryGet(userMap.getCompanyName()).ifPresent(companyMap -> {
                            companyMap.addCompanyShop(card.get(id));
                            this.companyMapping.put(company, companyMap);
                        });
                    }
                });
            }
        });

    }

    @Override
    public void createShopCard(Product product, String regions, String sender) {
        ChechStatus.onlySupplier(this.userMapping.tryGet(sender).get());
        this.onCheckProductCardMapping.tryGet(PRODUCT_MAPPING).ifPresent(card -> {
            product.setRegions(Arrays.asList(regions.split(",")));
            product.setCompanyName(this.userMapping.tryGet(sender).get().getCompanyName());
            card.add(product);
            this.onCheckProductCardMapping.put(PRODUCT_MAPPING, card);
        });
    }


    @Override
    public void approveCreateUser(int id, boolean status, String sender) {
        ChechStatus.onlyAdmin(this.userMapping.tryGet(sender).get());
        if (status) {
            this.newUsersMapping.tryGet("USERS").ifPresent(el -> {
                addUser(el.get(id));
                if (el.get(id).getCompanyName() != null && !Objects.equals(el.get(id).getRole(), USER_ROLE)) {
                    List<String> users = new ArrayList<>();
                    users.add(el.get(id).getLogin());
                    this.companyMapping.put(el.get(id).getCompanyName(), new Company(el.get(id).getCompanyName(), users));
                }
                el.get(id).setStatus(STATUS_ACCEPTED);
                this.newUsersMapping.put("USERS", el);
            });
        } else {
            this.newUsersMapping.tryGet("USERS").ifPresent(el -> {
                el.get(id).setStatus(STATUS_DENIED);
                this.newUsersMapping.put("USERS", el);
            });
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
        ChechStatus.onlyAdmin(this.userMapping.tryGet(sender).get());
        this.userMapping.tryGet(name).ifPresent(user -> {
            user.setBlocked(status);
            this.userMapping.put(user.getLogin(), user);
        });
    }

    @Override
    public void createOrderProduction(OrderProduction orderProduction) {
        ChechStatus.isBlocked(this.userMapping.tryGet(orderProduction.getCustomer()).get());
        this.orderProductionMapping.tryGet(orderProduction.getCustomer()).ifPresent(order -> {
            this.companyMapping.tryGet(orderProduction.getCompany()).ifPresent(shopMap -> {
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
            orderProduction.setCompany(orderProduction.getCompany());
            order.add(orderProduction);
            this.orderProductionMapping.put(orderProduction.getCustomer(), order);
        });
    }

    @Override
    public void transferProduct(int orderId, String to, String from) {
        this.companyMapping.tryGet(from).ifPresent(company -> {
            this.userMapping.tryGet(to).ifPresent(user -> {
                this.usersProductMapping.tryGet(to).ifPresent(products -> {
                    products.add(company.getCompanyShop().
                            get(this.orderProductionMapping.
                                    get(to).
                                    get(orderId).
                                    getId()));
                    this.usersProductMapping.put(to, products);
                });
                this.userMapping.put(to, user);
            });
        });
    }

    @Override
    public void acceptOrder(int order, boolean status, String sender) {
        this.orderProductionMapping.tryGet(sender).ifPresent(el -> {
            if (status) {
                OrderProduction currentOrder = el.get(order);
                transferProduct(currentOrder.getId(), sender, currentOrder.getCompany());
                el.remove(order);
                this.orderProductionMapping.put(sender, el);

            } else {
                el.remove(order);
                this.orderProductionMapping.put(sender, el);
            }
        });
    }

    //TODO: Если вводить корректирующие данные для заказа, то проверка попадает в else, а не if.
    @Override
    public void formatOrder(String requester, int id, int amount, String date, String sender) {
        ChechStatus.onlySupplierOrAdmin(this.userMapping.tryGet(sender).get());
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
    }
}