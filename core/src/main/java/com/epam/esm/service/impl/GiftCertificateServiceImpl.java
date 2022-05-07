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

    @Override
    public List<GiftCertificateDto> readAllGiftCertificates() throws ServiceException, ValidateException {
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

    @Override
    public GiftCertificateDto readGiftCertificate(String idStr) throws ServiceException, ValidateException {
        int id = ServiceUtil.parseInt(idStr);
        return getGiftCertificateDto(id);
    }

    @Override
    public GiftCertificateDto readGiftCertificate(int id) throws ServiceException, ValidateException {
        if(!GiftCertificateValidator.idValidation(id)){
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

    @Override
    public void createGiftCertificate(GiftCertificateDto giftDto) throws ServiceException, ValidateException {
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
            for (Tag tag : tags) {
                tag.setId(tagService.getTagIdOrCreateNewTag(tag));
            }
            repository.createGiftCertificate(gift, tags);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

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
            for (Tag tag : tags) {
                tag.setId(tagService.getTagIdOrCreateNewTag(tag));
            }
            repository.updateGiftCertificate(gift, tags);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteGiftCertificate(String idStr) throws ServiceException, ValidateException {
        int id = ServiceUtil.parseInt(idStr);
        try {
            repository.deleteGiftCertificate(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<GiftCertificateDto> findGiftCertificates(Map<String, String> criteriaMap, String sorting) throws ServiceException, ValidateException {
        //TODO Criteria validation
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
