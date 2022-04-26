package com.epam.esm.service.interf;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;

import java.util.List;

public interface GiftCertificateService {

    List<GiftCertificateDto> readAllGiftCertificates() throws ServiceException, ValidateException;

    GiftCertificateDto readGiftCertificate(String id) throws ServiceException, ValidateException;

    GiftCertificateDto readGiftCertificate(int id) throws ServiceException, ValidateException;

    void createGiftCertificate(GiftCertificateDto giftCertificateDto) throws ServiceException, ValidateException;

    void updateGiftCertificate(GiftCertificateDto giftCertificateDto) throws ServiceException, ValidateException;;

    void deleteGiftCertificate(String idStr) throws ServiceException, ValidateException;;
}
