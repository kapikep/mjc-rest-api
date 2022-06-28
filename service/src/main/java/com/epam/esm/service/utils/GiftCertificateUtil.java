package com.epam.esm.service.utils;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificateEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Utils for gift certificate
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public class GiftCertificateUtil {

    /**
     * Converting giftCertificateEntity to giftCertificateListDto
     */
    public static List<GiftCertificateDto> giftCertificateEntityListToDtoConverting(List<GiftCertificateEntity> giftCertificateList) {
        List<GiftCertificateDto> giftCertificateDtoList = new ArrayList<>();

        giftCertificateList.forEach(entity -> giftCertificateDtoList.add(giftCertificateEntityToDtoTransfer(entity)));
        return giftCertificateDtoList;
    }

    public static GiftCertificateEntity giftCertificateDtoToEntityTransfer(GiftCertificateDto dto){
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

    public static GiftCertificateDto giftCertificateEntityToDtoTransfer(GiftCertificateEntity entity) {
        GiftCertificateDto dto = new GiftCertificateDto();
        return getGiftCertificateDto(entity, dto);
    }

    public static GiftCertificateDto updateFieldsInDtoFromEntity(GiftCertificateEntity entity, GiftCertificateDto dto) {
        return getGiftCertificateDto(entity, dto);
    }

    private static GiftCertificateDto getGiftCertificateDto(GiftCertificateEntity entity, GiftCertificateDto dto) {
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setDuration(entity.getDuration());
        dto.setCreateDate(entity.getCreateDate());
        dto.setLastUpdateDate(entity.getLastUpdateDate());
        dto.setTags(TagUtil.tagEntityListToDtoConverting(entity.getTags()));
        return dto;
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
}
