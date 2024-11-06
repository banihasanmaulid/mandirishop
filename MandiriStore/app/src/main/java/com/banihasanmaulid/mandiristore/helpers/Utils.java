package com.banihasanmaulid.mandiristore.helpers;

/**
 * Copyright (c) 2024 Mandiri-Store. All rights reserved. <br>
 * Created by {@author} <b>Bani Hasan Maulid</b> on {@since} <b>11/6/2024</b>
 */
public class Utils {
    public static boolean isEmptyOrNull(String value) {
        if (value == null) {
            return true;
        }

        if (value.equals(" ")) {
            return true;
        }

        if (value.equals("")) {
            return true;
        }

        if (value.isEmpty()) {
            return true;
        }

        return false;
    }
}
