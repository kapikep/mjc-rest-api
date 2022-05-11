package com.epam.esm.repository.constant;

import java.util.Arrays;
import java.util.List;
/**
 * Constants for search param for Gift Certificate
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public final class GiftCertificateSearchParam {
    public static final String SEARCH_NAME = "name";
    public static final String SEARCH_DESCRIPTION = "description";
    public static final String SEARCH_TAG_NAME = "tag.name";

    public static final String SORT_CREATE_DATE = "create_date";
    public static final String SORT_LAST_UPDATE_DATE = "last_update_date";
    public static final String SORT_NAME = "name";
    public static final String SORT_DESCRIPTION = "description";

    public static final List<String> SEARCH_CERTIFICATE_PARAM = Arrays.asList(SEARCH_NAME, SEARCH_DESCRIPTION);
    public static final List<String> SORT_PARAM = Arrays.asList(SORT_CREATE_DATE, SORT_LAST_UPDATE_DATE, SORT_NAME, SORT_DESCRIPTION);
}
