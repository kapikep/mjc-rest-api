package com.epam.esm.service.util;

import com.epam.esm.service.exception.ValidateException;
import org.junit.jupiter.api.Test;

import static com.epam.esm.service.util.ServiceUtil.parseInt;
import static com.epam.esm.service.util.ServiceUtil.parseLong;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServiceUtilTest {

    @Test
    void parseIntTest() throws ValidateException {
        int actualInt = parseInt("356");

        assertEquals(356, actualInt);

        assertThrows(ValidateException.class,
                () -> parseInt("okdoako"));
    }

    @Test
    void parseLongTest() throws ValidateException {
        long actualLong = parseLong("89998");

        assertEquals(89998, actualLong);

        assertThrows(ValidateException.class,
                () -> parseLong("555i4"));
    }
}