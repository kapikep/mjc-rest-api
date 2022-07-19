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

import java.util.List;

@Validated
public interface UserService {
    List<UserDto> readPage(CriteriaDto cr) throws ValidateException, ServiceException;

    UserDto readOne(long id) throws ValidateException, ServiceException;

    public List<OrderForGiftCertificateDto> getUserOrders(long customerId, CriteriaDto cr) throws ValidateException, ServiceException;
}
