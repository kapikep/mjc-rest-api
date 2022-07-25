package com.epam.esm.service.util;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.repository.constant.SearchParam;
import com.epam.esm.service.exception.ValidateException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static com.epam.esm.repository.constant.SearchParam.GIFT_CERTIFICATE_SORT_PARAM;
import static com.epam.esm.repository.constant.SearchParam.TAG_SORT_PARAM;

/**
 * Utils for gift certificate
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public class GiftCertificateUtil {
    private static final int MAX_NAME_LENGHT = 45;
    private static final int MAX_DESCRIPTION_LENGHT = 255;
    public static final int CRITERIA_TAG_LENGHT = 45;
    /**
     * Converting giftCertificateEntity to giftCertificateListDto
     */
    public static List<GiftCertificateDto> giftCertificateEntityListToDtoConverting(List<GiftCertificateEntity> giftCertificateList) throws ValidateException {
        if(giftCertificateList == null){
            throw new ValidateException("GiftCertificate entities list is null");
        }
        List<GiftCertificateDto> giftCertificateDtoList = new ArrayList<>();
        for (GiftCertificateEntity entity : giftCertificateList) {
            giftCertificateDtoList.add(giftCertificateEntityToDtoTransfer(entity));
        }
        return giftCertificateDtoList;
    }

    public static List<GiftCertificateEntity> giftCertificateDtoListToEntityConverting(List<GiftCertificateDto> dtos) throws ValidateException {
        if(dtos == null){
            throw new ValidateException("GiftCertificate dto list is null");
        }
        List<GiftCertificateEntity> entities = new ArrayList<>();
        for (GiftCertificateDto dto : dtos) {
            entities.add(giftCertificateDtoToEntityTransfer(dto));
        }
        return entities;
    }

    public static GiftCertificateEntity giftCertificateDtoToEntityTransfer(GiftCertificateDto dto) throws ValidateException {
        if(dto == null){
            throw new ValidateException("GiftCertificateDto is null");
        }
        GiftCertificateEntity entity = new GiftCertificateEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setDuration(dto.getDuration());
        entity.setCreateDate(dto.getCreateDate());
        entity.setLastUpdateDate(dto.getLastUpdateDate());
        entity.setTags(TagUtil.tagDtoListToEntityConverting(dto.getTags()));
        return entity;
    }

    public static GiftCertificateDto giftCertificateEntityToDtoTransfer(GiftCertificateEntity entity) throws ValidateException {
        GiftCertificateDto dto = new GiftCertificateDto();
        updateFieldsInDtoFromEntity(entity, dto);
        return dto;
    }

    public static void updateFieldsInDtoFromEntity(GiftCertificateEntity entity, GiftCertificateDto dto) throws ValidateException {
        if(dto == null){
            throw new ValidateException("GiftCertificateDto is null");
        }
        if(entity == null){
            throw new ValidateException("GiftCertificateEntity is null");
        }
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setDuration(entity.getDuration());
        dto.setCreateDate(entity.getCreateDate());
        dto.setLastUpdateDate(entity.getLastUpdateDate());
        dto.setTags(TagUtil.tagEntityListToDtoConverting(entity.getTags()));
    }

    /**
     * Updates fields in new GiftCertificate from old GiftCertificate if it's null in new GiftCertificate
     */
    public static void updateFields(GiftCertificateDto newGiftCertificate, GiftCertificateDto oldGiftCertificate) {
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
            newGiftCertificate.setDuration(oldGiftCertificate.getDuration());
        }
        if (newGiftCertificate.getCreateDate() == null) {
            newGiftCertificate.setCreateDate(oldGiftCertificate.getCreateDate());
        }
        if (newGiftCertificate.getLastUpdateDate() == null) {
            newGiftCertificate.setLastUpdateDate(oldGiftCertificate.getLastUpdateDate());
        }
        if (newGiftCertificate.getTags() == null) {
            newGiftCertificate.setTags(oldGiftCertificate.getTags());
        }
    }

    public static void sortingValidation(CriteriaDto crDto) throws ValidateException {
        CriteriaUtil.sortingValidation(crDto, GIFT_CERTIFICATE_SORT_PARAM);
    }

    public static boolean isNullFieldValidation(GiftCertificateDto g) throws ValidateException {
        if (g.getId() == 0) {
            throw new ValidateException("incorrect.id");
        }
        return Stream.of(g.getId(), g.getName(), g.getDescription(), g.getPrice(), g.getDuration(),
                g.getCreateDate(), g.getTags()).anyMatch(Objects::isNull);
    }
}
