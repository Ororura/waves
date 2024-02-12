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
    void createOrderProduction(@InvokeParam(name = "product") OrderProduction orderProduction);

    @ContractAction
    void transferProduct(@InvokeParam(name = "id") int id, @InvokeParam(name = "to") String to, @InvokeParam(name = "from") String from);

    @ContractAction
    void acceptOrder(@InvokeParam(name = "order") int order, @InvokeParam(name = "status") boolean status, @InvokeParam(name = "sender") String sender);

    @ContractAction
    void createAccount(@InvokeParam(name = "user") User user, String supplyRegions);

    @ContractAction
    void approveCreateUser(@InvokeParam(name = "id") int id, @InvokeParam(name = "status") boolean status, @InvokeParam(name = "sender")String sender);

    @ContractAction
    void formatOrder(@InvokeParam(name = "id") int id, @InvokeParam(name = "amount") int amount, @InvokeParam(name = "date") String date, @InvokeParam(name = "sender") String sender);

    @ContractAction
    void createShopCard(@InvokeParam(name = "product") Product product, @InvokeParam(name = "regions") String regions, @InvokeParam(name = "sender") String sender);

    @ContractAction
    void approveCard(@InvokeParam(name = "company") String company, @InvokeParam(name = "id") int id, @InvokeParam(name = "status") boolean status, @InvokeParam(name = "min") int min, @InvokeParam(name = "max") int max, @InvokeParam(name = "distributors") String distributors, @InvokeParam(name = "sender") String sender);

    class Keys {
        public static final String CONTRACT_CREATOR = "CONTRACT_CREATOR";
        public static final String USER_MAPPING = "USERS";
        public static final String COMPANY_MAPPING = "COMPANY";
        public static final String USER_PRODUCT_MAPPING = "USERS_PRODUCT";
        public static final String ADMIN_ROLE = "admin";
        public static final String SUPPLIER_ROLE = "supplier";
        public static final String DISTRIBUTOR_ROLE = "distributor";
        public static final String USER_ROLE = "user";
        public static final String ORDER_PRODUCTION = "_";
        public static final String NEW_USERS = "_";
        public static final String STATUS_PREPARING = "preparing";
        public static final String STATUS_PROCESSING = "processing";
        public static final String STATUS_ACCEPTED = "accepted";
        public static final String STATUS_DENIED = "denied";
        public static final String STATUS_APPROVED = "approved";
        public static final String ON_CHECK = "onCheck";
        public static final String PRODUCT_MAPPING = "products";
        public static final String ORDER_PRODUCT = "orders";
        public static final String COMPANY_NAMES = "companyNames";
    }
}