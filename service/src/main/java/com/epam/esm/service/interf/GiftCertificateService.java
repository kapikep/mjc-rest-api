package com.epam.esm.service.interf;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;

import java.util.List;
import java.util.Map;
/**
 * Service for gift certificates
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public interface GiftCertificateService {

    /**
     * Reads all gift certificates from repository
     *
     * @return list with all GiftCertificateDto
     */
    List<GiftCertificateDto> readAllGiftCertificates() throws ServiceException, ValidateException;

    /**
     * Validates id and reads gift certificates by id from repository
     *
     * @return gift certificate from repository
     */
    GiftCertificateDto readGiftCertificate(String id) throws ServiceException, ValidateException;

    /**
     * Validates id and reads gift certificates by id from repository
     *
     * @return gift certificate from repository
     */
    GiftCertificateDto readGiftCertificate(int id) throws ServiceException, ValidateException;

    int createGiftCertificate(GiftCertificateDto giftCertificateDto) throws ServiceException, ValidateException;

    void updateGiftCertificate(GiftCertificateDto giftCertificateDto) throws ServiceException, ValidateException;

    void deleteGiftCertificate(String idStr) throws ServiceException, ValidateException;

    List<GiftCertificateDto> findGiftCertificates(Map<String, String> criteriaMap, String sorting) throws ServiceException, ValidateException;
}
