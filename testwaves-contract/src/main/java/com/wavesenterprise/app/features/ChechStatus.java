package com.wavesenterprise.app.features;

import com.wavesenterprise.app.domain.Product;
import com.wavesenterprise.app.domain.User;

import static com.wavesenterprise.app.api.IContract.Keys.*;

public class ChechStatus {
    public static void isBlocked(User user) {
        if (user.isBlocked()) {
            throw new IllegalStateException("Вы заблокированы");
        }
    }

    public static void onlyDistOrAdmin(User user) {
        if (!user.getRole().equals(ADMIN_ROLE) && !user.getRole().equals(DISTRIBUTOR_ROLE)) {
            throw new IllegalStateException("Вы не дистрибутор или оператор");
        }
    }

    public static void onlyAdmin(User user) {
        if (!user.getRole().equals(ADMIN_ROLE)) {
            throw new IllegalStateException("Вы не оператор");
        }
    }

    public static void onlySupplier(User user) {
        if (!user.getRole().equals(SUPPLIER_ROLE)) {
            throw new IllegalStateException("Вы не дистрибутор");
        }
    }

    public static void acceptedStatus(boolean status) {
        if (!status) {
            throw new IllegalStateException("Вы отменили подтверждение");
        }
    }

    public static void checkBalance(User user, int amount) {
        if (user.getBalance() < amount) {
            throw new IllegalStateException("У вас недостаточно баланса");
        }
    }

    public static void checkCount(Product product, int count) {
        if (product.getMinCount() >= count)
            throw new IllegalStateException("Минимальное кол-во продуктов: " + product.getMinCount());
        if (product.getMaxCount() <= count)
            throw new IllegalStateException("Максимальное кол-во продуктов: " + product.getMaxCount());
    }
}
