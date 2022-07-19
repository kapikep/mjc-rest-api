package com.epam.esm.controller.impl;

import com.epam.esm.controller.util.PaginationUtil;
import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.TagService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.*;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static com.epam.esm.controller.util.PaginationUtil.getSelfLink;

/**
 * Handles requests to /tags url
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@RestController
@RequestMapping("/tags")
@Validated
public class TagRestController {
    private final TagService service;
    private final MessageSource source;

    public TagRestController(TagService service, MessageSource source) {
        this.service = service;
        this.source = source;
    }

    @GetMapping
    public PagedModel<TagDto> readTags(
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "page") Integer page,
            @RequestParam(required = false, name = "size") Integer size,
            @RequestParam(required = false, name = "sort") String sort) throws ValidateException, ServiceException {
        List<TagDto> tags;

        CriteriaDto cr = new CriteriaDto();
        cr.setPage(page);
        cr.setSize(size);
        cr.setSorting(sort);

        if (name != null) {
            tags = Collections.singletonList(service.readByName(name));
        } else {
            tags = service.readPage(cr);
        }

        for (TagDto tag : tags) {
            tag.add(getSelfLink(TagRestController.class, tag.getId()));
        }

        if (name != null){
            return PagedModel.of(tags,
                    new PageMetadata(1, 1, 1));
        }

        PagedModel<TagDto> pagedModel = PaginationUtil.createPagedModel(tags, cr);
        PaginationUtil.addPaginationLinks(pagedModel);
        return pagedModel;
    }

    /**
     * Read tag by id
     *
     * @param id id tag for search
     * @return tag by id
     */
    @GetMapping("/{id}")
    public TagDto readTag(@PathVariable long id) throws ValidateException, ServiceException {
        TagDto tag = service.readOne(id);
        tag.add(getSelfLink(TagRestController.class, tag.getId()));
        return tag;
    }

    /**
     * Create new tag
     *
     * @param tag tag for create
     * @return Created tag
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public TagDto crateTag(@RequestBody TagDto tag) throws ValidateException, ServiceException {
        service.create(tag);
        tag.add(getSelfLink(TagRestController.class, tag.getId()));
        return tag;
    }

    /**
     * Update tag
     *
     * @param tag tag for update
     * @return Updated tag
     */
    @PutMapping("/{id}")
    public TagDto updateTag(@PathVariable long id,
                            @RequestBody TagDto tag) throws ValidateException, ServiceException {
        tag.setId(id);
        service.update(tag);
        tag.add(getSelfLink(TagRestController.class, tag.getId()));
        return tag;
    }

    /**
     * Delete tag
     *
     * @param id id tag for delete
     * @return Updated tag
     */
    @DeleteMapping(value = "/{id}", produces = "text/plain;charset=UTF-8")
    public String deleteTag(@PathVariable Long id) throws ValidateException, ServiceException {
        service.delete(id);
        return source.getMessage("tag.deleted", new Object[]{id}, LocaleContextHolder.getLocale());
    }
}
