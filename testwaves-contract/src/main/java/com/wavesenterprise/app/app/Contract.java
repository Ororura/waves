package com.wavesenterprise.app.app;

import com.wavesenterprise.app.api.IContract;
import com.wavesenterprise.app.domain.*;
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
    }

    @Override
    public void init() {
        contractState.put(CONTRACT_CREATOR, call.getCaller());
        this.newUsersMapping.put("USERS", this.newUserList);
        addUser(new User("admin", "admin", "admin", false, null, null, null, 0));
    }

    @Override
    public void approveCreateUser(int id, boolean status) {
        if (status) {
            this.newUsersMapping.tryGet("USERS").ifPresent(el -> {
                addUser(el.get(id));
                el.remove(id);
                this.newUsersMapping.put("USERS", el);
            });
            addUser(this.newUsersMapping.tryGet("USERS").get().get(id));
        } else {
            this.newUsersMapping.tryGet("USERS").ifPresent(el -> {
                el.remove(id);
                this.newUsersMapping.put("USERS", el);
            });
        }
    }

    @Override
    public void createAccount(User user) {
        this.newUsersMapping.tryGet("USERS").ifPresent(el -> {
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
            System.out.println("У вас нет прав");
        }
    }

    @Override
    public void createProduct(Product product, String regions, String sender) {
        String role = this.userMapping.tryGet(sender).get().getRole();
        String[] regionsArray = regions.split(",");
        if (Objects.equals(role, DISTRIBUTOR_ROLE)) {
            product.setRegions(Arrays.asList(regionsArray));
            this.usersProductMapping.tryGet(sender).ifPresent(el -> {
                el.add(product);
                this.usersProductMapping.put(sender, el);
            });
        }
    }

    @Override
    public void createOrderProduction(OrderProduction orderProduction, String sender) {
        this.orderProductionMapping.tryGet(sender).ifPresent(el -> {
            this.userMapping.tryGet(sender).ifPresent(user -> {
                if (Objects.equals(user.getRole(), USER_ROLE)) {
                    this.usersProductMapping.tryGet(sender).ifPresent(prod -> {
                        System.out.println("TEST1" + prod.get(orderProduction.getId()).getRegions());
                        for (String element : prod.get(orderProduction.getId()).getRegions()) {
                            System.out.println("TEST" + element);
                        }
                    });
                }
            });
            el.add(orderProduction);
            this.orderProductionMapping.put(sender, el);
        });
    }

    @Override
    public void transferProduct(int id, String to, String from) {
        this.usersProductMapping.tryGet(from).ifPresent(el -> this.usersProductMapping
                .tryGet(to)
                .ifPresent(prod -> {
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
}