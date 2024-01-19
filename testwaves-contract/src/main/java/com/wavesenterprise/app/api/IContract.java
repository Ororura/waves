package com.wavesenterprise.app.api;

import com.wavesenterprise.app.domain.*;
import com.wavesenterprise.sdk.contract.api.annotation.*;

public interface IContract {
    @ContractInit
    void init();

    @ContractAction
    void addFruit(@InvokeParam(name = "addFruit") Fruit fruit);

    @ContractAction
    Fruit getFruit(@InvokeParam(name = "getFruit") String name);

    @ContractAction
    void addUser(@InvokeParam(name = "addUser")User user);

    @ContractAction
    void addCar(@InvokeParam(name= "addCar")Car car);

    @ContractAction
    void addSupplier(@InvokeParam(name = "addSupplier")Supplier supplier);

    @ContractAction
    void addDist(@InvokeParam(name = "addDist")Distributor distributor);

    @ContractAction
    void createRef(@InvokeParam(name = "createRef") String name);

    class Keys {
        public static final String CONTRACT_CREATOR = "CONTRACT_CREATOR";
        public static final String FRUIT_MAPPING_PREFIX = "FRUITS";
        public static final String USER_MAPPING = "USERS";
        public static final String CAR_MAPPING = "CARS";
        public static final String SUPPLIERS_MAPPING = "SUPPLIERS";
        public static final String DISTRIBUTOR_MAPPING = "DISTRIBUTOR";
        public static final String REF_MAPPING = "REF";
    }
}
