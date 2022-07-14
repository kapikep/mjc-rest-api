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
    public static final String GIFT_SORT_CREATE_DATE = "create_date";
    public static final String GIFT_SORT_LAST_UPDATE_DATE = "last_update_date";
    public static final String GIFT_SORT_DESCRIPTION = "description";

    public static final List<String> GIFT_CERTIFICATE_SEARCH_PARAM = Arrays.asList(ID, GIFT_SEARCH_NAME, GIFT_SEARCH_DESCRIPTION);
    public static final List<String> GIFT_CERTIFICATE_SORT_PARAM = Arrays.asList(ID, GIFT_SORT_CREATE_DATE,
            GIFT_SORT_LAST_UPDATE_DATE, NAME, GIFT_SORT_DESCRIPTION);

    public static final List<String> TAG_SORT_PARAM = Arrays.asList(ID, NAME);
}
