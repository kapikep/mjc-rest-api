package com.epam.esm.repository.impl.EntityFactory;

import com.epam.esm.entity.UserEntity;

public class UserEntityFactory {
    private static final UserEntity userId1 = getNewUserEntityId1();
    private static final UserEntity userId2 = getNewUserEntityId2();
    private static final UserEntity userId3 = getNewUserEntityId3();
    private static final UserEntity userId4 = getNewUserEntityId4();
    private static final UserEntity userId5 = getNewUserEntityId5();

    public static UserEntity getNewUserEntityId1() {
        return new UserEntity(1, "Vasilij", "Pupkin",
                "Vasilij", "fasfq3wfw", "+375292629988");
    }

    public static UserEntity getNewUserEntityId2() {
        return new UserEntity(2, "Svetlana", "Vasilievna",
                "Svetlana", "dewqewqr44", "+375292629995");
    }

    public static UserEntity getNewUserEntityId3() {
        return new UserEntity(3, "Kirill", "Ivanovich",
                "Kirill", "dew88wqr44", "+375292629969");
    }

    public static UserEntity getNewUserEntityId4() {
        return new UserEntity(4, "Vladimir", "Ponamarev",
                "Vladimir", "ewrr3w88wqr44", "+375292629944");
    }

    public static UserEntity getNewUserEntityId5() {
        return new UserEntity(5, "Igor", "Vasiliev",
                "Igor", "ewqawerasdqr44", "+375292628844");
    }

    public static UserEntity getUserEntityId1() {
        return userId1;
    }

    public static UserEntity getUserEntityId2() {
        return userId2;
    }

    public static UserEntity getUserEntityId3() {
        return userId3;
    }

    public static UserEntity getUserEntityId4() {
        return userId4;
    }

    public static UserEntity getUserEntityId5() {
        return userId5;
    }
}
