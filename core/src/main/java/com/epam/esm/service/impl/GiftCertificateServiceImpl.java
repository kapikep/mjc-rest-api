package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.GiftCertificateService;
import com.epam.esm.service.validator.GiftCertificateValidator;
import com.epam.esm.service.validator.ServiceUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository repository;

    public GiftCertificateServiceImpl(GiftCertificateRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<GiftCertificate> readAllGiftCertificates() throws ServiceException, ValidateException {
        List<GiftCertificate> giftCertificates;
        try {
            giftCertificates = repository.readAll();
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return giftCertificates;
    }

    @Override
    public GiftCertificate readGiftCertificate(String idStr) throws ServiceException, ValidateException {
        int id = ServiceUtil.parseInt(idStr);
        GiftCertificate giftCertificate;
        try {
            giftCertificate = repository.readGiftCertificate(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return giftCertificate;
    }

    @Override
    public void createGiftCertificate(GiftCertificate giftCertificate) throws ServiceException, ValidateException {
        GiftCertificateValidator.giftCertificateFieldValidation(giftCertificate);
        try {
            repository.createGiftCertificate(giftCertificate);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void updateGiftCertificate(GiftCertificate giftCertificate) throws ServiceException, ValidateException {
        try {
            if(!GiftCertificateValidator.allNotNullFieldValidation(giftCertificate)){
                GiftCertificate oldGiftCertificate = repository.readGiftCertificate(giftCertificate.getId());
                updateFields(giftCertificate, oldGiftCertificate);
            }
            GiftCertificateValidator.giftCertificateFieldValidation(giftCertificate);
            repository.updateGiftCertificate(giftCertificate);
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

    private void updateFields(GiftCertificate newGiftCertificate, GiftCertificate oldGiftCertificate) {

        if (newGiftCertificate.getName() == null) {
            newGiftCertificate.setName(oldGiftCertificate.getName());
        }
        if(newGiftCertificate.getDescription() == null){
            newGiftCertificate.setDescription(oldGiftCertificate.getDescription());
        }
        if (newGiftCertificate.getPrice() == null) {
            newGiftCertificate.setPrice(oldGiftCertificate.getPrice());
        }
        if (newGiftCertificate.getDuration() == null) {
            newGiftCertificate.setPrice(oldGiftCertificate.getPrice());
        }
        if (newGiftCertificate.getCreateDate() == null) {
            newGiftCertificate.setCreateDate(oldGiftCertificate.getCreateDate());
        }
        if (newGiftCertificate.getLastUpdateDate() == null) {
            newGiftCertificate.setLastUpdateDate(oldGiftCertificate.getLastUpdateDate());
        }
    }
}
