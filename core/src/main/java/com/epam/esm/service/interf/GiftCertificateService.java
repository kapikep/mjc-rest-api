package com.epam.esm.service.interf;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;

import java.util.List;

public interface GiftCertificateService {

    List<GiftCertificate> readAll() throws ServiceException, ValidateException;

    GiftCertificate readGiftCertificate(String id) throws ServiceException, ValidateException;
}
