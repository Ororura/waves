package com.wavesenterprise.app.api;

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
    void getUser(@InvokeParam(name = "getUser")String name);

    @ContractAction
    void blockUser(@InvokeParam(name = "blockUser")String name, Boolean status);

    class Keys {
        public static final String CONTRACT_CREATOR = "CONTRACT_CREATOR";
        public static final String USER_MAPPING = "USERS";
        public static final String SUPPLIERS_MAPPING = "SUPPLIERS";
        public static final String DISTRIBUTOR_MAPPING = "DISTRIBUTOR";
        public static final String REF_MAPPING = "REF";
        public static final String TEST_MAPPING = "TEST";
        public static final String BLOCKED_MAPPING = "BLOCK";

    }
}