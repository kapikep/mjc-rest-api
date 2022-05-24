package com.epam.esm.service.validator;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.service.exception.ValidateException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TagValidatorTest {

    private TagDto getTagId1() {
        return new TagDto(1, "Sport");
    }

    @Test
    void tagFieldValidatorAllOk() throws ValidateException {
        TagDto tag = getTagId1();
        TagValidator.tagFieldValidator(tag);
    }

    @Test
    void tagFieldException() throws ValidateException {
        TagDto tag = getTagId1();
        tag.setId(-1);

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
        TagDto tag = getTagId1();
        assertTrue(TagValidator.allNotNullFieldValidation(tag));

        tag.setId(0);
        assertThrows(ValidateException.class, () -> TagValidator.allNotNullFieldValidation(tag));

        tag.setId(99);
        tag.setName(null);
        assertFalse(TagValidator.allNotNullFieldValidation(tag));
    }
}