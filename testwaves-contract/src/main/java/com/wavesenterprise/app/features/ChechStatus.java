package com.wavesenterprise.app.features;

import com.wavesenterprise.app.domain.User;

import static com.wavesenterprise.app.api.IContract.Keys.*;

public class ChechStatus {
    public static void isBlocked(User user) {
        if (user.isBlocked()) {
            throw new IllegalStateException("Вы заблокированы");
        }
    }

    public static void onlySupplierOrAdmin(User user) {
        if (!user.getRole().equals(ADMIN_ROLE) || !user.getRole().equals(DISTRIBUTOR_ROLE)) {
            throw new IllegalStateException("Вы не дистрибутор или оператор");
        }
    }

    public static void onlyAdmin(User user) {
        if (!user.getRole().equals(ADMIN_ROLE)) {
            throw new IllegalStateException("Вы не оператор");
        }
    }

    public static void onlySupplier(User user) {
        if(!user.getRole().equals(SUPPLIER_ROLE)) {
            throw new IllegalStateException("Вы не дистрибутор");
        }
    }
}
