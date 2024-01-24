package com.wavesenterprise.app.api;

import com.fasterxml.jackson.databind.ext.SqlBlobSerializer;
import com.wavesenterprise.app.domain.*;
import com.wavesenterprise.sdk.contract.api.annotation.*;

public interface IContract {
    @ContractInit
    void init();

    @ContractAction
    User addUser(@InvokeParam(name = "addUser")User user);

    @ContractAction
    void addSupplier(@InvokeParam(name = "addSupplier")Supplier supplier);

    @ContractAction
    void addDist(@InvokeParam(name = "addDist")Distributor distributor);

    @ContractAction
    void createRef(@InvokeParam(name = "createRef")Refferal refferal);

    @ContractAction
    User getUser(@InvokeParam(name = "getUser")String name);

    @ContractAction
    void blockUser(@InvokeParam(name = "blockUser")BlockUser blockUser);

    @ContractAction
    void addOrder(@InvokeParam(name = "addOrder")Order order);


    class Keys {
        public static final String CONTRACT_CREATOR = "CONTRACT_CREATOR";
        public static final String USER_MAPPING = "USERS";
        public static final String SUPPLIERS_MAPPING = "SUPPLIERS";
        public static final String DISTRIBUTOR_MAPPING = "DISTRIBUTOR";
        public static final String REF_MAPPING = "REF";
        public static final String BLOCKED_MAPPING = "BLOCK";
        public static final String DISTRIBUTORS_MAPPING = "DISTRIBUTORS";
        public static final String ORDERS_MAPPING = "ORDERS";

    }
}