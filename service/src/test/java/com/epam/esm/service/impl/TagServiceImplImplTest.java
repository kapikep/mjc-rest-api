package com.epam.esm.service.impl;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.TagRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.util.CriteriaUtil;
import com.epam.esm.service.util.TagUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.service.dtoFactory.DtoFactory.getNewCriteriaDtoWithDefaultVal;
import static com.epam.esm.service.dtoFactory.TagDtoFactory.getNewTagDto;
import static com.epam.esm.service.dtoFactory.TagDtoFactory.getNewTagDtoId1;
import static com.epam.esm.service.dtoFactory.TagDtoFactory.getNewTagDtoId3;
import static com.epam.esm.service.dtoFactory.TagDtoFactory.getNewTagDtoId4;
import static com.epam.esm.service.dtoFactory.TagDtoFactory.getNewTagDtoList;
import static com.epam.esm.service.dtoFactory.TagDtoFactory.getTagDtoId1;
import static com.epam.esm.service.dtoFactory.TagDtoFactory.getTagDtoId5;
import static com.epam.esm.service.dtoFactory.TagDtoFactory.getTagDtoList;
import static com.epam.esm.service.entityFactory.EntityFactory.getNewCriteriaEntityWithDefaultVal;
import static com.epam.esm.service.entityFactory.TagEntityFactory.getNewTagEntity;
import static com.epam.esm.service.entityFactory.TagEntityFactory.getNewTagEntityId1;
import static com.epam.esm.service.entityFactory.TagEntityFactory.getNewTagEntityId3;
import static com.epam.esm.service.entityFactory.TagEntityFactory.getNewTagEntityList;
import static com.epam.esm.service.entityFactory.TagEntityFactory.getTagEntityId1;
import static com.epam.esm.service.entityFactory.TagEntityFactory.getTagEntityId5;
import static com.epam.esm.service.impl.GiftCertificateServiceImpl.RESOURCE_NOT_FOUND;
import static com.epam.esm.service.util.CriteriaUtil.criteriaDtoToEntityConverting;
import static com.epam.esm.service.util.CriteriaUtil.setDefaultPageValIfEmpty;
import static com.epam.esm.service.util.TagUtil.sortingValidation;
import static com.epam.esm.service.util.TagUtil.tagEntityListToDtoConverting;
import static com.epam.esm.service.util.TagUtil.updateFieldsInEntityFromDto;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class TagServiceImplImplTest {

    public static final String MESSAGE = "message";
    @Mock
    TagRepository tagRepository;

    @InjectMocks
    TagServiceImpl tagServiceImpl;

    @Test
    void readTagPaginatedTest() throws ServiceException, RepositoryException, ValidateException {
        CriteriaEntity crEntity = getNewCriteriaEntityWithDefaultVal();
        CriteriaDto crDto = getNewCriteriaDtoWithDefaultVal();
        List<TagEntity> tagEntityList = getNewTagEntityList();
        List<TagDto> tagDtoList = getNewTagDtoList();
        List<TagDto> actualDtoList;
        long totalSize = tagEntityList.size();


        try (MockedStatic<CriteriaUtil> crUtil = Mockito.mockStatic(CriteriaUtil.class);
             MockedStatic<TagUtil> tagUtil = Mockito.mockStatic(TagUtil.class)) {

            when(tagRepository.readPaginated(crEntity)).thenReturn(tagEntityList);
            tagUtil.when(() -> tagEntityListToDtoConverting(tagEntityList)).thenReturn(tagDtoList);
            crUtil.when(() -> criteriaDtoToEntityConverting(crDto)).thenReturn(crEntity);

            crEntity.setTotalSize(totalSize);
            actualDtoList = tagServiceImpl.readTagsPaginated(crDto);

            verify(tagRepository).readPaginated(crEntity);
            crUtil.verify(() -> criteriaDtoToEntityConverting(crDto));
            crUtil.verify(() -> setDefaultPageValIfEmpty(crDto));
            tagUtil.verify(() -> sortingValidation(crDto));
            assertEquals(tagDtoList, actualDtoList);
            assertEquals(totalSize, crDto.getTotalSize());
        }
    }

    @Test
    void readTagPaginatedWithExceptionTest() throws RepositoryException {
        CriteriaEntity crEntity = getNewCriteriaEntityWithDefaultVal();
        CriteriaDto crDto = getNewCriteriaDtoWithDefaultVal();
        List<TagEntity> tagEntityList = getNewTagEntityList();
        List<TagDto> tagDtoList = getNewTagDtoList();

        try (MockedStatic<CriteriaUtil> crUtil = Mockito.mockStatic(CriteriaUtil.class);
             MockedStatic<TagUtil> tagUtil = Mockito.mockStatic(TagUtil.class)) {

            when(tagRepository.readPaginated(crEntity)).thenThrow(new RepositoryException(MESSAGE));
            tagUtil.when(() -> tagEntityListToDtoConverting(tagEntityList)).thenReturn(tagDtoList);
            crUtil.when(() -> criteriaDtoToEntityConverting(crDto)).thenReturn(crEntity);

            ServiceException e = assertThrows(ServiceException.class,
                    () -> tagServiceImpl.readTagsPaginated(crDto));

            assertEquals(MESSAGE, e.getMessage());
        }
    }

    @Test
    void readTagByIdTest() throws ServiceException, RepositoryException {
        when(tagRepository.readById(anyLong()))
                .thenReturn(getNewTagEntityId1())
                .thenThrow(new RepositoryException("Incorrect result size: expected 1, actual 0"));

        TagDto actualTag = tagServiceImpl.readTagById(1);

        assertEquals(getTagDtoId1(), actualTag);
        verify(tagRepository).readById(1);
        ServiceException e = assertThrows(ServiceException.class, () -> tagServiceImpl.readTagById(111));
        assertEquals(RESOURCE_NOT_FOUND, e.getResourceBundleCode());
    }

    @Test
    void getTagIdOrCreateNewTagIsExistTest() throws ServiceException {
        List<TagDto> tagsDtoList = getNewTagDtoList();
        tagServiceImpl.setIdOrCreateTags(tagsDtoList);
        assertEquals(getTagDtoList(), tagsDtoList);
    }

    @Test
    void getMostWidelyTagTest() {
        try (MockedStatic<TagUtil> tagUtil = Mockito.mockStatic(TagUtil.class)) {
            tagUtil.when(() -> tagEntityListToDtoConverting(getNewTagEntityList()))
                    .thenReturn(getTagDtoList());
            when(tagRepository.findMostWidelyTag()).thenReturn(getNewTagEntityList());

            tagServiceImpl.getMostWidelyTag();

            verify(tagRepository).findMostWidelyTag();
            tagUtil.verify(() -> tagEntityListToDtoConverting(getNewTagEntityList()));
        }
    }


    @Test
    void getTagIdOrCreateNewTagNotExistTest() throws ServiceException, RepositoryException {
        TagDto expectedDtoId8 = getNewTagDto();
        expectedDtoId8.setId(8);
        TagEntity expectedEntityId8 = getNewTagEntity();
        expectedEntityId8.setId(8);
        List<TagDto> tagsDtoList = getNewTagDtoList();
        tagsDtoList.get(0).setId(0);
        tagsDtoList.add(getNewTagDto());

        List<TagEntity> tagsEntityList = getNewTagEntityList();
        tagsEntityList.add(getNewTagEntity());
        tagsEntityList.get(7).setId(8);

        when(tagRepository.readByName(getTagEntityId1().getName())).thenReturn(getTagEntityId1());
        when(tagRepository.readByName(getNewTagEntity().getName())).thenThrow(RepositoryException.class);
        when(tagRepository.merge(getNewTagEntity())).thenReturn(expectedEntityId8);

        tagServiceImpl.setIdOrCreateTags(tagsDtoList);
        for (int i = 0; i < 6; i++) {
            assertEquals(getTagDtoList().get(i), tagsDtoList.get(i));
        }
        assertEquals(expectedDtoId8, tagsDtoList.get(7));
    }

    @Test
    void getTagIdOrCreateNewTagInputNullTest() {
        assertDoesNotThrow(() -> tagServiceImpl.setIdOrCreateTags(null));
    }

    @Test
    void getTagIdOrCreateNewTagIsEmptyTest() {
        assertDoesNotThrow(() -> tagServiceImpl.setIdOrCreateTags(new ArrayList<>()));
    }

    @Test
    void readTagByName() throws RepositoryException, ServiceException {
        when(tagRepository.readByName("Auto")).thenReturn(getTagEntityId5()).thenThrow(new RepositoryException());
        when(tagRepository.readByName("noSuchName")).thenThrow(new RepositoryException());
        TagDto actualTag = tagServiceImpl.readTagByName("Auto");
        assertEquals(getTagDtoId5(), actualTag);

        verify(tagRepository).readByName("Auto");
//        assertThrows(ValidateException.class, () -> service.readTagByName(""));
//        assertThrows(ValidateException.class, () -> service.readTagByName(null));
        assertThrows(ServiceException.class, () -> tagServiceImpl.readTagByName("noSuchName"));
        assertThrows(ServiceException.class, () -> tagServiceImpl.readTagByName("Auto"));
    }

    @Test
    void createTagTest() throws RepositoryException, ServiceException {
        TagDto tagDto = getNewTagDto();
        TagEntity tagEntity = getNewTagEntity();
        tagServiceImpl.createTag(tagDto);

        verify(tagRepository).create(tagEntity);

        doThrow(RepositoryException.class).when(tagRepository).create(tagEntity);
        assertThrows(ServiceException.class, () -> tagServiceImpl.createTag(tagDto));
    }

    @Test
    void createTagSetIdToZeroTest() throws ServiceException {
        TagDto tagDto = getNewTagDtoId4();

        tagServiceImpl.createTag(tagDto);
        assertEquals(0, tagDto.getId());
    }

    @Test
    void updateTagTest() throws RepositoryException, ServiceException {
        TagDto oldTagDto = getNewTagDtoId3();
        TagDto newTagDto = getNewTagDtoId3();
        newTagDto.setName("new name");
        TagEntity oldTagEntity = getNewTagEntityId3();

        try (MockedStatic<TagUtil> util = Mockito.mockStatic(TagUtil.class)) {

            when(tagRepository.readById(newTagDto.getId())).thenReturn(oldTagEntity);
            tagServiceImpl.updateTag(newTagDto);
            util.verify(() -> updateFieldsInEntityFromDto(newTagDto, oldTagEntity));
        }

        verify(tagRepository).readById(3);
        assertNotEquals(oldTagDto, newTagDto);
//        assertThrows(ValidateException.class, () -> service.updateTag(new TagDto(-1, null)));
//        assertThrows(ValidateException.class, () -> service.updateTag(new TagDto(0, "tag")));
    }

    @Test
    void updateNotExistTagTest() throws RepositoryException {
        when(tagRepository.readById(anyLong()))
                .thenThrow(new RepositoryException("Incorrect result size: expected 1, actual 0"));

        ServiceException e = assertThrows(ServiceException.class,
                () -> tagServiceImpl.updateTag(getNewTagDtoId1()));
        assertEquals(RESOURCE_NOT_FOUND, e.getResourceBundleCode());
    }

    @Test
    void deleteTagTest() throws RepositoryException, ServiceException {
        tagServiceImpl.deleteTagById(1);
        verify(tagRepository).deleteById(1);

        doThrow(RepositoryException.class).doThrow(DataIntegrityViolationException.class)
                .when(tagRepository).deleteById(1);
        assertThrows(ServiceException.class, () -> tagServiceImpl.deleteTagById(1));
        assertThrows(ServiceException.class, () -> tagServiceImpl.deleteTagById(1));

//        assertThrows(ValidateException.class, () -> service.deleteTagById(-1));
    }

//    @Test
//    public void iterator_will_return_hello_world() {
//        //подготавливаем
//        Iterator i = mock(Iterator.class);
//        when(i.next()).thenReturn("Hello").thenReturn("World");
//        //выполняем
//        String result = i.next() + " " + i.next();
//        //сравниваем
//        System.out.println(result);
//        assertEquals("Hello World", result);
//    }
//
//    @Test
//    public void with_arguments() {
//        Comparable c = mock(Comparable.class);
//        when(c.compareTo("Tet")).thenReturn(1);
//        assertEquals(1, c.compareTo("Tet"));
//    }
}