package com.epam.esm.service.util;

import com.epam.esm.service.exception.ValidateException;

public class ServiceUtil {

    public static int parseInt(String str) throws ValidateException {
        int i;
        try {
            i = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new ValidateException("incorrect.parameter", new Object[]{str});
        }
        return i;
    }

    public static long parseLong(String str) throws ValidateException {
        long i = 0;
        try {
            i = Long.parseLong(str);
        } catch (NumberFormatException e) {
            throw new ValidateException("incorrect.parameter", new Object[]{str});
        }
        return i;
    }
}
