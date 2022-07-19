package com.epam.esm.service.interf;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.OrderForGiftCertificateDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface OrderForGiftCertificateService {
    List<OrderForGiftCertificateDto> getUserOrders(long userId, CriteriaDto criteriaDto) throws ValidateException, ServiceException;
}
