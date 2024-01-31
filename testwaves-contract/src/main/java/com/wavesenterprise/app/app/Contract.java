package com.wavesenterprise.app.app;

import com.wavesenterprise.app.api.IContract;
import com.wavesenterprise.app.domain.*;
import com.wavesenterprise.sdk.contract.api.annotation.ContractHandler;
import com.wavesenterprise.sdk.contract.api.domain.ContractCall;
import com.wavesenterprise.sdk.contract.api.state.ContractState;
import com.wavesenterprise.sdk.contract.api.state.mapping.*;

import java.util.*;

import static com.wavesenterprise.app.api.IContract.Keys.*;

@ContractHandler
public class Contract implements IContract {

    private final ContractState contractState;
    private final ContractCall call;
    private final Mapping<User> userMapping;
    private final Mapping<Boolean> blockedMapping;
    private final Mapping<List> usersProductMapping;
    private final Mapping<List> orderProductionMapping;


    public Contract(ContractState contractState, ContractCall call) {
        this.contractState = contractState;
        this.call = call;
        this.userMapping = contractState.getMapping(User.class, USER_MAPPING);
        this.blockedMapping = contractState.getMapping(Boolean.class, BLOCKED_MAPPING);
        this.usersProductMapping = contractState.getMapping(List.class, USER_PRODUCT_MAPPING);
        this.orderProductionMapping = contractState.getMapping(List.class, ORDER_PRODUCTION);
    }

    @Override
    public void init() {
        contractState.put(CONTRACT_CREATOR, call.getCaller());
    }

    @Override
    public void addUser(User user) {
        this.userMapping.put(user.getLogin(), user);
    }

    @Override
    public void blockUser(String name, boolean status, String sender) {
        String role = this.userMapping.tryGet(sender).get().getRole();

        if (Objects.equals(role, ADMIN_ROLE)) {
            Optional<User> currentUser = this.userMapping.tryGet(name);
            this.blockedMapping.put(name, status);
            currentUser.ifPresent(user -> user.setBlocked(status));
            currentUser.ifPresent(user -> this.userMapping.put(user.getLogin(), user));
        } else {
            System.out.println("У вас нет прав");
        }
    }

    @Override
    public void createProduct(Product product, String regions, String sender) {
        String role = this.userMapping.tryGet(sender).get().getRole();
        String[] regionsArray = regions.split(",");
        if (Objects.equals(role, DISTRIBUTOR_ROLE)) {
            Optional<List> currentUsersProducts = this.usersProductMapping.tryGet(sender);
            product.setRegions(Arrays.asList(regionsArray));
            if (!currentUsersProducts.isPresent()) {
                List<Product> listProduct = new ArrayList<>();
                listProduct.add(product);
                this.usersProductMapping.put(sender, listProduct);
            } else {
                currentUsersProducts.ifPresent(el -> el.add(product));
                this.usersProductMapping.put(sender, currentUsersProducts.get());
            }
        }
    }

    @Override
    public void createOrderProduction(OrderProduction orderProduction, String sender) {
        System.out.println("ORDER");
        Optional<List> currentOrderProduction = this.orderProductionMapping.tryGet(sender);
        if (!currentOrderProduction.isPresent()) {
            System.out.println("ORDER1");
            List<OrderProduction> orderList = new ArrayList<>();
            currentOrderProduction.ifPresent(el -> {
                orderProduction.setId(el.size());
                orderList.add(orderProduction);
                this.orderProductionMapping.put(sender, orderList);
            });
        } else {
            System.out.println("ORDER2");
            currentOrderProduction.ifPresent(el -> {
                orderProduction.setId(el.size());
                el.add(orderProduction);
                this.orderProductionMapping.put(sender, el);
            });
        }
    }
}