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
 * Service interface for gift certificates
 *
 * @author Artsemi Kapitula
 * @version 2.0
 */
@Validated
public interface GiftCertificateService {
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
    List<GiftCertificateDto> readGiftCertificatesPaginated(@Valid CriteriaDto crDto) throws ServiceException, ValidateException;

    /**
     * Validate id. It must be positive.
     * Read GiftCertificateEntity by id from repository and convert it to GiftCertificateDto.
     *
     * @param id unique identifier of the gift certificate to search for.
     * @return GiftCertificateDto by id.
     * @throws ServiceException if GiftCertificateDto with this id does not exist.
     *                          If any RepositoryException or DataAccessException has occurred.
     */
    GiftCertificateDto readGiftCertificateById(@Positive long id) throws ServiceException;

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
    List<GiftCertificateDto> findGiftCertificatesByCriteria(@Valid CriteriaDto crDto) throws ServiceException, ValidateException;


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
    @Validated(OnCreate.class)
    void createGiftCertificate(@Valid GiftCertificateDto giftDto) throws ServiceException, ValidateException;

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
    @Validated(OnUpdate.class)
    void updateGiftCertificate(@Valid GiftCertificateDto giftDto) throws ServiceException, ValidateException;

    /**
     * Validates id. It must be positive.
     * Delete gift certificate by id from repository.
     *
     * @param id unique identifier of the gift certificate to delete from repository.
     * @throws ServiceException if gift certificate with this id does not exist in repository.
     */
    void deleteGiftCertificateById(@Positive long id) throws ServiceException;
}
