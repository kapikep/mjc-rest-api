package com.epam.esm.service.impl;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.GiftCertificateService;
import com.epam.esm.service.interf.TagService;
import com.epam.esm.service.util.GiftCertificateUtil;
import com.epam.esm.service.util.ServiceUtil;
import com.epam.esm.service.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.List;

import static com.epam.esm.service.util.CriteriaUtil.*;
import static com.epam.esm.service.util.GiftCertificateUtil.giftCertificateDtoToEntityTransfer;

/**
 * Service for gift certificates
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Service
@Validated
public class GiftCertificateServiceImpl implements GiftCertificateService {
    public static final String RESOURCE_NOT_FOUND = "error.resource.not.found";
    public static final String INCORRECT_ID = "incorrect.search.id";
    public static final String CANNOT_ADD_OR_UPDATE_A_CHILD_ROW = "Cannot add or update a child row";

    private final GiftCertificateRepository repository;
    private final TagService tagService;

    @Autowired
    private GiftCertificateValidator gfValidator;

    @Autowired
    private Validator validator;

    public GiftCertificateServiceImpl(GiftCertificateRepository repository, TagService tagService) {
        this.repository = repository;
        this.tagService = tagService;
    }

    @Override
    public List<GiftCertificateDto> readPage(CriteriaDto crDto) throws ServiceException, ValidateException {
        setDefaultPageValIfEmpty(crDto);
//        giftCertificateCriteriaFieldValidation(crDto);
        CriteriaEntity cr = criteriaDtoToEntityConverting(crDto);
        List<GiftCertificateDto> giftCertificates;
        try {
            giftCertificates = GiftCertificateUtil.giftCertificateEntityListToDtoConverting(repository.readPage(cr));
            crDto.setTotalSize(cr.getTotalSize());
        } catch (RepositoryException | DataAccessException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return giftCertificates;
    }

    /**
     * Validates id, parse to int and reads gift certificates by id from repository
     *
     * @return gift certificate from repository
     */
    @Override
    public GiftCertificateDto readOne(String idStr) throws ServiceException, ValidateException {
        int id = ServiceUtil.parseInt(idStr);
        return getGiftCertificateDto(id);
    }

    /**
     * Validates id and reads gift certificates by id from repository
     *
     * @return gift certificate from repository
     */
    @Override
    public GiftCertificateDto readOne(long id) throws ServiceException, ValidateException {
        if (!GiftCertificateValidator.idValidation(id)) {
            throw new ValidateException(INCORRECT_ID, id);
        }
        return getGiftCertificateDto(id);
    }

    @Override
    public List<GiftCertificateDto> find(CriteriaDto crDto) throws ServiceException, ValidateException {
        setDefaultPageValIfEmpty(crDto);
        giftCertificateCriteriaFieldValidation(crDto);
        List<GiftCertificateDto> giftCertificates;
        CriteriaEntity cr = criteriaDtoToEntityConverting(crDto);
        try {
            giftCertificates = GiftCertificateUtil
                    .giftCertificateEntityListToDtoConverting(repository.findByParams(cr));
            crDto.setTotalSize(cr.getTotalSize());
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return giftCertificates;
    }

    /**
     * Validates gift certificate fields. Sets create date and last update date to now unless otherwise specified in the field.
     * Creates gift certificate in repository
     */
    @Override
    public void create(GiftCertificateDto giftDto) throws ServiceException, ValidateException {
        giftDto.setId(0);
        giftDto.setCreateDate(LocalDateTime.now());
        giftDto.setLastUpdateDate(LocalDateTime.now());

//        System.out.println(validator.validate(giftDto));
//        gfValidator.giftCertificateFieldValid(giftDto);
//        GiftCertificateValidator.giftCertificateFieldValidation(giftDto);

        try {
            List<TagDto> tags = giftDto.getTags();
//            tagService.getIdOrCreateTagsInList(tags);
            GiftCertificateEntity giftEntity = giftCertificateDtoToEntityTransfer(giftDto);
            giftEntity = repository.update(giftEntity);
            GiftCertificateUtil.updateFieldsInDtoFromEntity(giftEntity, giftDto);
        } catch (RepositoryException e) {
            String mes = e.getMessage();

            if (mes != null && mes.contains(CANNOT_ADD_OR_UPDATE_A_CHILD_ROW)) {
                throw new ServiceException(e.getMessage(), e, "create.problem");
            }
            throw new ServiceException(mes, e);
        }
    }

    /**
     * Validates gift certificate fields. Sets last update date to now unless otherwise specified in the field.
     * Updates gift certificate in repository
     */
    @Override
    //TODO "{tags[0].id=must be greater than 0}

    public void update(GiftCertificateDto giftDto) throws ServiceException, ValidateException {
        giftDto.setLastUpdateDate(LocalDateTime.now());

        try {
            if (GiftCertificateValidator.isNullFieldValidation(giftDto)) {
                GiftCertificateUtil.updateFields(giftDto, readOne(giftDto.getId()));
            }
            GiftCertificateValidator.giftCertificateFieldValidation(giftDto);
//            tagService.getIdOrCreateTagsInList(giftDto.getTags());

            repository.update(giftCertificateDtoToEntityTransfer(giftDto));
        } catch (RepositoryException e) {
            String mes = e.getMessage();

            if (mes != null && mes.contains("0 updated rows")) {
                throw new ServiceException(e.getMessage(), e, RESOURCE_NOT_FOUND, giftDto.getId());
            }
            if (mes != null && mes.contains(CANNOT_ADD_OR_UPDATE_A_CHILD_ROW)) {
                throw new ServiceException(e.getMessage(), e, "update.problem");
            }
            throw new ServiceException(mes, e);
        }
    }

    /**
     * Validates id and deletes gift certificate
     *
     * @param id id gift certificate to delete
     */
    @Override
    public void delete(long id) throws ServiceException, ValidateException {
        try {
            repository.deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e, RESOURCE_NOT_FOUND, id);
        }
    }

    private GiftCertificateDto getGiftCertificateDto(long id) throws ServiceException {
        GiftCertificateEntity entity;
        GiftCertificateDto dto;
        try {
            entity = repository.readOne(id);
            dto = GiftCertificateUtil.giftCertificateEntityToDtoTransfer(entity);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e, RESOURCE_NOT_FOUND, id);
        }
        return dto;
    }
}
