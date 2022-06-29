package com.epam.esm.controller.impl;

import com.epam.esm.controller.util.PaginationUtil;
import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.repository.interf.TagRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.TagService;
import com.epam.esm.service.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Handles requests to /tags url
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@RestController
@RequestMapping("/tags")
@Validated
public class TagController {
    private final TagService service;
    private final MessageSource source;

    public TagController(TagService service, MessageSource source) {
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
        PagedModel<TagDto> pagedModel;

        CriteriaDto cr = new CriteriaDto();
        cr.setPage(page);
        cr.setSize(size);
        cr.setSorting(sort);

        if (name != null) {
            tags = Collections.singletonList(service.readTagByName(name));
        } else {
            tags = service.readPage(cr);
        }

        for (TagDto tag : tags) {
            tag.add(PaginationUtil.getSelfLink(TagController.class, tag.getId()));
        }

        if (name != null){
            return PagedModel.of(tags,
                    new PageMetadata(1, 1, 1));
        }

        pagedModel = PaginationUtil.createPagedModel(tags, cr);
        PaginationUtil.addPaginationLinks(pagedModel);
        return pagedModel;
    }

    /**
     * Read tag by id
     *
     * @param id id tag for search
     * @return tag by id
     */
    @GetMapping("/old/{id}")
    public TagDto readTag(@PathVariable String id) throws ValidateException, ServiceException {
        TagDto tag = service.readTag(id);
        tag.add(PaginationUtil.getSelfLink(TagController.class, tag.getId()));
        return tag;
    }

    @GetMapping("/{id}")
    public TagDto readTag(@PathVariable Long id) throws ValidateException, ServiceException {
        TagDto tag = service.readTag(id);
//        tag.add(linkTo(methodOn(TagController.class).readTag(id)).withSelfRel());
        tag.add(PaginationUtil.getSelfLink(TagController.class, tag.getId()));
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
        service.createTag(tag);
        return tag;
    }

    /**
     * Update tag
     *
     * @param tag tag for update
     * @return Updated tag
     */
    @PutMapping("/{id}")
    public TagDto updateTag(@PathVariable String id,
                            @RequestBody @Valid TagDto tag) throws ValidateException, ServiceException {
        tag.setId(ServiceUtil.parseInt(id));
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
    public String deleteTag(@PathVariable String id) throws ValidateException, ServiceException {
        service.deleteTag(id);
        return source.getMessage("tag.deleted", new Object[]{id}, LocaleContextHolder.getLocale());
    }
}
