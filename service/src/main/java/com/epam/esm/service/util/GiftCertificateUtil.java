package com.epam.esm.service.util;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.service.exception.ValidateException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.epam.esm.repository.constant.SearchParam.GIFT_CERTIFICATE_SEARCH_PARAM;
import static com.epam.esm.repository.constant.SearchParam.GIFT_CERTIFICATE_SORT_PARAM;
import static com.epam.esm.repository.constant.ExceptionMes.GIFT_CERTIFICATE_DTO_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.ExceptionMes.GIFT_CERTIFICATE_DTO_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.ExceptionMes.GIFT_CERTIFICATE_ENTITY_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.ExceptionMes.GIFT_CERTIFICATE_ENTITY_MUST_NOT_BE_NULL;
import static org.springframework.util.Assert.notNull;

/**
 * Utils for gift certificate
 *
 * @author Artsemi Kapitula
 * @version 2.0
 */
public class GiftCertificateUtil {
    /**
     * Converting GiftCertificateEntity list to GiftCertificateDto list
     *
     * @param giftCertificateList GiftCertificateEntity list
     * @return GiftCertificateDto list
     */
    public static List<GiftCertificateDto> giftCertificateEntityListToDtoConverting(List<GiftCertificateEntity> giftCertificateList) {
        notNull(giftCertificateList, GIFT_CERTIFICATE_ENTITY_LIST_MUST_NOT_BE_NULL);

        List<GiftCertificateDto> giftCertificateDtoList = new ArrayList<>();
        for (GiftCertificateEntity entity : giftCertificateList) {
            giftCertificateDtoList.add(giftCertificateEntityToDtoConverting(entity));
        }
        return giftCertificateDtoList;
    }

    /**
     * Converting GiftCertificateDto list to GiftCertificateEntity list
     *
     * @param giftCertificateDtoList GiftCertificateDto list
     * @return GiftCertificateEntity list
     */
    public static List<GiftCertificateEntity> giftCertificateDtoListToEntityConverting(List<GiftCertificateDto> giftCertificateDtoList) {
        notNull(giftCertificateDtoList, GIFT_CERTIFICATE_DTO_LIST_MUST_NOT_BE_NULL);

        List<GiftCertificateEntity> entities = new ArrayList<>();
        for (GiftCertificateDto dto : giftCertificateDtoList) {
            entities.add(giftCertificateDtoToEntityConverting(dto));
        }
        return entities;
    }

    /**
     * Converting GiftCertificateDto to GiftCertificateEntity
     *
     * @param dto GiftCertificateDto
     * @return new GiftCertificateEntity with fields values from GiftCertificateDto
     */
    public static GiftCertificateEntity giftCertificateDtoToEntityConverting(GiftCertificateDto dto) {
        notNull(dto, GIFT_CERTIFICATE_DTO_MUST_NOT_BE_NULL);

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

    /**
     * Converting GiftCertificateEntity to GiftCertificateDto
     *
     * @param entity GiftCertificateEntity
     * @return new GiftCertificateDto with fields values from GiftCertificateEntity
     */
    public static GiftCertificateDto giftCertificateEntityToDtoConverting(GiftCertificateEntity entity) {
        GiftCertificateDto dto = new GiftCertificateDto();
        updateFieldsInDtoFromEntity(entity, dto);
        return dto;
    }

    /**
     * Update fields in GiftCertificateDto from GiftCertificateEntity
     * @param entity GiftCertificateEntity
     * @param dto GiftCertificateDto
     */
    public static void updateFieldsInDtoFromEntity(GiftCertificateEntity entity,
                                                   GiftCertificateDto dto) {
        notNull(entity, GIFT_CERTIFICATE_ENTITY_MUST_NOT_BE_NULL);
        notNull(dto, GIFT_CERTIFICATE_DTO_MUST_NOT_BE_NULL);

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
     * Updates fields in old GiftCertificateDto from new GiftCertificate if it's not null in new GiftCertificate
     */
    public static void updateNonNullFieldsFromDtoToEntity(GiftCertificateDto newGiftCertificate,
                                                          GiftCertificateEntity oldGiftCertificate) {
        notNull(newGiftCertificate, GIFT_CERTIFICATE_DTO_MUST_NOT_BE_NULL);
        notNull(oldGiftCertificate, GIFT_CERTIFICATE_DTO_MUST_NOT_BE_NULL);

        if (newGiftCertificate.getName() != null) {
            oldGiftCertificate.setName(newGiftCertificate.getName());
        }
        if (newGiftCertificate.getDescription() != null) {
            oldGiftCertificate.setDescription(newGiftCertificate.getDescription());
        }
        if (newGiftCertificate.getPrice() != null) {
            oldGiftCertificate.setPrice(newGiftCertificate.getPrice());
        }
        if (newGiftCertificate.getDuration() != null) {
            oldGiftCertificate.setDuration(newGiftCertificate.getDuration());
        }
        if (newGiftCertificate.getTags() != null) {
            oldGiftCertificate.setTags(TagUtil.tagDtoListToEntityConverting(newGiftCertificate.getTags()));
        }
    }

    /**
     * Validate CriteriaDto sorting field for GiftCertificateEntity
     *
     * @param crDto CriteriaDto
     * @throws ValidateException if sorting field does not match GIFT_CERTIFICATE_SORT_PARAM
     */
    public static void giftCertificateSortingValidation(CriteriaDto crDto) throws ValidateException {
        CriteriaUtil.sortingValidation(crDto, GIFT_CERTIFICATE_SORT_PARAM);
    }

    /**
     * Validate CriteriaDto searchParam map keys for GIFT_CERTIFICATE_SEARCH_PARAM
     *
     * @param crDto       CriteriaDto.
     * @throws ValidateException if sorting field does not match GIFT_CERTIFICATE_SEARCH_PARAM
     */
    public static void giftCertificateSearchParamKeyValidation(CriteriaDto crDto) throws ValidateException {
        CriteriaUtil.searchParamKeyValidation(crDto, GIFT_CERTIFICATE_SEARCH_PARAM);
    }

    /**
     * Check if all fields are empty.
     *
     * @return true if any field in GiftCertificateDto is null
     */
    public static boolean isNullFieldValidation(GiftCertificateDto g) {
        return Stream.of(g.getId(), g.getName(), g.getDescription(), g.getPrice(), g.getDuration(),
                g.getTags()).anyMatch(Objects::isNull);
    }
}
