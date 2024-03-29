package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.GiftCertificateService;
import com.epam.esm.service.interf.TagService;
import com.epam.esm.service.utils.GiftCertificateUtil;
import com.epam.esm.service.utils.ServiceUtil;
import com.epam.esm.service.validator.GiftCertificateValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
/**
 * Service for gift certificates
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    public static final String RESOURCE_NOT_FOUND = "resource.not.found";
    public static final String INCORRECT_ID = "incorrect.id";
    private final GiftCertificateRepository repository;
    private final TagService tagService;

    public GiftCertificateServiceImpl(GiftCertificateRepository repository, TagService tagService) {
        this.repository = repository;
        this.tagService = tagService;
    }

    /**
     * Reads all gift certificates from repository
     *
     * @return list with all GiftCertificateDto
     */
    @Override
    public List<GiftCertificateDto> readAllGiftCertificates() throws ServiceException {
        List<GiftCertificate> giftCertificates;
        List<GiftCertificateDto> giftCertificateDtoList;
        try {
            giftCertificates = repository.readAllGiftCertificates();
            giftCertificateDtoList = GiftCertificateUtil.giftCertificateEntityListToDtoConverting(giftCertificates);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return giftCertificateDtoList;
    }

    /**
     * Validates id, parse to int and reads gift certificates by id from repository
     *
     * @return gift certificate from repository
     */
    @Override
    public GiftCertificateDto readGiftCertificate(String idStr) throws ServiceException, ValidateException {
        int id = ServiceUtil.parseInt(idStr);
        return getGiftCertificateDto(id);
    }

    /**
     * Validates id and reads gift certificates by id from repository
     *
     * @return gift certificate from repository
     */
    @Override
    public GiftCertificateDto readGiftCertificate(int id) throws ServiceException, ValidateException {
        if (!GiftCertificateValidator.idValidation(id)) {
            throw new ValidateException(INCORRECT_ID, id);
        }
        return getGiftCertificateDto(id);
    }

    private GiftCertificateDto getGiftCertificateDto(int id) throws ServiceException {
        List<GiftCertificate> giftCertificates;
        List<GiftCertificateDto> giftCertificateDtoList;
        try {
            giftCertificates = repository.readGiftCertificate(id);

            if (giftCertificates.isEmpty()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, id);
            }
            giftCertificateDtoList = GiftCertificateUtil.giftCertificateEntityListToDtoConverting(giftCertificates);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return giftCertificateDtoList.get(0);
    }

    /**
     * Validates gift certificate fields. Sets create date and last update date to now unless otherwise specified in the field.
     * Creates gift certificate in repository
     *
     * @return id for created tag
     */
    @Override
    public int createGiftCertificate(GiftCertificateDto giftDto) throws ServiceException, ValidateException {
        int id;
        if (giftDto.getCreateDate() == null) {
            giftDto.setCreateDate(LocalDateTime.now());
        }
        if (giftDto.getLastUpdateDate() == null) {
            giftDto.setLastUpdateDate(LocalDateTime.now());
        }
        GiftCertificateValidator.giftCertificateFieldValidation(giftDto);
        GiftCertificate gift = GiftCertificateUtil.giftCertificateDtoToEntityTransfer(giftDto);

        try {
            List<Tag> tags = giftDto.getTags();

            tagService.getIdOrCreateTagsInList(tags);

            id = repository.createGiftCertificate(gift, tags);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return id;
    }

    /**
     * Validates gift certificate fields. Sets last update date to now unless otherwise specified in the field.
     * Updates gift certificate in repository
     */
    @Override
    public void updateGiftCertificate(GiftCertificateDto giftDto) throws ServiceException, ValidateException {
        if (giftDto.getLastUpdateDate() == null) {
            giftDto.setLastUpdateDate(LocalDateTime.now());
        }
        try {
            if (!GiftCertificateValidator.allNotNullFieldValidation(giftDto)) {
                GiftCertificateDto oldGiftCertificateDto = readGiftCertificate(giftDto.getId());
                GiftCertificateUtil.updateFields(giftDto, oldGiftCertificateDto);
            }
            GiftCertificateValidator.giftCertificateFieldValidation(giftDto);
            GiftCertificate gift = GiftCertificateUtil.giftCertificateDtoToEntityTransfer(giftDto);
            List<Tag> tags = giftDto.getTags();

            tagService.getIdOrCreateTagsInList(giftDto.getTags());

            repository.updateGiftCertificate(gift, tags);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * Validates id and deletes gift certificate
     *
     * @param idStr id gift certificate to delete
     */
    @Override
    public void deleteGiftCertificate(String idStr) throws ServiceException, ValidateException {
        int id = ServiceUtil.parseInt(idStr);
        try {
            repository.deleteGiftCertificate(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * Validate parameters. Finds gift certificate in repository by parameters
     *
     * @param criteriaMap search parameters
     * @param sorting sorting rules
     */
    @Override
    public List<GiftCertificateDto> findGiftCertificates(Map<String, String> criteriaMap, String sorting) throws ServiceException, ValidateException {
        GiftCertificateValidator.giftCertificateCriteriaValidation(criteriaMap, sorting);
        List<GiftCertificate> giftCertificates;
        List<GiftCertificateDto> giftCertificateDtoList;
        try {
            giftCertificates = repository.findGiftCertificate(criteriaMap, sorting);
            giftCertificateDtoList = GiftCertificateUtil.giftCertificateEntityListToDtoConverting(giftCertificates);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return giftCertificateDtoList;
    }
}
