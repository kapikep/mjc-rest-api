package com.epam.esm.repository.constant;

import java.util.Arrays;
import java.util.List;

public final class SearchParam {
    public static final String TAG_NAME = "tag.name";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String SEARCH_CREATE_DATE = "create_date";
    public static final String SEARCH_LAST_UPDATE_DATE = "last_update_date";
    public static final String SEARCH_NAME = "name";
    public static final String SEARCH_DESCRIPTION = "description";
    public static final List<String> SEARCH_CERTIFICATE_PARAM = Arrays.asList(NAME, DESCRIPTION);
    public static final List<String> SORT_PARAM = Arrays.asList(SEARCH_CREATE_DATE, SEARCH_LAST_UPDATE_DATE, SEARCH_NAME, SEARCH_DESCRIPTION);
}
