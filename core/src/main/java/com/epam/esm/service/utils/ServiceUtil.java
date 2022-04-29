package com.epam.esm.service.utils;

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
            throw new ValidateException("incorrect.parameter", new Object[] {str});
        }
        return i;
    }
}
