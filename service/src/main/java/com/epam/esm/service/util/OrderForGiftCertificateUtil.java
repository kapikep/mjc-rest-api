package com.epam.esm.service.util;

import com.epam.esm.dto.OrderForGiftCertificateDto;
import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.OrderForGiftCertificateEntity;
import com.epam.esm.service.exception.ValidateException;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.repository.constant.SearchParam.TAG_SORT_PARAM;

public class OrderForGiftCertificateUtil {
    public static List<OrderForGiftCertificateDto> OrderForGiftCertificateEntityListToDtoConverting(List<OrderForGiftCertificateEntity> entities){
        List<OrderForGiftCertificateDto> dtoList = new ArrayList<>();
        entities.forEach(entity -> dtoList.add(OrderForGiftCertificateEntityToDtoTransfer(entity)));
        return dtoList;
    }

    public static List<OrderForGiftCertificateEntity> OrderForGiftCertificateDtoListToEntityConverting(List<OrderForGiftCertificateDto> dtoList){
        List<OrderForGiftCertificateEntity> entities = new ArrayList<>();
        dtoList.forEach(dto -> entities.add(OrderForGiftCertificateDtoToEntityTransfer(dto)));
        return entities;
    }

    public static OrderForGiftCertificateEntity OrderForGiftCertificateDtoToEntityTransfer(OrderForGiftCertificateDto dto){
        OrderForGiftCertificateEntity entity = new OrderForGiftCertificateEntity();
        entity.setId(dto.getId());
        entity.setOrderTime(dto.getOrderTime());
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setUser(UserUtil.userDtoToEntityTransfer(dto.getUser()));
        entity.setGifts(GiftCertificateUtil.giftCertificateDtoListToEntityConverting(dto.getGifts()));
        return entity;
    }

    public static OrderForGiftCertificateDto OrderForGiftCertificateEntityToDtoTransfer(OrderForGiftCertificateEntity entity){
        OrderForGiftCertificateDto dto = new OrderForGiftCertificateDto();
        updateFieldsInDtoFromEntity(entity, dto);
        return dto;
    }

    public static void updateFieldsInDtoFromEntity(OrderForGiftCertificateEntity entity, OrderForGiftCertificateDto dto){
        dto.setId(entity.getId());
        dto.setOrderTime(entity.getOrderTime());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setUser(UserUtil.userEntityToDtoTransfer(entity.getUser()));
        entity.getGifts().forEach(g -> g.setTags(new ArrayList<>()));
        dto.setGifts(GiftCertificateUtil.giftCertificateEntityListToDtoConverting(entity.getGifts()));
    }

    public static void sortingValidation(CriteriaEntity crDto) throws ValidateException {
        String sorting = crDto.getSorting();

        if (sorting != null) {
            if (sorting.startsWith("-") || sorting.startsWith("+") || sorting.startsWith(" ")) {
                sorting = sorting.substring(1);
            }

            if (!TAG_SORT_PARAM.contains(sorting)) {
                throw new ValidateException("incorrect.param.sorting", TAG_SORT_PARAM);
            }
        }
    }
}
