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
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.epam.esm.repository.constant.ExceptionMes.CANNOT_ADD_OR_UPDATE_A_CHILD_ROW;
import static com.epam.esm.repository.constant.ExceptionMes.INCORRECT_RESULT_SIZE_EXPECTED_1_ACTUAL_0;
import static com.epam.esm.service.util.CriteriaUtil.criteriaDtoToEntityConverting;
import static com.epam.esm.service.util.CriteriaUtil.setDefaultPageValIfEmpty;
import static com.epam.esm.service.util.GiftCertificateUtil.giftCertificateDtoToEntityConverting;
import static com.epam.esm.service.util.GiftCertificateUtil.giftCertificateEntityToDtoConverting;
import static com.epam.esm.service.util.GiftCertificateUtil.giftCertificateSearchParamKeyValidation;
import static com.epam.esm.service.util.GiftCertificateUtil.giftCertificateSortingValidation;
import static com.epam.esm.service.util.GiftCertificateUtil.updateFieldsInDtoFromEntity;
import static com.epam.esm.service.util.GiftCertificateUtil.updateNonNullFieldsFromDtoToEntity;

/**
 * Service for gift certificates.
 *
 * @author Artsemi Kapitula
 * @version 2.0
 */
@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final String RESOURCE_NOT_FOUND = "error.resource.not.found";
    private static final String CREATE_PROBLEM = "create.problem";
    private static final String UPDATE_PROBLEM = "update.problem";

    private final GiftCertificateRepository giftCertificateRepository;
    private final TagService tagService;

    /**
     * Validate CriteriaDto.
     * Set default value in CriteriaDto.
     * Read GiftCertificateEntities paginated from repository and converting it to GiftCertificateDto list.
     * Set total size.
     *
     * @param crDto CriteriaEntity with params for pagination.
     * @return GiftCertificateDto list.
     * @throws ServiceException  if page or size is null or less 1.
     *                           If any RepositoryException or DataAccessException has occurred.
     * @throws ValidateException if sorting field does not match GIFT_CERTIFICATE_SORT_PARAM.
     */
    @Override
    public List<GiftCertificateDto> readGiftCertificatesPaginated(CriteriaDto crDto) throws ServiceException, ValidateException {
        setDefaultPageValIfEmpty(crDto);
        giftCertificateSortingValidation(crDto);
        CriteriaEntity cr = criteriaDtoToEntityConverting(crDto);
        List<GiftCertificateDto> giftCertificates;
        try {
            giftCertificates = GiftCertificateUtil
                    .giftCertificateEntityListToDtoConverting(giftCertificateRepository.readPaginated(cr));
            crDto.setTotalSize(cr.getTotalSize());
        } catch (RepositoryException | DataAccessException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return giftCertificates;
    }

    /**
     * Validate id. It must be positive.
     * Read GiftCertificateEntity by id from repository and convert it to GiftCertificateDto.
     *
     * @param id unique identifier of the gift certificate to search for.
     * @return GiftCertificateDto by id.
     * @throws ServiceException if GiftCertificateDto with this id does not exist.
     *                          If any RepositoryException or DataAccessException has occurred.
     */
    @Override
    public GiftCertificateDto readGiftCertificateById(long id) throws ServiceException {
        GiftCertificateDto giftDto;
        try {
            giftDto = giftCertificateEntityToDtoConverting(giftCertificateRepository.readById(id));
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e, RESOURCE_NOT_FOUND, id);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return giftDto;
    }

    /**
     * Validate CriteriaDto.
     * Find GiftCertificateEntity by criteria map and sort by sorting param paginated from repository.
     * Convert it to GiftCertificateDto.
     *
     * @param crDto CriteriaEntity with criteria map and sorting param.
     * @return GiftCertificateDto.
     * @throws ServiceException  if any RepositoryException has occurred.
     * @throws ValidateException if sorting field does not match GIFT_CERTIFICATE_SORT_PARAM.
     */
    @Override
    public List<GiftCertificateDto> findGiftCertificatesByCriteria(CriteriaDto crDto) throws ServiceException, ValidateException {
        List<GiftCertificateDto> giftCertificates;
        setDefaultPageValIfEmpty(crDto);
        giftCertificateSortingValidation(crDto);
        giftCertificateSearchParamKeyValidation(crDto);
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
     * Validate GiftCertificateDto fields OnCreate group.
     * Convert GiftCertificateDto to GiftCertificateEntity
     * Update tags id in tag list field from repository,
     * if tag not exist - creates new, and write id in tag list field
     * Create new GiftCertificateEntity in repository.
     *
     * @param giftDto GiftCertificateDto.
     * @throws ServiceException if any IllegalArgumentException has occurred.
     */
    @Override
    @Transactional
    public void createGiftCertificate(GiftCertificateDto giftDto) throws ServiceException {
        try {
            giftDto.setId(0);
            tagService.setIdOrCreateTags(giftDto.getTags());
            GiftCertificateEntity giftEntity = giftCertificateDtoToEntityConverting(giftDto);
            giftCertificateRepository.create(giftEntity);
            updateFieldsInDtoFromEntity(giftEntity, giftDto);
        } catch (IllegalArgumentException e) {
            String mes = e.getMessage();
            if (mes != null && mes.contains(CANNOT_ADD_OR_UPDATE_A_CHILD_ROW)) {
                throw new ServiceException(e.getMessage(), e, CREATE_PROBLEM);
            }
            throw new ServiceException(mes, e);
        }
    }

    /**
     * Validate GiftCertificateDto fields OnUpdate group.
     * Convert GiftCertificateDto to GiftCertificateEntity.
     * Update tags id in tag list field from repository,
     * if tag not exist - creates new, and write id in tag list field
     * Update GiftCertificateEntity in repository.
     *
     * @param giftDto GiftCertificateDto.
     * @throws ServiceException if entity with this id does not exist in repository.
     *                          If any RepositoryException or IllegalArgumentException has occurred.
     */
    @Override
    @Transactional
    public void updateGiftCertificate(GiftCertificateDto giftDto) throws ServiceException {
        try {
            GiftCertificateEntity entityFromDb = giftCertificateRepository
                    .readById(giftDto.getId());
            tagService.setIdOrCreateTags(giftDto.getTags());
            updateNonNullFieldsFromDtoToEntity(giftDto, entityFromDb);
            updateFieldsInDtoFromEntity(entityFromDb, giftDto);
            giftDto.setLastUpdateDate(LocalDateTime.now());
        } catch (RepositoryException | IllegalArgumentException e) {
            String mes = e.getMessage();
            if (mes != null && mes.contains(INCORRECT_RESULT_SIZE_EXPECTED_1_ACTUAL_0)) {
                throw new ServiceException(e.getMessage(), e, RESOURCE_NOT_FOUND, giftDto.getId());
            }
            if (mes != null && mes.contains(CANNOT_ADD_OR_UPDATE_A_CHILD_ROW)) {
                throw new ServiceException(e.getMessage(), e, UPDATE_PROBLEM);
            }
            throw new ServiceException(mes, e);
        }
    }

    /**
     * Validates id. It must be positive.
     * Delete gift certificate by id from repository.
     *
     * @param id unique identifier of the gift certificate to delete from repository.
     * @throws ServiceException if gift certificate with this id does not exist in repository.
     */
    @Override
    public void deleteGiftCertificateById(long id) throws ServiceException {
        try {
            giftCertificateRepository.deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e, RESOURCE_NOT_FOUND, id);
        }
    }
}
