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

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    TagRepository repository;

    @InjectMocks
    TagServiceImpl service;

    List<TagDto> tagsDtoList = getTagDtoList();
    List<TagEntity> tagsEntityList = getTagEntityList();

    @Test
    void readTagInt() throws ValidateException, ServiceException, RepositoryException {
        when(repository.readOne(1)).thenReturn(getTagEntityId1()).thenThrow(new RepositoryException());

        TagDto actualTag = service.readOne(1);
        assertEquals(getTagDtoId1(), actualTag);
        verify(repository, times(1)).readOne(1);

        ValidateException e = assertThrows(ValidateException.class, () -> service.readOne(-2));
        assertThrows(ServiceException.class, () -> service.readOne(1));
    }

    @Test
    void readTagString() throws RepositoryException, ValidateException, ServiceException {
        when(repository.readOne(1)).thenReturn(getTagEntityId1()).thenThrow(new RepositoryException());

        TagDto actualTag = service.readOne("1");
        assertEquals(getTagDtoId1(), actualTag);

        verify(repository, times(1)).readOne(1);

        ValidateException e = assertThrows(ValidateException.class, () -> service.readOne("abc"));
        assertThrows(ServiceException.class, () -> service.readOne("1"));
    }

    @Test
    void getTagIdOrCreateNewTagIsExist() throws ServiceException, ValidateException, RepositoryException {
        when(repository.readByName(getTagDtoId1().getName())).thenReturn(getTagEntityId1());
        when(repository.readByName(getTagDtoId2().getName())).thenReturn(getTagEntityId2());
        when(repository.readByName(getTagDtoId5().getName())).thenReturn(getTagEntityId5());

        service.getIdOrCreateTagsInList(tagsDtoList);
        assertEquals(1, tagsDtoList.get(0).getId());
        assertEquals(2, tagsDtoList.get(1).getId());
        assertEquals(5, tagsDtoList.get(2).getId());
    }

    @Test
    void getTagIdOrCreateNewTagNotExist() throws ServiceException, ValidateException, RepositoryException {
        when(repository.readByName(tagsEntityList.get(0).getName())).thenReturn(tagsEntityList.get(0));
        when(repository.readByName(tagsEntityList.get(1).getName())).thenThrow(RepositoryException.class);
        when(repository.readByName(tagsEntityList.get(2).getName())).thenThrow(RepositoryException.class);
//        when(repository.create(tagsEntityList.get(1))).thenReturn(6);
//        when(repository.create(tagsEntityList.get(2))).thenReturn(7);

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
        when(repository.readByName("Auto")).thenReturn(getTagEntityId5()).thenThrow(new RepositoryException());
        when(repository.readByName("noSuchName")).thenThrow(new RepositoryException());
        TagDto actualTag = service.readByName("Auto");
        assertEquals(getTagDtoId5(), actualTag);

        verify(repository, times(1)).readByName("Auto");

        assertThrows(ValidateException.class, () -> service.readByName(""));
        assertThrows(ValidateException.class, () -> service.readByName(null));
        assertThrows(ServiceException.class, () -> service.readByName("noSuchName"));
        assertThrows(ServiceException.class, () -> service.readByName("Auto"));
    }

    @Test
    void createTag() throws RepositoryException, ValidateException, ServiceException {
        service.create(getTagDtoId1());
        verify(repository, times(1)).create(getTagEntityId1());

        doThrow(new RepositoryException()).when(repository).create(getTagEntityId1());
        assertThrows(ServiceException.class, () -> service.create(getTagDtoId1()));

        assertThrows(ValidateException.class, () -> service.create(new TagDto(1, null)));
    }

    @Test
    void updateTag() throws RepositoryException, ValidateException, ServiceException {
        service.update(getTagDtoId1());
        verify(repository, times(1)).update(getTagEntityId1());

        doThrow(new RepositoryException()).when(repository).update(getTagEntityId1());
        assertThrows(ServiceException.class, () -> service.update(getTagDtoId1()));

        assertThrows(ValidateException.class, () -> service.update(new TagDto(-1, null)));
        assertThrows(ValidateException.class, () -> service.update(new TagDto(0, "tag")));
    }

    @Test
    void deleteTag() throws RepositoryException, ValidateException, ServiceException {
        service.delete(1);
        verify(repository, times(1)).deleteById(1);

        doThrow(new RepositoryException()).when(repository).deleteById(anyInt());
        assertThrows(ServiceException.class, () -> service.delete(1));

        assertThrows(ValidateException.class, () -> service.delete(-1));
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