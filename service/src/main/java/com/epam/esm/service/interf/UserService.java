package com.epam.esm.service.interf;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.OrderForGiftCertificateDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
public interface UserService {
    List<UserDto> readPage(@Valid CriteriaDto cr) throws ValidateException, ServiceException;

    UserDto readOne(@Positive long id) throws ValidateException, ServiceException;

    public List<OrderForGiftCertificateDto> getUserOrders(@Positive long customerId,
                                                          @Valid CriteriaDto cr) throws ValidateException, ServiceException;
}
