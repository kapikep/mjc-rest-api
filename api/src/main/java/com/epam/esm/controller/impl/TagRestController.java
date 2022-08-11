package com.epam.esm.controller.impl;

import com.epam.esm.controller.util.PaginationUtil;
import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagRestController {
    private final TagService tagService;
    private final MessageSource messageSource;

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

        tags = name != null ? Collections.singletonList(tagService.readTagByName(name))
                : tagService.readAllTagsPaginated(cr);

        tags.forEach(tag -> tag.add(getSelfLink(TagRestController.class, tag.getId())));

        if (name != null){
            return PagedModel.of(tags,
                    new PageMetadata(1, 1, 1));
        }

        PagedModel<TagDto> pagedModel = PaginationUtil.createPagedModel(tags, cr);
        PaginationUtil.addPaginationLinks(pagedModel);
        return pagedModel;
    }

    @GetMapping("most-widely")
    public List<TagDto> getMostWidelyTag() throws ServiceException {
        List<TagDto> tags = tagService.getMostWidelyTag();

        tags.forEach(tag -> tag.add(getSelfLink(TagRestController.class, tag.getId())));

        return tags;
    }

    /**
     * Read tag by id
     *
     * @param id id tag for search
     * @return tag by id
     */
    @GetMapping("/{id}")
    public TagDto readTag(@PathVariable long id) throws ValidateException, ServiceException {
        TagDto tag = tagService.readTagById(id);
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
        tagService.createTag(tag);
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
        tagService.updateTag(tag);
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
        tagService.deleteTagById(id);
        return messageSource.getMessage("tag.deleted", new Object[]{id}, LocaleContextHolder.getLocale());
    }
}
