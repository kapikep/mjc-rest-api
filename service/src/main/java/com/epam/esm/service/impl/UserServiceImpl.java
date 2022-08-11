package com.epam.esm.service.impl;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.OrderForGiftCertificateDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.UserRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.UserService;
import com.epam.esm.service.util.OrderForGiftCertificateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.service.util.CriteriaUtil.criteriaDtoToEntityConverting;
import static com.epam.esm.service.util.CriteriaUtil.setDefaultPageValIfEmpty;
import static com.epam.esm.service.util.UserUtil.sortingValidation;
import static com.epam.esm.service.util.UserUtil.userEntityListToDtoConverting;
import static com.epam.esm.service.util.UserUtil.userEntityToDtoTransfer;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    public static final String RESOURCE_NOT_FOUND = "error.resource.not.found";
    private final UserRepository userRepository;

    @Override
    public List<UserDto> readAllUsersPaginated(CriteriaDto crDto) throws ValidateException, ServiceException {
        setDefaultPageValIfEmpty(crDto);
        sortingValidation(crDto);
        CriteriaEntity cr = criteriaDtoToEntityConverting(crDto);
        List<UserDto> users;
        try {
            users = userEntityListToDtoConverting(userRepository.readAllPaginated(cr));
        } catch (RepositoryException | DataAccessException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        crDto.setTotalSize(cr.getTotalSize());
        return users;
    }

    @Override
    public UserDto readUserById(long id) throws ValidateException, ServiceException {
        UserDto dto;
        try{
            dto = userEntityToDtoTransfer(userRepository.readById(id));
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e, RESOURCE_NOT_FOUND, id);
        }
        return dto;
    }

    @Override
    public List<OrderForGiftCertificateDto> getUserOrdersForGiftCertificate(long customerId, CriteriaDto crDto)
            throws ValidateException, ServiceException {
        setDefaultPageValIfEmpty(crDto);
        sortingValidation(crDto);
        CriteriaEntity cr = criteriaDtoToEntityConverting(crDto);
        List<OrderForGiftCertificateDto> orders;
        try {
            orders = OrderForGiftCertificateUtil
                    .OrderForGiftCertificateEntityListToDtoConverting(userRepository.readById(customerId).getOrders());
        } catch (RepositoryException | DataAccessException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        crDto.setTotalSize(cr.getTotalSize());
        return orders;
    }
}
