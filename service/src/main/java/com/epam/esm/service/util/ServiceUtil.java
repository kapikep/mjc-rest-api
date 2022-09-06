package com.epam.esm.service.util;

import com.epam.esm.service.exception.ValidateException;

/**
 * Utils for service
 *
 * @author Artsemi Kapitula
 * @version 2.0
 */
public class ServiceUtil {
    private static final String INCORRECT_PARAMETER = "incorrect.parameter";

    /**
     * Parses the string argument as a signed decimal integer.
     *
     * @param str a String containing the int representation to be parsed
     * @return the integer value represented by the argument in decimal.
     * @throws ValidateException if the string does not contain a parsable integer.
     */
    public static int parseInt(String str) throws ValidateException {
        int i;
        try {
            i = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new ValidateException(INCORRECT_PARAMETER, str);
        }
        return i;
    }

    /**
     * Parses the string argument as a signed decimal long.
     *
     * @param str a String containing the long representation to be parsed
     * @return the long represented by the argument in decimal
     * @throws ValidateException if the string does not contain a parsable long
     */
    public static long parseLong(String str) throws ValidateException {
        long i;
        try {
            i = Long.parseLong(str);
        } catch (NumberFormatException e) {
            throw new ValidateException(INCORRECT_PARAMETER, str);
        }
        return i;
    }
}
