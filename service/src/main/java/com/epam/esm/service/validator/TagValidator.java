package com.epam.esm.service.validator;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.exception.ValidateException;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Validator for tag
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public class TagValidator {
    private static final int MAX_ID = 1_000_000;
    private static final int MAX_NAME_LENGHT = 45;

    public static void tagFieldValidator(TagDto tag) throws ValidateException {
        List<String> resList = new ArrayList<>();

        if(tag == null){
            throw new ValidateException("tag.is.null");
        }
        if (!idValidation(tag.getId())) {
            resList.add("incorrect.tag.id");
        }
        if (!nameValidation(tag.getName())) {
            resList.add("incorrect.tag.name");
        }
        if (!resList.isEmpty()) {
            throw new ValidateException(resList);
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

    public static boolean allNotNullFieldValidation(TagDto g) throws ValidateException {
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
