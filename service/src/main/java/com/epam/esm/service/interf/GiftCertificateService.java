package com.epam.esm.service.interf;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.validator.groups.OnCreate;
import com.epam.esm.service.validator.groups.OnUpdate;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Service for gift certificates
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Validated
public interface GiftCertificateService {

    List<GiftCertificateDto> readPage(@Valid CriteriaDto crDto) throws ServiceException, ValidateException;

    List<GiftCertificateDto> find(@Valid CriteriaDto cr) throws ServiceException, ValidateException;

    /**
     * Validates id and reads gift certificates by id from repository
     *
     * @return gift certificate from repository
     */
    GiftCertificateDto readOne(String id) throws ServiceException, ValidateException;

    /**
     * Validates id and reads gift certificates by id from repository
     *
     * @return gift certificate from repository
     */

    GiftCertificateDto readOne(@Positive long id) throws ServiceException, ValidateException;

    @Validated(OnCreate.class)
    void create(@Valid GiftCertificateDto dto) throws ServiceException, ValidateException;

    @Validated(OnUpdate.class)
    void update(@Valid GiftCertificateDto dto) throws ServiceException, ValidateException;

    void delete(@Positive long idStr) throws ServiceException, ValidateException;
}
