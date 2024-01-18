package com.wavesenterprise.app.api;

import com.wavesenterprise.app.domain.Car;
import com.wavesenterprise.app.domain.Fruit;
import com.wavesenterprise.app.domain.User;
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

    class Keys {
        public static final String CONTRACT_CREATOR = "CONTRACT_CREATOR";
        public static final String FRUIT_MAPPING_PREFIX = "FRUITS";
        public static final String USER_MAPPING = "USERS";
        public static final String CAR_MAPPING = "CARS";
    }
}
