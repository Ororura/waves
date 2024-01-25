package com.wavesenterprise.app.api;

import com.wavesenterprise.app.domain.*;
import com.wavesenterprise.sdk.contract.api.annotation.*;

import java.security.NoSuchAlgorithmException;

public interface IContract {
    @ContractInit
    void init();

    @ContractAction
    void addUser(@InvokeParam(name = "addUser")User user, @InvokeParam(name = "regions")String regions);

    @ContractAction
    void createRef(@InvokeParam(name = "createRef")Refferal refferal);

    @ContractAction
    User getUser(@InvokeParam(name = "getUser")String name);

    @ContractAction
    void blockUser(@InvokeParam(name = "userName")String name,
                   @InvokeParam(name = "status") boolean status,
                   @InvokeParam(name = "sender")String sender) throws NoSuchAlgorithmException;

    @ContractAction
    void addOrder(@InvokeParam(name = "order")Order order, @InvokeParam(name = "regions")String regions, @InvokeParam(name = "sender") String sender) throws NoSuchAlgorithmException;

    @ContractAction
    void createCompany(@InvokeParam(name = "company")Company company);

    class Keys {
        public static final String CONTRACT_CREATOR = "CONTRACT_CREATOR";
        public static final String USER_MAPPING = "USERS";
        public static final String REF_MAPPING = "REF";
        public static final String BLOCKED_MAPPING = "BLOCK";
        public static final String ORDERS_MAPPING = "ORDERS";
        public static final String COMPANY_MAPPING = "COMPANY";

    }
}