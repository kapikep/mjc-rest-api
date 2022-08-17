package com.epam.esm.service.dtoFactory;

import com.epam.esm.dto.UserDto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserDtoFactory {
    private static final UserDto userId1 = getNewUserDtoId1();
    private static final UserDto userId2 = getNewUserDtoId2();
    private static final UserDto userId3 = getNewUserDtoId3();
    private static final UserDto userId4 = getNewUserDtoId4();
    private static final UserDto userId5 = getNewUserDtoId5();

    public static UserDto getNewUserDtoId1() {
        return new UserDto(1, "Vasilij", "Pupkin",
                "+375292629988");
    }

    public static UserDto getNewUserDtoId2() {
        return new UserDto(2, "Svetlana", "Vasilievna",
                "+375292629995");
    }

    public static UserDto getNewUserDtoId3() {
        return new UserDto(3, "Kirill", "Ivanovich",
                "+375292629969");
    }

    public static UserDto getNewUserDtoId4() {
        return new UserDto(4, "Vladimir", "Ponamarev",
                "+375292629944");
    }

    public static UserDto getNewUserDtoId5() {
        return new UserDto(5, "Igor", "Vasiliev",
                "+375292628844");
    }

    public static UserDto getUserDtoId1() {
        return userId1;
    }

    public static UserDto getUserDtoId2() {
        return userId2;
    }

    public static UserDto getUserDtoId3() {
        return userId3;
    }

    public static UserDto getUserDtoId4() {
        return userId4;
    }

    public static UserDto getUserDtoId5() {
        return userId5;
    }

    public static List<UserDto> getUserDtoList() {
        return Arrays.asList(userId1, userId2, userId3, userId4, userId5);
    }

    public static List<UserDto> getNewUserDtoList() {
        return Stream.of(getUserDtoId1(), getUserDtoId2(), getUserDtoId3(),
                getUserDtoId4(), getUserDtoId5()).collect(Collectors.toList());
    }
}
