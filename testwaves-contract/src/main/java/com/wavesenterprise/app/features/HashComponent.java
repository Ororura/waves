package com.wavesenterprise.app.features;

import java.util.List;

public class HashComponent {
    public static boolean hasCommonElement(List<String> list1, List<String> list2) {
        for (String item1 : list1) {
            for (String item2 : list2) {
                if (item1.equals(item2)) {
                    return true;
                }
            }
        }
        return false;
    }
}
