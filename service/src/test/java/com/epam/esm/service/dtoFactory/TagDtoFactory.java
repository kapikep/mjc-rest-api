package com.epam.esm.service.dtoFactory;

import com.epam.esm.dto.TagDto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagDtoFactory {
    private static final TagDto tagId1 = getNewTagDtoId1();
    private static final TagDto tagId2 = getNewTagDtoId2();
    private static final TagDto tagId3 = getNewTagDtoId3();
    private static final TagDto tagId4 = getNewTagDtoId4();
    private static final TagDto tagId5 = getNewTagDtoId5();
    private static final TagDto tagId6 = getNewTagDtoId6();
    private static final TagDto tagId7 = getNewTagDtoId7();

    public static TagDto getTagDtoId1(){
        return tagId1;
    }

    public static TagDto getTagDtoId2(){
        return tagId2;
    }

    public static TagDto getTagDtoId3(){
        return tagId3;
    }

    public static TagDto getTagDtoId4(){
        return tagId4;
    }

    public static TagDto getTagDtoId5(){
        return tagId5;
    }

    public static TagDto getTagDtoId6(){
        return tagId6;
    }

    public static TagDto getTagDtoId7(){
        return tagId7;
    }

    public static TagDto getNewTagDtoId1() {
        return new TagDto(1, "Sport");
    }

    public static TagDto getNewTagDtoId2() {
        return new TagDto(2, "Water");
    }

    public static TagDto getNewTagDtoId3() {
        return new TagDto(3, "Photo-session");
    }

    public static TagDto getNewTagDtoId4() {
        return new TagDto(4, "Cafe");
    }

    public static TagDto getNewTagDtoId5() {
        return new TagDto(5, "Auto");
    }

    public static TagDto getNewTagDtoId6() {
        return new TagDto(6, "Romantic");
    }

    public static TagDto getNewTagDtoId7() {
        return new TagDto(7, "Health");
    }

    public static TagDto getNewTagDto() {
        return new TagDto(0, "New tag");
    }

    public static List<TagDto> getTagDtoList() {
        return Stream.of(tagId1, tagId2, tagId3, tagId4, tagId5, tagId6, tagId7).collect(Collectors.toList());
    }

    public static List<TagDto> getNewTagDtoList() {
        return Stream.of(getTagDtoId1(), getTagDtoId2(), getTagDtoId3(),
                getTagDtoId4(), getTagDtoId5(), getTagDtoId6(), getTagDtoId7()).collect(Collectors.toList());
    }
}
