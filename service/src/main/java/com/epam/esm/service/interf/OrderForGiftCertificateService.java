package com.epam.esm.service.interf;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.OrderForGiftCertificateDto;
import com.epam.esm.dto.OrderItemDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
public interface OrderForGiftCertificateService {
    List<OrderForGiftCertificateDto> getUserOrders(@Positive long userId,
                                                   @Valid CriteriaDto criteriaDto) throws ValidateException, ServiceException;

    OrderForGiftCertificateDto create(@Positive long customerId,
                                      @NotEmpty List<@Valid OrderItemDto> orderItems) throws ValidateException, ServiceException;
}
