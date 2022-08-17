package com.epam.esm.service.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.validator.groups.OnCreate;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDtoValidatorTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private Set<ConstraintViolation<UserDto>> constraintViolations;

    @Test
    void onCreateUserValidatorTest(){
    }
}
