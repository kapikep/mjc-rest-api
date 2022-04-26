package com.epam.esm.service.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServiceUtil {

    public static int parseInt(String str, int defaultVal) {
        int i = defaultVal;
        if (str != null && Pattern.matches("\\d+", str)) {
            i = Integer.parseInt(str);
        }
        return i;
    }

    public static int parseInt(String str) throws ValidateException {
        int i = 0;
        if (str != null && Pattern.matches("\\d+", str)) {
            i = Integer.parseInt(str);
        } else {
            throw new ValidateException(String.format("Incorrect parameter (%s)", str));
        }
        return i;
    }

    public static double parseDouble(String str) throws ValidateException {
        double d = 0;
        if (str != null) {
            d = Double.parseDouble(str);
        } else {
            throw new ValidateException(String.format("Incorrect parameter (%s)", str));
        }
        return d;
    }

    public static List<GiftCertificateDto> giftCertificateEntityToDtoConverting(List<GiftCertificate> giftCertificateList) {
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

//        for (GiftCertificate entity : giftCertificateList) {
//            if (giftCertificateDtoList.stream().anyMatch(dto -> dto.getId() == entity.getId())) {
//                for (GiftCertificateDto gift :
//                        giftCertificateDtoList) {
//                    if(entity.getId() == gift.getId()){
//                        gift.addTag(entity.getTag());
//                    }
//                }
//            } else {
//                giftCertificateDtoList.add(giftCertificateEntityToDtoTransfer(entity));
//            }
//        }
        return giftCertificateDtoList;
    }

    private static GiftCertificateDto giftCertificateEntityToDtoTransfer(GiftCertificate gift) {
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
}
