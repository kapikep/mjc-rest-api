package com.epam.esm.service.validator;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.validator.groups.OnCreate;
import com.epam.esm.service.validator.groups.OnUpdate;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagDtoValidatorTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private Set<ConstraintViolation<TagDto>> constraintViolations;

    @Test
    void onCreateNullNameTagValidatorTest() {
        TagDto tag = new TagDto(1, null);

        constraintViolations = validator.validate(tag, OnCreate.class);
        assertEquals(1, constraintViolations.size());

        tag.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        constraintViolations = validator.validate(tag, OnCreate.class);
        assertEquals(1, constraintViolations.size());
    }

    @Test
    void onUpdateTagValidatorTest() {
        TagDto tag = new TagDto(-1, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        constraintViolations = validator.validate(tag, OnUpdate.class);
        assertEquals(2, constraintViolations.size());
    }
}
