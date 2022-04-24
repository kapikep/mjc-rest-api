package com.epam.esm.service.validator;

import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;

import java.util.regex.Pattern;

public class ServiceUtil {

    public static int parseInt(String str, int defaultVal) {
        int i = defaultVal;
        if (str != null && Pattern.matches("\\d+", str)) {
            i = Integer.parseInt(str);
        }
        return i;
    }

    public static int parseInt(String str) throws ValidateException {
        int i = 0;
        if (str != null && Pattern.matches("\\d+", str)) {
            i = Integer.parseInt(str);
        } else {
            throw new ValidateException("Incorrect parameter " + str);
        }
        return i;
    }

    public static double parseDouble(String str) throws ValidateException {
        double d = 0;
        if (str != null) {
            d = Double.parseDouble(str);
        } else {
            throw new ValidateException("Incorrect parameter " + str);
        }
        return d;
    }

}
