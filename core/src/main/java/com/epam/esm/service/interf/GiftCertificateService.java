package com.epam.esm.service.interf;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;

import java.util.List;

public interface GiftCertificateService {

    List<GiftCertificateDto> readAllGiftCertificates() throws ServiceException, ValidateException;

    GiftCertificate readGiftCertificate(String id) throws ServiceException, ValidateException;

    void createGiftCertificate(GiftCertificate giftCertificate) throws ServiceException, ValidateException;

    void updateGiftCertificate(GiftCertificate giftCertificate) throws ServiceException, ValidateException;;

    void deleteGiftCertificate(String idStr) throws ServiceException, ValidateException;;
}
