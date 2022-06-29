package com.epam.esm.service.interf;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
/**
 * Service for gift certificates
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Validated
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
    GiftCertificateDto readGiftCertificate(long id) throws ServiceException, ValidateException;

    void createGiftCertificate(@Valid GiftCertificateDto giftCertificateDto) throws ServiceException, ValidateException;

    void updateGiftCertificate(@Valid GiftCertificateDto giftCertificateDto) throws ServiceException, ValidateException;

    void deleteGiftCertificate(String idStr) throws ServiceException, ValidateException;

    List<GiftCertificateDto> findGiftCertificates(Map<String, String> criteriaMap, String sorting) throws ServiceException, ValidateException;
}
