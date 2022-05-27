package com.epam.esm.controller.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.TagService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Handles requests to /tags url
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService service;
    private final MessageSource source;

    public TagController(TagService service, MessageSource source) {
        this.service = service;
        this.source = source;
    }

    /**
     * Read all tags
     *
     * @return all tags
     */
    @GetMapping
    public List<TagDto> findTags(
            @RequestParam(required = false, name = "name") String name) throws ValidateException, ServiceException {
        if(name != null){
            return Collections.singletonList(service.readTagByName(name));
        }else {
            return service.readAllTags();
        }
    }

    /**
     * Read tag by id
     *
     * @param id id tag for search
     * @return tag by id
     */
    @GetMapping("/{id}")
    public TagDto readTag(@PathVariable String id)  throws ValidateException, ServiceException {
        return service.readTag(id);
    }

    /**
     * Create new tag
     *
     * @param tag tag for create
     * @return Created tag
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public TagDto crateTag (@RequestBody TagDto tag)  throws ValidateException, ServiceException {
        int id = service.createTag(tag);
        tag.setId(id);
        return tag;
    }

    /**
     * Update tag
     *
     * @param tag tag for update
     * @return Updated tag
     */
    @PutMapping
    public TagDto updateTag (@RequestBody TagDto tag)  throws ValidateException, ServiceException {
        service.updateTag(tag);
        return tag;
    }

    /**
     * Delete tag
     *
     * @param id id tag for delete
     * @return Updated tag
     */
    @DeleteMapping(value = "/{id}", produces = "text/plain;charset=UTF-8")
    public String deleteTag(@PathVariable String id)  throws ValidateException, ServiceException {
        service.deleteTag(id);
        return source.getMessage("tag.deleted", new Object[]{id}, LocaleContextHolder.getLocale());
    }
}
