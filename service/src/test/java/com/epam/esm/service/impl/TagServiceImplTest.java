package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.TagRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("dev")
@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    TagRepository repository;

    @InjectMocks
    TagServiceImpl service;

    List<TagDto> tagsDtoList = getTagDtoList();
    List<TagEntity> tagsEntityList = getTagEntityList();

    @Test
    void readAllTags() throws RepositoryException, ValidateException, ServiceException {
        when(repository.readAllTags()).thenReturn(getTagEntityList()).thenThrow(new RepositoryException());

        List<TagDto> actualTags = service.readAllTags();
        assertEquals(getTagDtoList(), actualTags);

        assertThrows(ServiceException.class, () -> service.readAllTags());
    }

    @Test
    void readTagInt() throws ValidateException, ServiceException, RepositoryException {
        when(repository.readTag(1)).thenReturn(getTagEntityId1()).thenThrow(new RepositoryException());

        TagDto actualTag = service.readTag(1);
        assertEquals(getTagDtoId1(), actualTag);
        verify(repository, times(1)).readTag(1);

        ValidateException e = assertThrows(ValidateException.class, () -> service.readTag(-2));
        assertThrows(ServiceException.class, () -> service.readTag(1));
    }

    @Test
    void readTagString() throws RepositoryException, ValidateException, ServiceException {
        when(repository.readTag(1)).thenReturn(getTagEntityId1()).thenThrow(new RepositoryException());

        TagDto actualTag = service.readTag("1");
        assertEquals(getTagDtoId1(), actualTag);

        verify(repository, times(1)).readTag(1);

        ValidateException e = assertThrows(ValidateException.class, () -> service.readTag("abc"));
        assertThrows(ServiceException.class, () -> service.readTag("1"));
    }

    @Test
    void getTagIdOrCreateNewTagIsExist() throws ServiceException, ValidateException, RepositoryException {
        when(repository.readTagByName(getTagDtoId1().getName())).thenReturn(getTagEntityId1());
        when(repository.readTagByName(getTagDtoId2().getName())).thenReturn(getTagEntityId2());
        when(repository.readTagByName(getTagDtoId5().getName())).thenReturn(getTagEntityId5());

        service.getIdOrCreateTagsInList(tagsDtoList);
        assertEquals(1, tagsDtoList.get(0).getId());
        assertEquals(2, tagsDtoList.get(1).getId());
        assertEquals(5, tagsDtoList.get(2).getId());
    }

    @Test
    void getTagIdOrCreateNewTagNotExist() throws ServiceException, ValidateException, RepositoryException {
        when(repository.readTagByName(tagsEntityList.get(0).getName())).thenReturn(tagsEntityList.get(0));
        when(repository.readTagByName(tagsEntityList.get(1).getName())).thenThrow(RepositoryException.class);
        when(repository.readTagByName(tagsEntityList.get(2).getName())).thenThrow(RepositoryException.class);
        when(repository.createTag(tagsEntityList.get(1))).thenReturn(6);
        when(repository.createTag(tagsEntityList.get(2))).thenReturn(7);

        service.getIdOrCreateTagsInList(tagsDtoList);
        assertEquals(1, tagsDtoList.get(0).getId());
        assertEquals(6, tagsDtoList.get(1).getId());
        assertEquals(7, tagsDtoList.get(2).getId());
    }

    @Test
    void getTagIdOrCreateNewTagInputNull() {
        assertDoesNotThrow(() -> service.getIdOrCreateTagsInList(null));
    }

    @Test
    void getTagIdOrCreateNewTagIsEmpty() {
        assertDoesNotThrow(() -> service.getIdOrCreateTagsInList(new ArrayList<>()));
    }

    @Test
    void readTagByName() throws RepositoryException, ValidateException, ServiceException {
        when(repository.readTagByName("Auto")).thenReturn(getTagEntityId5()).thenThrow(new RepositoryException());
        when(repository.readTagByName("noSuchName")).thenThrow(new RepositoryException());
        TagDto actualTag = service.readTagByName("Auto");
        assertEquals(getTagDtoId5(), actualTag);

        verify(repository, times(1)).readTagByName("Auto");

        assertThrows(ValidateException.class, () -> service.readTagByName(""));
        assertThrows(ValidateException.class, () -> service.readTagByName(null));
        assertThrows(ServiceException.class, () -> service.readTagByName("noSuchName"));
        assertThrows(ServiceException.class, () -> service.readTagByName("Auto"));
    }

    @Test
    void createTag() throws RepositoryException, ValidateException, ServiceException {
        service.createTag(getTagDtoId1());
        verify(repository, times(1)).createTag(getTagEntityId1());

        doThrow(new RepositoryException()).when(repository).createTag(getTagEntityId1());
        assertThrows(ServiceException.class, () -> service.createTag(getTagDtoId1()));

        assertThrows(ValidateException.class, () -> service.createTag(new TagDto(1, null)));
    }

    @Test
    void updateTag() throws RepositoryException, ValidateException, ServiceException {
        service.updateTag(getTagDtoId1());
        verify(repository, times(1)).updateTag(getTagEntityId1());

        doThrow(new RepositoryException()).when(repository).updateTag(getTagEntityId1());
        assertThrows(ServiceException.class, () -> service.updateTag(getTagDtoId1()));

        assertThrows(ValidateException.class, () -> service.updateTag(new TagDto(-1, null)));
        assertThrows(ValidateException.class, () -> service.updateTag(new TagDto(0, "tag")));
    }

    @Test
    void deleteTag() throws RepositoryException, ValidateException, ServiceException {
        service.deleteTag("1");
        verify(repository, times(1)).deleteTag(1);

        doThrow(new RepositoryException()).when(repository).deleteTag(anyInt());
        assertThrows(ServiceException.class, () -> service.deleteTag("1"));

        assertThrows(ValidateException.class, () -> service.deleteTag("abc"));
        assertThrows(ValidateException.class, () -> service.deleteTag("-1"));
    }

    private List<TagEntity> getTagEntityList(){
        List<TagEntity> tagEntityList = new ArrayList<>();
        tagEntityList.add(getTagEntityId1());
        tagEntityList.add(getTagEntityId2());
        tagEntityList.add(getTagEntityId5());
        return tagEntityList;
    }

    private List<TagDto> getTagDtoList(){
        List<TagDto> tagDtoList = new ArrayList<>();
        tagDtoList.add(getTagDtoId1());
        tagDtoList.add(getTagDtoId2());
        tagDtoList.add(getTagDtoId5());
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

    private TagDto getTagDtoId1() {
        return new TagDto(1, "Sport");
    }

    private TagDto getTagDtoId2() {
        return new TagDto(2, "Water");
    }

    private TagDto getTagDtoId5() {
        return new TagDto(5, "Auto");
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