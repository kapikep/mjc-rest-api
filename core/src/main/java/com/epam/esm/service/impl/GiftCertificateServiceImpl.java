package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.GiftCertificateService;
import com.epam.esm.service.utils.GiftCertificateUtil;
import com.epam.esm.service.validator.GiftCertificateValidator;
import com.epam.esm.service.utils.ServiceUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository repository;
    private final TagServiceImpl tagService;
    private final MessageSource source;

    public GiftCertificateServiceImpl(GiftCertificateRepository repository, TagServiceImpl tagService, MessageSource source) {
        this.repository = repository;
        this.tagService = tagService;
        this.source = source;
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
        List<GiftCertificate> giftCertificates;
        List<GiftCertificateDto> giftCertificateDtoList;
        try {
            giftCertificates = repository.readGiftCertificate(id);

            if (giftCertificates.isEmpty()) {
                throw new ServiceException(source.getMessage("message", new Object[] {id}, LocaleContextHolder.getLocale()));
            }
            giftCertificateDtoList = GiftCertificateUtil.giftCertificateEntityListToDtoConverting(giftCertificates);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return giftCertificateDtoList.get(0);
    }

    @Override
    public GiftCertificateDto readGiftCertificate(int id) throws ServiceException, ValidateException {
        GiftCertificateValidator.idValidation(id);
        List<GiftCertificate> giftCertificates;
        List<GiftCertificateDto> giftCertificateDtoList;
        try {
            giftCertificates = repository.readGiftCertificate(id);

            if (giftCertificates.isEmpty()) {
                throw new ServiceException(String.format("Requested id resource not found (id = %d)", id));
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
                int id;
                try {
                    id = tagService.readTagByName(tag.getName()).getId();
                } catch (ServiceException e) {
                    tagService.createTag(tag);
                    id = tagService.readTagByName(tag.getName()).getId();
                }
                tag.setId(id);
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
                int id;
                try {
                    id = tagService.readTagByName(tag.getName()).getId();
                } catch (ServiceException e) {
                    tagService.createTag(tag);
                    id = tagService.readTagByName(tag.getName()).getId();
                }
                tag.setId(id);
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
