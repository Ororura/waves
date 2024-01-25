package com.wavesenterprise.app.app;

import com.wavesenterprise.app.api.IContract;
import com.wavesenterprise.app.domain.*;
import com.wavesenterprise.sdk.contract.api.annotation.ContractHandler;
import com.wavesenterprise.sdk.contract.api.domain.ContractCall;
import com.wavesenterprise.sdk.contract.api.state.ContractState;
import com.wavesenterprise.sdk.contract.api.state.mapping.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static com.wavesenterprise.app.api.IContract.Keys.*;

@ContractHandler
public class Contract implements IContract {

    private final ContractState contractState;
    private final ContractCall call;
    private final Mapping<User> userMapping;
    private final Mapping<Refferal> refMapping;
    private final Mapping<Boolean> blockedMapping;
    private final Mapping<Order> orderMapping;
    private final Mapping<Company> companyMapping;


    public Contract(ContractState contractState, ContractCall call) {
        this.contractState = contractState;
        this.call = call;
        this.userMapping = contractState.getMapping(User.class, USER_MAPPING);
        this.refMapping = contractState.getMapping(Refferal.class,  REF_MAPPING);
        this.blockedMapping = contractState.getMapping(Boolean.class, BLOCKED_MAPPING);
        this.orderMapping = contractState.getMapping(Order.class, ORDERS_MAPPING);
        this.companyMapping = contractState.getMapping(Company.class, COMPANY_MAPPING);
    }

    @Override
    public void init() {
        contractState.put(CONTRACT_CREATOR, call.getCaller());
    }

    @Override
    public void addUser(User user, String regions) {
        System.out.println("USER" + user);
        String[] regionsArray = regions.split(",");
        user.setRegions(Arrays.asList(regionsArray));
        this.userMapping.put(user.getLogin(), user);

        System.out.println(user.getCompanyName());
        Optional<Company> currentCompany = this.companyMapping.tryGet("Bober");

        currentCompany.ifPresent(w -> System.out.println(w.getCompanyName()));
        System.out.println(currentCompany.isEmpty());
    }

    @Override
    public void createRef(Refferal refferal) {
        refferal.setName("2024-" + refferal.getUserLogin() + "-PROFI");
        this.refMapping.put(refferal.getUserLogin(), refferal);
    }

    @Override
    public User getUser(String name) {
        Optional<User> currentUser = this.userMapping.tryGet(name);
        currentUser.ifPresent(user -> System.out.println(user.getLogin()));
        return currentUser.orElseThrow(() -> new IllegalStateException(name));
    }

    @Override
    public void blockUser(String name, boolean status, String sender) {
        System.out.println("BLOCK");
        String role = this.userMapping.tryGet(sender).get().getRole();
        System.out.println(role);

        if(Objects.equals(role, "admin")) {
            Optional<User> currentUser = this.userMapping.tryGet(name);
            this.blockedMapping.put(name, status);
            currentUser.ifPresent(user -> user.setBlocked(status));
            currentUser.ifPresent(user -> System.out.println(user.isBlocked()));
            currentUser.ifPresent(user -> this.userMapping.put(user.getLogin(), user));
            currentUser.ifPresent(user -> System.out.println(user.isBlocked()));
        } else {
            System.out.println("У вас нет прав");
        }
    }

    @Override
    public void addOrder(Order order, String regions, String sender) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String text = "kek";
        byte[] hash  = digest.digest(text.getBytes(StandardCharsets.UTF_8));
        System.out.println(Arrays.toString(hash));
        System.out.println("ORDER");
        String role = this.userMapping.tryGet(sender).get().getRole();
        System.out.println(regions);
        String[] regionsArray = regions.split(",");
        if (Objects.equals(role, "dist")) {
            order.setRegions(Arrays.asList(regionsArray));
            this.orderMapping.put(order.getProductName(), order);
            System.out.println(role);
            System.out.println(order.getRegions());
            System.out.println(order.getRegions().get(0));
            System.out.println(order.getRegions().size());
        }
    }

    @Override
    public void createCompany(Company company) {
        this.companyMapping.put(company.getCompanyName(), company);
    }
}