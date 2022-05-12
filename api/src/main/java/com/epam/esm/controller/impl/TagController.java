package com.epam.esm.controller.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    public TagController(TagService service) {
        this.service = service;
    }

    /**
     * Read all tags
     *
     * @return all tags
     */
    @GetMapping
    public List<Tag> readAllTags() throws ValidateException, ServiceException {
        return service.readAllTags();
    }

    /**
     * Read tag by id
     *
     * @param id id tag for search
     * @return tag by id
     */
    @GetMapping("/{id}")
    public Tag readTag(@PathVariable String id)  throws ValidateException, ServiceException {
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
    public Tag crateTag (@RequestBody Tag tag)  throws ValidateException, ServiceException {
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
    public Tag updateTag (@RequestBody Tag tag)  throws ValidateException, ServiceException {
        service.updateTag(tag);
        return tag;
    }

    /**
     * Delete tag
     *
     * @param id id tag for delete
     * @return Updated tag
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public String deleteTag(@PathVariable String id)  throws ValidateException, ServiceException {
        service.deleteTag(id);
        return id;
    }
}
