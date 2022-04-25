package com.epam.esm.service.validator;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.exception.ValidateException;

import java.time.LocalDateTime;

public class GiftCertificateValidator {
    private static final int MAX_ID = 1_000_000;
    private static final int MAX_NAME_LENGHT = 45;
    private static final int MAX_DESCRIPTION_LENGHT = 255;
    private static final int MIN_PRICE = 1;
    private static final int MAX_PRICE = 100_000;
    private static final int MIN_DURATION = 1;
    private static final int MAX_DURATION = 10_000;

    public static void giftCertificateFieldValidation(GiftCertificate giftCertificate) throws ValidateException {
        StringBuilder resMes = new StringBuilder();
        if(!idValidation(giftCertificate.getId())){
            resMes.append("Wrong id ");
        }
        if(!nameValidation(giftCertificate.getName())){
            resMes.append("Wrong name ");
        }
        if(!descriptionValidation(giftCertificate.getDescription())){
            resMes.append("Wrong description ");
        }
        if(!priceValidation(giftCertificate.getPrice())){
            resMes.append("Wrong price ");
        }
        if(!durationValidation(giftCertificate.getDuration())){
            resMes.append("Wrong duration ");
        }
        if(!createDateValidation(giftCertificate.getCreateDate())){
            resMes.append("Wrong create date ");
        }
        if(!lastUpdateDateValidation(giftCertificate.getCreateDate(), giftCertificate.getLastUpdateDate())){
            resMes.append("Wrong update date ");
        }

        if(resMes.length() != 0){
            throw new ValidateException(resMes.toString());
        }
    }

    public static boolean idValidation(int id){
        boolean res = true;

        if(id < 0 || id > MAX_ID){
            res = false;
        }

        return res;
    }

    public static boolean nameValidation(String name){
        boolean res = true;

        if(name != null){
            if(name.isEmpty() || name.length() > MAX_NAME_LENGHT) {
                res = false;
            }
        } else {
            res = false;
        }

        return res;
    }

    public static boolean descriptionValidation(String description){
        boolean res = true;

        if(description != null){
            if(description.isEmpty() || description.length() > MAX_DESCRIPTION_LENGHT) {
                res = false;
            }
        } else {
            res = false;
        }

        return res;
    }

    public static boolean priceValidation(double price){
        boolean res = true;

        if(price < MIN_PRICE || price > MAX_PRICE){
            res = false;
        }

        return res;
    }

    public static boolean durationValidation(int duration){
        boolean res = true;

        if(duration < MIN_DURATION || duration > MAX_DURATION){
            res = false;
        }

        return res;
    }

    public static boolean createDateValidation(LocalDateTime createDate){
        boolean res = true;

        if(createDate == null){
            res = false;
        }

        return res;
    }

    public static boolean lastUpdateDateValidation(LocalDateTime createDate ,LocalDateTime lastUpdateDate){
        boolean res = true;

        if(lastUpdateDate == null){
            res = false;
        }else {
            if(lastUpdateDate.isBefore(createDate)){
                res = false;
            }
        }

        return res;
    }

}
