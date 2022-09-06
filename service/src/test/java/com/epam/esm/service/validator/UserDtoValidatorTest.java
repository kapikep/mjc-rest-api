package com.epam.esm.service.validator;

import com.epam.esm.dto.UserDto;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class UserDtoValidatorTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private Set<ConstraintViolation<UserDto>> constraintViolations;

    @Test
    void onCreateUserValidatorTest(){
    }
}
