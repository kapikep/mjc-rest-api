package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.GiftCertificateService;
import com.epam.esm.service.utils.GiftCertificateUtil;
import com.epam.esm.service.validator.GiftCertificateValidator;
import com.epam.esm.service.utils.ServiceUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository repository;

    public GiftCertificateServiceImpl(GiftCertificateRepository repository) {
        this.repository = repository;
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

            if(giftCertificates.isEmpty()){
                throw new ServiceException(String.format("Requested resource not found (id = %d)", id));
            }
            giftCertificateDtoList = GiftCertificateUtil.giftCertificateEntityListToDtoConverting(giftCertificates);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return giftCertificateDtoList.get(0);
    }

    @Override
    public void createGiftCertificate(GiftCertificateDto giftCertificateDto) throws ServiceException, ValidateException {
        GiftCertificateValidator.giftCertificateFieldValidation(giftCertificateDto);
        GiftCertificate giftCertificate = null;
        try {
            repository.createGiftCertificate(giftCertificate);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void updateGiftCertificate(GiftCertificateDto giftCertificateDto) throws ServiceException, ValidateException {
        try {
            if(!GiftCertificateValidator.allNotNullFieldValidation(giftCertificateDto)){
                GiftCertificateDto oldGiftCertificateDto = readGiftCertificate(Integer.toString(giftCertificateDto.getId()));
                GiftCertificateUtil.updateFields(giftCertificateDto, oldGiftCertificateDto);
            }
            GiftCertificateValidator.giftCertificateFieldValidation(giftCertificateDto);
            GiftCertificate gift = GiftCertificateUtil.giftCertificateDtoToEntityTransfer(giftCertificateDto);
            repository.updateGiftCertificate(gift);
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
}
