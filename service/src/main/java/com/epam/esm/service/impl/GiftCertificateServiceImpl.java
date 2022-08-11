package com.epam.esm.service.impl;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.GiftCertificateDto;
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
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.epam.esm.service.util.CriteriaUtil.criteriaDtoToEntityConverting;
import static com.epam.esm.service.util.CriteriaUtil.setDefaultPageValIfEmpty;
import static com.epam.esm.service.util.GiftCertificateUtil.giftCertificateDtoToEntityTransfer;
import static com.epam.esm.service.util.GiftCertificateUtil.giftCertificateEntityToDtoTransfer;
import static com.epam.esm.service.util.GiftCertificateUtil.sortingValidation;
import static com.epam.esm.service.util.GiftCertificateUtil.updateFieldsInDtoFromEntity;

/**
 * Service for gift certificates
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    public static final String RESOURCE_NOT_FOUND = "error.resource.not.found";
    public static final String CANNOT_ADD_OR_UPDATE_A_CHILD_ROW = "Cannot add or update a child row";

    private final GiftCertificateRepository giftCertificateRepository;
    private final TagService tagService;

    @Override
    public List<GiftCertificateDto> readAllGiftCertificatesPaginated(CriteriaDto crDto) throws ServiceException, ValidateException {
        setDefaultPageValIfEmpty(crDto);
        sortingValidation(crDto);
        CriteriaEntity cr = criteriaDtoToEntityConverting(crDto);
        List<GiftCertificateDto> giftCertificates;
        try {
            giftCertificates = GiftCertificateUtil.giftCertificateEntityListToDtoConverting(giftCertificateRepository.readAllPaginated(cr));
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
    public GiftCertificateDto readGiftCertificateById(String idStr) throws ServiceException, ValidateException {
        int id = ServiceUtil.parseInt(idStr);
        return getGiftCertificateDto(id);
    }

    /**
     * Validates id and reads gift certificates by id from repository
     *
     * @return gift certificate from repository
     */
    @Override
    public GiftCertificateDto readGiftCertificateById(long id) throws ServiceException{
        return getGiftCertificateDto(id);
    }

    @Override
    public List<GiftCertificateDto> findGiftCertificatesByCriteria(CriteriaDto crDto) throws ServiceException, ValidateException {
        setDefaultPageValIfEmpty(crDto);
        sortingValidation(crDto);
        List<GiftCertificateDto> giftCertificates;
        CriteriaEntity cr = criteriaDtoToEntityConverting(crDto);
        try {
            giftCertificates = GiftCertificateUtil
                    .giftCertificateEntityListToDtoConverting(giftCertificateRepository.findByCriteria(cr));
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
    @Transactional
    public void createGiftCertificate(GiftCertificateDto giftDto) throws ServiceException, ValidateException {
        giftDto.setId(0);
        tagService.setIdOrCreateTags(giftDto.getTags());
        try {
            GiftCertificateEntity giftEntity = giftCertificateDtoToEntityTransfer(giftDto);
            giftCertificateRepository.create(giftEntity);
            updateFieldsInDtoFromEntity(giftEntity, giftDto);
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
    @Transactional
    public void updateGiftCertificate(GiftCertificateDto giftDto) throws ServiceException, ValidateException {
        GiftCertificateEntity giftEntity;
        try {
            if (GiftCertificateUtil.isNullFieldValidation(giftDto)) {
                GiftCertificateUtil.updateFields(giftDto, readGiftCertificateById(giftDto.getId()));
            }
            tagService.setIdOrCreateTags(giftDto.getTags());
            giftEntity = giftCertificateDtoToEntityTransfer(giftDto);
            giftCertificateRepository.merge(giftEntity);
            updateFieldsInDtoFromEntity(giftEntity, giftDto);
            giftDto.setLastUpdateDate(LocalDateTime.now());
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
    public void deleteGiftCertificateById(long id) throws ServiceException{
        try {
            giftCertificateRepository.deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e, RESOURCE_NOT_FOUND, id);
        }
    }

    private GiftCertificateDto getGiftCertificateDto(long id) throws ServiceException {
        GiftCertificateEntity entity;
        GiftCertificateDto dto;
        try {
            entity = giftCertificateRepository.readById(id);
            dto = giftCertificateEntityToDtoTransfer(entity);
        } catch (RepositoryException | ValidateException e) {
            throw new ServiceException(e.getMessage(), e, RESOURCE_NOT_FOUND, id);
        }
        return dto;
    }
}
