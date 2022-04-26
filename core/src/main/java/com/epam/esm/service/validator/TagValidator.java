package com.epam.esm.service.validator;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.LocaleResolver;

public class TagValidator {

    private static final int MAX_ID = 1_000_000;
    private static final int MAX_NAME_LENGHT = 45;

    public static void tagFieldValidator(Tag tag) throws ValidateException {
        StringBuilder resMes = new StringBuilder();
        if (!idValidation(tag.getId())) {
            resMes.append("Wrong tag id ");
        }
        if (!nameValidation(tag.getName())) {
            resMes.append("Wrong tag name ");
        }

        if (resMes.length() != 0) {
            throw new ValidateException(resMes.toString());
        }
    }

    public static boolean idValidation(int id) {
        boolean res = true;

        if (id < 0 || id > MAX_ID) {
            res = false;
        }
        return res;
    }

    public static boolean nameValidation(String name) {
        boolean res = true;

        if (name != null) {
            if (name.isEmpty() || name.length() > MAX_NAME_LENGHT) {
                res = false;
            }
        } else {
            res = false;
        }
        return res;
    }

    public static boolean allNotNullFieldValidation(GiftCertificate g) throws ValidateException {
        boolean res = true;

        if (g.getId() == 0) {
            throw new ValidateException("No Id");
        }
        if (res && g.getName() == null) {
            res = false;
        }
        return res;
    }
}
