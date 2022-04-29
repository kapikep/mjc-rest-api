package com.epam.esm.service.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.exception.ValidateException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GiftCertificateValidator {
    private static final int MAX_ID = 1_000_000;
    private static final int MAX_NAME_LENGHT = 45;
    private static final int MAX_DESCRIPTION_LENGHT = 255;
    private static final int MIN_PRICE = 1;
    private static final int MAX_PRICE = 100_000;
    private static final int MIN_DURATION = 1;
    private static final int MAX_DURATION = 10_000;

    public static void giftCertificateFieldValidation(GiftCertificateDto giftCertificateDto) throws ValidateException {
        List<String> resList = new ArrayList<>();

        if (!idValidation(giftCertificateDto.getId())) {
            resList.add("incorrect.id");
        }
        if (!nameValidation(giftCertificateDto.getName())) {
            resList.add("incorrect.name");
        }
        if (!descriptionValidation(giftCertificateDto.getDescription())) {
            resList.add("incorrect.description");
        }
        if (!priceValidation(giftCertificateDto.getPrice())) {
            resList.add("incorrect.price");
        }
        if (!durationValidation(giftCertificateDto.getDuration())) {
            resList.add("incorrect.duration");
        }
        if (!createDateValidation(giftCertificateDto.getCreateDate())) {
            resList.add("incorrect.create.date");
        }
        if (!lastUpdateDateValidation(giftCertificateDto.getCreateDate(), giftCertificateDto.getLastUpdateDate())) {
            resList.add("incorrect.update.date");        }

        //TODO Tag validate

        if (!resList.isEmpty()) {
            throw new ValidateException(resList);
        }
    }


    public static boolean idValidation(int id) {
        boolean res = true;

        if (id < 0 || id > MAX_ID) {
            res = false;
        }
        return res;
    }

    public static boolean nameValidation(String name) {
        boolean res = true;

        if (name != null) {
            if (name.isEmpty() || name.length() > MAX_NAME_LENGHT) {
                res = false;
            }
        } else {
            res = false;
        }
        return res;
    }

    public static boolean descriptionValidation(String description) {
        boolean res = true;

        if (description != null) {
            if (description.isEmpty() || description.length() > MAX_DESCRIPTION_LENGHT) {
                res = false;
            }
        } else {
            res = false;
        }
        return res;
    }

    public static boolean priceValidation(double price) {
        boolean res = true;

        if (price < MIN_PRICE || price > MAX_PRICE) {
            res = false;
        }
        return res;
    }

    public static boolean durationValidation(int duration) {
        boolean res = true;

        if (duration < MIN_DURATION || duration > MAX_DURATION) {
            res = false;
        }
        return res;
    }

    public static boolean createDateValidation(LocalDateTime createDate) {
        boolean res = true;

        if (createDate == null) {
            res = false;
        }
        return res;
    }

    public static boolean lastUpdateDateValidation(LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        boolean res = true;

        if (lastUpdateDate == null) {
            res = false;
        } else {
            if (lastUpdateDate.isBefore(createDate)) {
                res = false;
            }
        }
        return res;
    }

    public static boolean allNotNullFieldValidation(GiftCertificateDto g) throws ValidateException {
        boolean res = true;

        if (g.getId() == 0) {
            throw new ValidateException("No Id", null);
        }
        if (res && g.getName() == null) {
            res = false;
        }
        if(res && g.getDescription() == null){
            res = false;
        }
        if (res && g.getPrice() == null) {
            res = false;
        }
        if (res && g.getDuration() == null) {
            res = false;
        }
        if (res && g.getCreateDate() == null) {
            res = false;
        }
        if (res && g.getLastUpdateDate() == null) {
            res = false;
        }
        if (res && g.getTags() == null) {
            res = false;
        }

        return res;
    }
}

