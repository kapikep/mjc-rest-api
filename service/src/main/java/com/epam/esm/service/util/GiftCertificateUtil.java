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
        updateFieldsInDtoFromEntity(entity, dto);
        return dto;
    }

    public static void updateFieldsInDtoFromEntity(GiftCertificateEntity entity, GiftCertificateDto dto) {
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
        String sorting = crDto.getSorting();

        if (sorting != null) {
            if (sorting.startsWith("-") || sorting.startsWith("+") || sorting.startsWith(" ")) {
                sorting = sorting.substring(1);
            }

            if (!GIFT_CERTIFICATE_SORT_PARAM.contains(sorting)) {
                throw new ValidateException("incorrect.param.sorting", GIFT_CERTIFICATE_SORT_PARAM);
            }
        }
    }

    public static boolean isNullFieldValidation(GiftCertificateDto g) throws ValidateException {
        if (g.getId() == 0) {
            throw new ValidateException("incorrect.id");
        }
        return Stream.of(g.getId(), g.getName(), g.getDescription(), g.getPrice(), g.getDuration(),
                g.getCreateDate(), g.getTags()).anyMatch(Objects::isNull);
    }
}
