package com.epam.esm.service.entityFactory;

import com.epam.esm.entity.TagEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagEntityFactory {
    private static final TagEntity tagId1 = getNewTagEntityId1();
    private static final TagEntity tagId2 = getNewTagEntityId2();
    private static final TagEntity tagId3 = getNewTagEntityId3();
    private static final TagEntity tagId4 = getNewTagEntityId4();
    private static final TagEntity tagId5 = getNewTagEntityId5();
    private static final TagEntity tagId6 = getNewTagEntityId6();
    private static final TagEntity tagId7 = getNewTagEntityId7();

    public static TagEntity getNewTagEntityId1() {
        return new TagEntity(1, "Sport");
    }

    public static TagEntity getNewTagEntityId2() {
        return new TagEntity(2, "Water");
    }

    public static TagEntity getNewTagEntityId3() {
        return new TagEntity(3, "Photo-session");
    }

    public static TagEntity getNewTagEntityId4() {
        return new TagEntity(4, "Cafe");
    }

    public static TagEntity getNewTagEntityId5() {
        return new TagEntity(5, "Auto");
    }

    public static TagEntity getNewTagEntityId6() {
        return new TagEntity(6, "Romantic");
    }

    public static TagEntity getNewTagEntityId7() {
        return new TagEntity(7, "Health");
    }

    public static TagEntity getNewTagEntity() {
        return new TagEntity(0, "New tag");
    }

    public static TagEntity getTagEntityId1() {
        return tagId1;
    }

    public static TagEntity getTagEntityId2() {
        return tagId2;
    }

    public static TagEntity getTagEntityId3() {
        return tagId3;
    }

    public static TagEntity getTagEntityId4() {
        return tagId4;
    }

    public static TagEntity getTagEntityId5() {
        return tagId5;
    }

    public static TagEntity getTagEntityId6() {
        return tagId6;
    }

    public static TagEntity getTagEntityId7() {
        return tagId7;
    }

    public static List<TagEntity> getTagEntityList() {
        return Stream.of(tagId1, tagId2, tagId3, tagId4, tagId5, tagId6, tagId7).collect(Collectors.toList());
    }

    public static List<TagEntity> getNewTagEntityList() {
        return Stream.of(getTagEntityId1(), getTagEntityId2(), getTagEntityId3(),
                getTagEntityId4(), getTagEntityId5(), getTagEntityId6(), getTagEntityId7())
                .collect(Collectors.toList());
    }

}
