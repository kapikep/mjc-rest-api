package com.epam.esm.service.interf;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.OrderForGiftCertificateDto;
import com.epam.esm.dto.OrderItemDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Service interface for gift certificate orders
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Validated
public interface OrderForGiftCertificateService {
    /**
     * Validate CriteriaDto.
     * Set default value in CriteriaDto.
     * Read OrderForGiftCertificateEntities from repository by user id.
     * and converting it to OrderForGiftCertificateDto list.
     * Set total size.
     *
     * @param userId user id to search for OrdersForGiftCertificate.
     * @param crDto  CriteriaEntity with params for pagination.
     * @return OrderForGiftCertificateDto list.
     * @throws ServiceException  if page or size is null or less 1.
     *                           If any RepositoryException or DataAccessException has occurred.
     * @throws ValidateException if sorting field does not match ORDER_SORT_PARAM.
     */
    List<OrderForGiftCertificateDto> readUserOrdersForGiftCertificatePaginated(@Positive long userId,
                                                                               @Valid CriteriaDto crDto) throws ValidateException, ServiceException;

    /**
     * Validate OrderItemDto list fields OnCreate group.
     * Validate customerId. It must be positive.
     * Read user by customerId.
     * Read gift certificate by OrderItemDto giftCertificate id field.
     * Set giftCertificate with full data from repository to OrderItemDto.
     * Calculate total price.
     * Convert OrderForGiftCertificateDto to OrderForGiftCertificateEntity.
     * Create new OrderForGiftCertificateEntity in repository.
     *
     * @param customerId customer id for the OrderForGiftCertificate.
     * @param orderItems order items for the OrderForGiftCertificate.
     * @throws ServiceException if user with this customerId does not exist in repository.
     *                          If gift certificate does not exist in repository.
     *                          If any IllegalArgumentException or  has occurred.
     */
    OrderForGiftCertificateDto createOrderForGiftCertificate(@Positive long customerId,
                                                             @NotEmpty List<@Valid OrderItemDto> orderItems) throws ServiceException;
}
