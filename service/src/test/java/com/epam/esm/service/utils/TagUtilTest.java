package com.epam.esm.service.utils;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.TagEntity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.service.utils.TagUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TagUtilTest {

    private List<TagEntity> getEntityList(){
        List<TagEntity> tagEntityList = new ArrayList<>();
        tagEntityList.add(getTagEntityId1());
        tagEntityList.add(getTagEntityId2());
        tagEntityList.add(getTagEntityId5());
        tagEntityList.add(getTagEntityId7());
        return tagEntityList;
    }

    private List<TagDto> getDtoList(){
        List<TagDto> tagDtoList = new ArrayList<>();
        tagDtoList.add(getTagDtoId1());
        tagDtoList.add(getTagDtoId2());
        tagDtoList.add(getTagDtoId5());
        tagDtoList.add(getTagDtoId7());
        return tagDtoList;
    }

    private TagEntity getTagEntityId1() {
        return new TagEntity(1, "Sport");
    }

    private TagEntity getTagEntityId2() {
        return new TagEntity(2, "Water");
    }

    private TagEntity getTagEntityId5() {
        return new TagEntity(5, "Auto");
    }

    private TagEntity getTagEntityId7() {
        return new TagEntity(7, "Health");
    }

    private TagDto getTagDtoId1() {
        return new TagDto(1, "Sport");
    }

    private TagDto getTagDtoId2() {
        return new TagDto(2, "Water");
    }

    private TagDto getTagDtoId5() {
        return new TagDto(5, "Auto");
    }

    private TagDto getTagDtoId7() {
        return new TagDto(7, "Health");
    }

    @Test
    void tagEntityListToDtoConvertingTest() {
        List<TagDto> actualDto = tagEntityListToDtoConverting(getEntityList());

        assertEquals(getDtoList(), actualDto);
    }

    @Test
    void tagDtoListToEntityConvertingTest() {
        List<TagEntity> actualEntity = tagDtoListToEntityConverting(getDtoList());

        assertEquals(getEntityList(), actualEntity);
    }

    @Test
    void tagDtoToEntityTransferTest() {
        TagEntity actualEntity = tagDtoToEntityTransfer(getTagDtoId1());

        assertEquals(getTagEntityId1(), actualEntity);
    }

    @Test
    void tagEntityToDtoTransferTest() {
        TagDto actualTagDto = tagEntityToDtoTransfer(getTagEntityId2());

        assertEquals(getTagDtoId2(), actualTagDto);
    }
}