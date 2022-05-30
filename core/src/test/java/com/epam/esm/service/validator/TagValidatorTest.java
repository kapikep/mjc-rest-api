package com.epam.esm.service.validator;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.ValidateException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TagValidatorTest {

    @Test
    void tagFieldValidatorAllOk() throws ValidateException {
        Tag tag = new Tag(1, "name");
        TagValidator.tagFieldValidator(tag);
    }

    @Test
    void tagFieldException() throws ValidateException {
        Tag tag = new Tag(-1, "name");
        ValidateException e = assertThrows(ValidateException.class, () -> TagValidator.tagFieldValidator(tag));
        assertEquals(Arrays.asList("incorrect.tag.id"), e.getResourceBundleCodeList());

        tag.setName("");
        e = assertThrows(ValidateException.class, () -> TagValidator.tagFieldValidator(tag));
        assertEquals(Arrays.asList("incorrect.tag.id", "incorrect.tag.name"), e.getResourceBundleCodeList());

        tag.setId(999);
        e = assertThrows(ValidateException.class, () -> TagValidator.tagFieldValidator(tag));
        assertEquals(Arrays.asList("incorrect.tag.name"), e.getResourceBundleCodeList());
    }

    @Test
    void allNotNullFieldValidation() throws ValidateException {
        Tag tag = new Tag(1, "name");
        assertTrue(TagValidator.allNotNullFieldValidation(tag));

        tag.setId(0);
        assertThrows(ValidateException.class, () -> TagValidator.allNotNullFieldValidation(tag));

        tag.setId(99);
        tag.setName(null);
        assertFalse(TagValidator.allNotNullFieldValidation(tag));
    }
}