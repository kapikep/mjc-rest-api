package com.epam.esm.controller.impl;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.repository.interf.TagRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.TagService;
import com.epam.esm.service.utils.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
public class TagController {
    private final TagService service;
    private final MessageSource source;

    @Autowired
    private TagRepository repository;
    //    @Autowired
//    private ActorModelAssembler actorModelAssembler;
//
//    @Autowired
//    private AlbumModelAssembler albumModelAssembler;
//
    @Autowired
    private PagedResourcesAssembler<TagDto> pagedResourcesAssembler;

    public TagController(TagService service, MessageSource source) {
        this.service = service;
        this.source = source;
    }

    /**
     * Read all tags
     *
     * @return all tags
     */
    @GetMapping(value = "/v1", produces = {"application/hal+json"})
    public List<TagDto> readTags(
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "page") String page,
            @RequestParam(required = false, name = "size") String size,
            @RequestParam(required = false, name = "sort") String sort) throws ValidateException, ServiceException {
        List<TagDto> tags;
//        PagedModel<List<TagDto>> pagedModel;
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
            tag.add(linkTo(TagController.class).slash(tag.getId()).withSelfRel());
        }
//        PagedModel.PageMetadata pm = new PagedModel.PageMetadata(1,2,3);
//        pagedModel = new PagedModel<List<TagDto>>(tags, pm);


        return tags;
    }

    @GetMapping("/v2")
    public CollectionModel<TagDto> readTags1(
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "page") String page,
            @RequestParam(required = false, name = "size") String size,
            @RequestParam(required = false, name = "sort") String sort) throws ValidateException, ServiceException {
        List<TagDto> tags;
//        PagedModel<List<TagDto>> pagedModel;
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
            tag.add(linkTo(TagController.class).slash(tag.getId()).withSelfRel());
        }

        int pageInt = (ServiceUtil.parseInt(page));
        Link selfRel = linkTo(TagController.class).withSelfRel();
        Link first = linkTo(TagController.class).slash("page=1").withRel("first");
        Link next = linkTo(TagController.class).slash("page=" + (pageInt + 1)).withRel("next");
        Link prev = linkTo(methodOn(TagController.class).readTags1(name, String.valueOf(pageInt - 1), size, sort)).withRel("prev").expand();

        Link expand = linkTo(TagController.class, "page=" + page).withRel("expand");

        Map<String, String> map = new HashMap<>();
        map.put("sort", sort);
        Link expand1 = linkTo(TagController.class, map).withRel("EXP");
        expand1.expand();
        CollectionModel<TagDto> res = CollectionModel.of(tags, selfRel, first, next, prev, expand, expand1);

//        PagedModel.PageMetadata pm = new PagedModel.PageMetadata(1,2,3);
//        pagedModel = new PagedModel<List<TagDto>>(tags, pm);

        return res;
    }

    @GetMapping("/v3")
    public PagedModel<TagDto> readTags2(
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "page") String page,
            @RequestParam(required = false, name = "size") String size,
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
            tag.add(linkTo(TagController.class).slash(tag.getId()).withSelfRel());
        }

        int sizeInt = ServiceUtil.parseInt(size);
        int pageInt = ServiceUtil.parseInt(page);
        long total = repository.totalSize();

        PageMetadata pm = new PageMetadata(sizeInt, pageInt, total);
        System.out.println(total);
        pagedModel = PagedModel.of(tags, pm);

        return pagedModel;
    }

    @GetMapping("/v4")
    public PagedModel<EntityModel<TagDto>> readTags3(
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "page") String page,
            @RequestParam(required = false, name = "size") String size,
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
            tag.add(linkTo(TagController.class).slash(tag.getId()).withSelfRel());
        }

        int sizeInt = ServiceUtil.parseInt(size);
        int pageInt = ServiceUtil.parseInt(page);
        long total = repository.totalSize();

        PageMetadata pm = new PageMetadata(sizeInt, pageInt, total);
        pagedModel = PagedModel.of(tags, pm);

        PageRequest pageRequest = PageRequest.of(pageInt, sizeInt, Sort.by(sort));
        Page<TagDto> page1 = new PageImpl<TagDto>(tags, pageRequest, total);
        System.out.println(total);
        PagedModel<EntityModel<TagDto>> pagedModel1 = pagedResourcesAssembler.toModel(page1);

        return pagedModel1;
    }

    @GetMapping("/v5")
    public PagedModel<EntityModel<TagDto>> readTags4(Pageable pageable, @RequestParam(required = false, name = "name") String name) throws ValidateException, ServiceException {
        List<TagDto> tags;
//        System.out.println(pageable);

        CriteriaDto cr = new CriteriaDto();
        cr.setPage(String.valueOf(pageable.getPageNumber()));
        cr.setSize(String.valueOf(pageable.getPageSize()));
        System.out.println(pageable.getSort());
//        cr.setSorting(String.valueOf(pageable.getSort()));

        tags = service.readPage(cr);

        for (TagDto tag : tags) {
            tag.add(linkTo(TagController.class).slash(tag.getId()).withSelfRel());
        }

        long total = repository.totalSize();

        Page<TagDto> page1 = new PageImpl<TagDto>(tags, pageable, total);
        PagedModel<EntityModel<TagDto>> pagedModel1 = pagedResourcesAssembler.toModel(page1);

        return pagedModel1;
    }

    /**
     * Read tag by id
     *
     * @param id id tag for search
     * @return tag by id
     */
    @GetMapping("/{id}")
    public TagDto readTag(@PathVariable String id) throws ValidateException, ServiceException {
        TagDto tag = service.readTag(id);
//        tag.add(linkTo(methodOn(TagController.class).readTag(id)).withSelfRel());
        tag.add(linkTo(TagController.class).slash(tag.getId()).withSelfRel());
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
