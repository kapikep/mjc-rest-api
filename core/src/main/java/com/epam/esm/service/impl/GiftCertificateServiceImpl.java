package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.GiftCertificateService;
import com.epam.esm.service.validator.ServiceUtil;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository repository;

    public GiftCertificateServiceImpl(GiftCertificateRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<GiftCertificate> readAll() throws ServiceException, ValidateException {
        List<GiftCertificate> giftCertificates = null;
        try {
            giftCertificates = repository.readAll();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return giftCertificates;
    }

    @Override
    public GiftCertificate readGiftCertificate(String idStr) throws ServiceException, ValidateException {
        int id = ServiceUtil.parseInt(idStr);
        GiftCertificate giftCertificate = null;
        try {
            giftCertificate = repository.readGiftCertificate(id);
        } catch (RepositoryException e) {
            System.out.println("Service ->" + e);
            throw new ServiceException(e);
        }
        return giftCertificate;
    }
}
