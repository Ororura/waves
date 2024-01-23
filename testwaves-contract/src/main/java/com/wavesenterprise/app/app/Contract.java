package com.wavesenterprise.app.app;

import com.wavesenterprise.app.api.IContract;
import com.wavesenterprise.app.domain.*;
import com.wavesenterprise.sdk.contract.api.annotation.ContractHandler;
import com.wavesenterprise.sdk.contract.api.domain.ContractCall;
import com.wavesenterprise.sdk.contract.api.state.ContractState;
import com.wavesenterprise.sdk.contract.api.state.mapping.*;

import java.util.Arrays;
import java.util.Optional;

import static com.wavesenterprise.app.api.IContract.Keys.*;

@ContractHandler
public class Contract implements IContract {

    private final ContractState contractState;
    private final ContractCall call;
    private final Mapping<User> userMapping;
    private final Mapping<Supplier> supplierMapping;
    private final Mapping<Distributor> distributorMapping;
    private final Mapping<Refferal> refMapping;
    private final Mapping<String> blockedMapping;

    public Contract(ContractState contractState, ContractCall call) {
        this.contractState = contractState;
        this.call = call;
        this.userMapping = contractState.getMapping(User.class, USER_MAPPING);
        this.supplierMapping = contractState.getMapping(Supplier.class, SUPPLIERS_MAPPING);
        this.distributorMapping = contractState.getMapping(Distributor.class, DISTRIBUTOR_MAPPING);
        this.refMapping = contractState.getMapping(Refferal.class,  REF_MAPPING);
        this.blockedMapping = contractState.getMapping(String.class, BLOCKED_MAPPING);

    }

    @Override
    public void init() {
        contractState.put(CONTRACT_CREATOR, call.getCaller());
    }

    @Override
    public User addUser(User user) {
        System.out.println("SOUT" + user);
        this.userMapping.put(user.getLogin(), user);
        return user;
    }

    @Override
    public void addSupplier(Supplier supplier) {
        this.supplierMapping.put(supplier.getSupplierName(), supplier);
    }

    @Override
    public void addDist(Distributor distributor) {
        this.distributorMapping.put(distributor.getKey(), distributor);
    }


    @Override
    public void createRef(Refferal refferal) {
        refferal.setName("2024-" + refferal.getUserLogin() + "-PROFI");
        this.refMapping.put(refferal.getUserLogin(), refferal);
    }

    @Override
    public void getUser(String name) {
        Optional<User> currentUser = this.userMapping.tryGet(name);
        currentUser.ifPresent(user -> System.out.println(user.getLogin()));

    }

    @Override
    public void blockUser(String name, Boolean status) {
        Optional<User> currentUser = this.userMapping.tryGet(name);
        this.blockedMapping.put(name, "Blocked");
        currentUser.ifPresent(user -> user.setBlocked(true));
        currentUser.ifPresent(user -> System.out.println(user.isBlocked()));
        //currentUser.ifPresent(user -> this.userMapping.put(user.getLogin(), user));
        currentUser.ifPresent(user -> System.out.println(user.isBlocked()));
    }
}