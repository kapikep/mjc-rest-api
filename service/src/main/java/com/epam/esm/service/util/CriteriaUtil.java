package com.epam.esm.service.util;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.repository.constant.SearchParam;
import com.epam.esm.service.exception.ValidateException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CriteriaUtil {

    private static final int MAX_NAME_LENGHT = 45;
    private static final int MAX_DESCRIPTION_LENGHT = 255;
    public static final int CRITERIA_TAG_LENGHT = 45;

    public static CriteriaEntity criteriaDtoToEntityConverting(CriteriaDto dto) throws ValidateException {
        CriteriaEntity entity = new CriteriaEntity();

        entity.setPage(dto.getPage());
        entity.setSize(dto.getSize());
        entity.setSorting(dto.getSorting());
        entity.setSearchParam(dto.getSearchParam());

        return entity;
    }

    public static CriteriaDto criteriaEntityToDtoConverting(CriteriaEntity entity) throws ValidateException {
        CriteriaDto dto = new CriteriaDto();

        dto.setPage(entity.getPage());
        dto.setSize(entity.getSize());
        dto.setSorting(entity.getSorting());
        dto.setSearchParam(entity.getSearchParam());

        return dto;
    }

    public static void giftCertificateCriteriaFieldValidation(CriteriaDto crDto) throws ValidateException {
        List<String> resList = new ArrayList<>();
        Map<String, String> searchParam = crDto.getSearchParam();
        String sorting = crDto.getSorting();

        if (searchParam != null) {
            searchParam.forEach((k, v) -> {
                switch (k) {
                    case SearchParam.GIFT_SEARCH_BY_TAG_NAME:
                        if (v.length() > CRITERIA_TAG_LENGHT) {
                            resList.add("incorrect.param.tag");
                        }
                        break;
                    case SearchParam.GIFT_SEARCH_NAME:
                        if (v.length() > MAX_NAME_LENGHT) {
                            resList.add("incorrect.param.name");
                        }
                        break;
                    case SearchParam.GIFT_SEARCH_DESCRIPTION:
                        if (v.length() > MAX_DESCRIPTION_LENGHT) {
                            resList.add("incorrect.param.description");
                        }
                        break;
                    default:
                        resList.add("incorrect.param");
                }
            });
        }

//        if (sorting != null) {
//            if (sorting.startsWith("-") || sorting.startsWith("+") || sorting.startsWith(" ")) {
//                sorting = sorting.substring(1);
//            }
//
//            if (!GiftCertificateSearchParam.GIFT_CERTIF_SORT_PARAM.contains(sorting)) {
//                throw new ValidateException("incorrect.param.sorting", GiftCertificateSearchParam.GIFT_CERTIF_SORT_PARAM);
////                resList.add("incorrect.param.sorting" + GiftCertificateSearchParam.GIFT_CERTIF_SORT_PARAM);
////                resList.add("incorrect.param.sorting");
//            }
//        }

        sortingValidate(sorting, SearchParam.GIFT_CERTIFICATE_SORT_PARAM);

        if (!resList.isEmpty()) {
            throw new ValidateException(resList);
        }
    }

    public static void sortingValidate(String sorting, List<?> param) throws ValidateException {
        if (sorting != null) {
            if (sorting.startsWith("-") || sorting.startsWith("+") || sorting.startsWith(" ")) {
                sorting = sorting.substring(1);
            }

            if (!param.contains(sorting)) {
                throw new ValidateException("incorrect.param.sorting", param);
//                resList.add("incorrect.param.sorting" + GiftCertificateSearchParam.GIFT_CERTIF_SORT_PARAM);
//                resList.add("incorrect.param.sorting");
            }
        }
    }

    public static void setDefaultPageValIfEmpty(CriteriaDto crDto) {
        if (crDto.getPage() == null) {
            crDto.setPage(1);
        }

        if (crDto.getSize() == null) {
            crDto.setSize(20);
        }

        if (crDto.getTotalSize() == null) {
            crDto.setTotalSize(0L);
        }
    }
}
