package com.epam.esm.repository.constant;

import java.util.Arrays;
import java.util.List;
/**
 * Constants for search param for Gift Certificate
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public final class SearchParam {
    public static final String GIFT_SEARCH_NAME = "name";
    public static final String GIFT_SEARCH_DESCRIPTION = "description";
    public static final String GIFT_SEARCH_BY_TAG_NAME = "t.name";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String FIRST_NAME = "firstName";
    public static final String SECOND_NAME = "secondName";
    public static final String CREATE_DATE = "create_date";
    public static final String LAST_UPDATE_DATE = "last_update_date";
    public static final String DESCRIPTION = "description";
    public static final String TOTAL_AMOUNT = "totalAmount";
    public static final String PRICE = "price";
    public static final String DURATION = "duration";

    public static final List<String> GIFT_CERTIFICATE_SEARCH_PARAM = Arrays.asList(ID, GIFT_SEARCH_NAME, GIFT_SEARCH_DESCRIPTION);

    public static final List<String> GIFT_CERTIFICATE_SORT_PARAM = Arrays.asList(ID, CREATE_DATE,
            LAST_UPDATE_DATE, NAME, DESCRIPTION, PRICE, DURATION);
    public static final List<String> TAG_SORT_PARAM = Arrays.asList(ID, NAME);
    public static final List<String> USER_SORT_PARAM = Arrays.asList(ID, FIRST_NAME, SECOND_NAME);
    public static final List<String> ORDER_SORT_PARAM = Arrays.asList(ID, TOTAL_AMOUNT);
}
