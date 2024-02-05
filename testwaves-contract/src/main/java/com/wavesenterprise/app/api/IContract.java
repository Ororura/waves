package com.wavesenterprise.app.api;

import com.wavesenterprise.app.domain.*;
import com.wavesenterprise.sdk.contract.api.annotation.*;

public interface IContract {
    @ContractInit
    void init();

    @ContractAction
    void addUser(@InvokeParam(name = "addUser") User user);

    @ContractAction
    void blockUser(@InvokeParam(name = "userName") String name,
                   @InvokeParam(name = "status") boolean status,
                   @InvokeParam(name = "sender") String sender);

    @ContractAction
    void createProduct(@InvokeParam(name = "product") Product product, @InvokeParam(name = "regions") String regions, @InvokeParam(name = "sender") String sender);

    @ContractAction
    void createOrderProduction(@InvokeParam(name = "product") OrderProduction orderProduction, @InvokeParam(name = "sender") String sender);

    @ContractAction
    void transferProduct(@InvokeParam(name = "id") int id, @InvokeParam(name = "to") String to, @InvokeParam(name = "from") String from);

    @ContractAction
    void approveTransfer(@InvokeParam(name = "user") String user, @InvokeParam(name = "order") int order, @InvokeParam(name = "status") boolean status, @InvokeParam(name = "sender") String sender);

    @ContractAction
    void createAccount(@InvokeParam(name = "user") User user);

    @ContractAction
    void approveCreateUser(@InvokeParam(name = "id") int id, @InvokeParam(name = "status") boolean status);

    class Keys {
        public static final String CONTRACT_CREATOR = "CONTRACT_CREATOR";
        public static final String USER_MAPPING = "USERS";
        public static final String BLOCKED_MAPPING = "BLOCK";
        public static final String COMPANY_MAPPING = "COMPANY";
        public static final String USER_PRODUCT_MAPPING = "USERS_PRODUCT";
        public static final String ADMIN_ROLE = "admin";
        public static final String DISTRIBUTOR_ROLE = "distributor";
        public static final String USER_ROLE = "user";
        public static final String ORDER_PRODUCTION = "ORDER_PRODUCTION";
        public static final String NEW_USERS = "NEW_USERS";
    }
}