package com.epam.esm.service.utils;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GiftCertificateUtil {

    public static List<GiftCertificateDto> giftCertificateEntityListToDtoConverting(List<GiftCertificate> giftCertificateList) {
        List<GiftCertificateDto> giftCertificateDtoList = new ArrayList<>();

        giftCertificateList.forEach(entity -> {
            if(giftCertificateDtoList.stream().anyMatch(dto -> dto.getId() == entity.getId())){
                giftCertificateDtoList.stream()
                        .filter(dto -> dto.getId() == entity.getId()).findAny()
                        .ifPresent(dto -> dto.getTags().add(entity.getTag()));
            }else{
                giftCertificateDtoList.add(giftCertificateEntityToDtoTransfer(entity));
            }
        });
        return giftCertificateDtoList;
    }

    public static GiftCertificate giftCertificateDtoToEntityTransfer(GiftCertificateDto dto){
        GiftCertificate entity = new GiftCertificate();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setDuration(dto.getDuration());
        entity.setCreateDate(dto.getCreateDate());
        entity.setLastUpdateDate(dto.getLastUpdateDate());
        return entity;
    }

    public static GiftCertificateDto giftCertificateEntityToDtoTransfer(GiftCertificate gift) {
        GiftCertificateDto giftDto = new GiftCertificateDto();
        giftDto.setId(gift.getId());
        giftDto.setName(gift.getName());
        giftDto.setDescription(gift.getDescription());
        giftDto.setPrice(gift.getPrice());
        giftDto.setDuration(gift.getDuration());
        giftDto.setCreateDate(gift.getCreateDate());
        giftDto.setLastUpdateDate(gift.getLastUpdateDate());
        giftDto.setTags(new ArrayList<>(Collections.singletonList(new Tag(gift.getTag().getId(), gift.getTag().getName()))));
        return giftDto;
    }

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
