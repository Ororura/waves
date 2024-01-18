package com.wavesenterprise.app.app;

import com.wavesenterprise.app.api.IContract;
import com.wavesenterprise.app.domain.Car;
import com.wavesenterprise.app.domain.Fruit;
import com.wavesenterprise.app.domain.User;
import com.wavesenterprise.sdk.contract.api.annotation.ContractHandler;
import com.wavesenterprise.sdk.contract.api.domain.ContractCall;
import com.wavesenterprise.sdk.contract.api.state.ContractState;
import com.wavesenterprise.sdk.contract.api.state.mapping.*;

import java.util.Optional;

import static com.wavesenterprise.app.api.IContract.Keys.*;

@ContractHandler
public class Contract implements IContract {

    private final ContractState contractState;
    private final ContractCall call;
    private final Mapping<Fruit> fruitMapping;
    private final Mapping<User> userMapping;
    private final Mapping<Car> carMapping;

    public Contract(ContractState contractState, ContractCall call) {
        this.contractState = contractState;
        this.call = call;
        this.userMapping = contractState.getMapping(User.class, USER_MAPPING);
        this.fruitMapping = contractState.getMapping(Fruit.class, FRUIT_MAPPING_PREFIX);
        this.carMapping = contractState.getMapping(Car.class, CAR_MAPPING);
    }

    @Override
    public void init() {
        contractState.put(CONTRACT_CREATOR, call.getCaller());
    }

    @Override
    public void addFruit(Fruit fruit) {
        this.fruitMapping.put(fruit.getName(), fruit);
    }
    @Override
    public void addUser(User user) {
        this.userMapping.put(user.getLastName(), user);
    }

    @Override
    public void addCar(Car car) {
        this.carMapping.put(car.getModel(), car);
    }

    @Override
    public Fruit getFruit(String name) {
        System.out.println(this.fruitMapping);
        Optional<Fruit> currentFruit = this.fruitMapping.tryGet(name);
        System.out.println(currentFruit);
        return currentFruit.orElseThrow(() -> new IllegalStateException(
            "Fruit " + name + " does not exist"
        ));
    }

}
