package com.epam.esm.service.impl;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.OrderForGiftCertificateDto;
import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.OrderForGiftCertificateRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.OrderForGiftCertificateService;
import com.epam.esm.service.util.OrderForGiftCertificateUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.service.util.CriteriaUtil.criteriaDtoToEntityConverting;
import static com.epam.esm.service.util.CriteriaUtil.setDefaultPageValIfEmpty;

@Service
public class OrderForGiftCertificateServiceImpl implements OrderForGiftCertificateService {
    private final OrderForGiftCertificateRepository repository;

    public OrderForGiftCertificateServiceImpl(OrderForGiftCertificateRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<OrderForGiftCertificateDto> getUserOrders(long userId, CriteriaDto crDto) throws ValidateException, ServiceException {
        setDefaultPageValIfEmpty(crDto);
        CriteriaEntity cr = criteriaDtoToEntityConverting(crDto);
        List<OrderForGiftCertificateDto> orders;
        try {
            orders = OrderForGiftCertificateUtil
                    .OrderForGiftCertificateEntityListToDtoConverting(repository.getUserOrders(userId, cr));
        } catch (RepositoryException | DataAccessException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        crDto.setTotalSize(cr.getTotalSize());
        return orders;
    }
}
