package com.epam.esm.controller.util;

import com.epam.esm.service.exception.ValidateException;

/**
 * Utils for controller.
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public class ControllerUtil {
    /**
     * Validate id to zero.
     *
     * @param id id to validate.
     * @throws ValidateException with "error.gift.patch.id.in.body" resource bundle
     *                           if id is null.
     */
    public static void idInBodyValidation(long id) throws ValidateException {
        if (id != 0) {
            throw new ValidateException("error.gift.patch.id.in.body", (Object) null);
        }
    }
}
