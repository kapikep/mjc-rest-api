package com.epam.esm.service.utils;

import com.epam.esm.service.exception.ValidateException;

import java.util.regex.Pattern;

public class ServiceUtil {

    public static int parseInt(String str) throws ValidateException {
        int i = 0;
        if (str != null) {
            i = Integer.parseInt(str);
        } else {
            throw new ValidateException("incorrect.parameter", new Object[] {str});
        }
        return i;
    }

    public static long parseLong(String str) throws ValidateException {
        long i = 0;
        if (str != null) {
            i = Long.parseLong(str);
        } else {
            throw new ValidateException("incorrect.parameter", new Object[] {str});
        }
        return i;
    }
}
