package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.TagRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
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

    Tag expectedTag;
    List<Tag> tags;

    @BeforeEach
    void init() {
        expectedTag = new Tag(1, "name");

        tags = new ArrayList<>();
        tags.add(expectedTag);
        tags.add(new Tag(2, "New tag1"));
    }

    @Test
    void readAllTags() throws RepositoryException, ValidateException, ServiceException {
        when(repository.readAllTags()).thenReturn(tags).thenThrow(new RepositoryException());
        List<Tag> actualTags = service.readAllTags();
        assertIterableEquals(tags, actualTags);

        assertThrows(ServiceException.class, () -> service.readAllTags());
    }

    @Test
    void readTagInt() throws ValidateException, ServiceException, RepositoryException {
        when(repository.readTag(1)).thenReturn(expectedTag).thenThrow(new RepositoryException());

        Tag actualTag = service.readTag(1);
        assertEquals(expectedTag, actualTag);
        verify(repository, times(1)).readTag(1);

        ValidateException e = assertThrows(ValidateException.class, () -> service.readTag(-2));
        assertThrows(ServiceException.class, () -> service.readTag(1));
    }

    @Test
    void readTagString() throws RepositoryException, ValidateException, ServiceException {
        when(repository.readTag(1)).thenReturn(expectedTag).thenThrow(new RepositoryException());

        Tag actualTag = service.readTag("1");
        assertEquals(expectedTag, actualTag);

        verify(repository, times(1)).readTag(1);

        ValidateException e = assertThrows(ValidateException.class, () -> service.readTag("abc"));
        assertThrows(ServiceException.class, () -> service.readTag("1"));
    }

    @Test
    void getTagIdOrCreateNewTagIsExist() throws ServiceException, ValidateException, RepositoryException {
        when(repository.readTagByName(tags.get(0).getName())).thenReturn(tags.get(0));
        when(repository.readTagByName(tags.get(1).getName())).thenReturn(tags.get(1));

        service.getIdOrCreateTagsInList(tags);
        assertEquals(1, tags.get(0).getId());
        assertEquals(2, tags.get(1).getId());
    }

    @Test
    void getTagIdOrCreateNewTagNotExist() throws ServiceException, ValidateException, RepositoryException {
        when(repository.readTagByName(tags.get(0).getName())).thenReturn(tags.get(0));
        when(repository.readTagByName(tags.get(1).getName())).thenThrow(RepositoryException.class);
        when(repository.createTag(tags.get(1))).thenReturn(2);

        service.getIdOrCreateTagsInList(tags);
        assertEquals(1, tags.get(0).getId());
        assertEquals(2, tags.get(1).getId());
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
        when(repository.readTagByName("name")).thenReturn(expectedTag).thenThrow(new RepositoryException());
        when(repository.readTagByName("noSuchName")).thenThrow(new RepositoryException());
        Tag actualTag = service.readTagByName("name");
        assertEquals(expectedTag, actualTag);

        verify(repository, times(1)).readTagByName("name");

        assertThrows(ValidateException.class, () -> service.readTagByName(""));
        assertThrows(ValidateException.class, () -> service.readTagByName(null));
        assertThrows(ServiceException.class, () -> service.readTagByName("noSuchName"));
        assertThrows(ServiceException.class, () -> service.readTagByName("name"));
    }

    @Test
    void createTag() throws RepositoryException, ValidateException, ServiceException {
        service.createTag(expectedTag);
        verify(repository, times(1)).createTag(expectedTag);

        doThrow(new RepositoryException()).when(repository).createTag(expectedTag);
        assertThrows(ServiceException.class, () -> service.createTag(expectedTag));

        assertThrows(ValidateException.class, () -> service.createTag(new Tag(1, null)));
    }

    @Test
    void updateTag() throws RepositoryException, ValidateException, ServiceException {
        service.updateTag(expectedTag);
        verify(repository, times(1)).updateTag(expectedTag);

        doThrow(new RepositoryException()).when(repository).updateTag(expectedTag);
        assertThrows(ServiceException.class, () -> service.updateTag(expectedTag));

        assertThrows(ValidateException.class, () -> service.updateTag(new Tag(-1, null)));
        assertThrows(ValidateException.class, () -> service.updateTag(new Tag(0, "tag")));
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