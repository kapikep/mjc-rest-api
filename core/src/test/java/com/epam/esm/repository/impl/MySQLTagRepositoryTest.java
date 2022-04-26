package com.epam.esm.repository.impl;

import com.epam.esm.config.CoreConfig;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CoreConfig.class)
class MySQLTagRepositoryTest {
    @Autowired
    private TagRepository repository;

    @Test
    void readAllTags() throws RepositoryException {
        List<Tag>tags;
        tags = repository.readAllTags();
        tags.forEach(System.out::println);
    }

    @Test
    void readTagById() throws RepositoryException {
        Tag tag;
        tag = repository.readTag(6);
        System.out.println(tag);
    }

    @Test
    void readTagByName() throws RepositoryException {
        Tag tag;
        tag = repository.readTag("Вода");
        System.out.println(tag);
    }

    @Test
    void createTag() throws RepositoryException {
        Tag tag = new Tag("Обучение и мастер классы");
        repository.createTag(tag);
    }

    @Test
    void updateTag()  throws RepositoryException {
        Tag tag = new Tag(7,"Обучение и мастер классы");
        repository.updateTag(tag);
    }
}