package com.epam.esm.service.interf;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.validator.groups.OnCreate;
import com.epam.esm.service.validator.groups.OnUpdate;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
/**
 * Service for tags
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Validated
public interface TagService {
    List<TagDto> readPage(@Valid CriteriaDto cr) throws ServiceException, ValidateException;

    /**
     * Validates id and reads tag by id from repository
     *
     * @return tag from repository
     */
    TagDto readOne(String id) throws ServiceException, ValidateException;

    /**
     * Validates id and reads tag by id from repository
     *
     * @return tag from repository
     */
    TagDto readOne(@Positive long id) throws ServiceException, ValidateException;

    /**
     * Validates name and reads tag by name from repository
     *
     * @return tag from repository
     */
    TagDto readByName(@NotEmpty @Size(min = 2, max = 20) String name) throws ServiceException, ValidateException;

    List<TagDto> getMostWidelyTag() throws ServiceException;

    /**
     * Validates tags. Update tags id in list from db, if tag not exist - creates new, and
     * write id in list
     *
     * @param tags list for get ids
     */
    @Validated(OnCreate.class)
    void getIdOrCreateTags(@Valid List<TagDto> tags) throws ServiceException, ValidateException;

    /**
     * Validates tag fields and creates tag in repository
     *
     * @return id for created tag
     */
    @Validated(OnCreate.class)
    void create(@Valid TagDto tag) throws ServiceException, ValidateException;

    /**
     * Updates tag in repository
     *
     * @param tag tag to update
     */
    @Validated(OnUpdate.class)
    void update(@Valid TagDto tag) throws ServiceException, ValidateException;

    /**
     * Validates id and deletes tag by id in repository
     */
    void delete(@Positive long id) throws ServiceException, ValidateException;
}
