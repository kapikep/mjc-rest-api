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

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;

import static com.epam.esm.controller.util.PaginationUtil.getSelfLink;

/**
 * Handles requests to /tags url
 *
 * @author Artsemi Kapitula
 * @version 2.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagRestController {
    private final TagService tagService;
    private final MessageSource messageSource;

    /**
     * Read TagDto paginated. If parameter name is exist, read TagDto by name.
     * Add self links to TagDto.
     * Add pagination information.
     *
     * @param name read TagDto by name. The parameter is optional
     * @param page page to read. The parameter is optional. Default value 1.
     * @param size size of page. The parameter is optional. Default value 20.
     * @param sort sorting field. The parameter is optional.
     * @return TagDto list
     * @throws ServiceException  if page or size is null or less 1.
     *                           If the page is larger than the total size of the pages.
     *                           If any RepositoryException or DataAccessException has occurred.
     * @throws ValidateException if sorting field does not match TAG_SORT_PARAM.
     */
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
                : tagService.readTagsPaginated(cr);

        tags.forEach(tag -> tag.add(getSelfLink(TagRestController.class, tag.getId())));

        if (name != null) {
            return PagedModel.of(tags, new PageMetadata(1, 1, 1));
        }

        PagedModel<TagDto> pagedModel = PaginationUtil.createPagedModel(tags, cr);
        PaginationUtil.addPaginationLinks(pagedModel);
        return pagedModel;
    }

    /**
     * Find the most widely used tag of a user with the highest cost of all orders.
     * If there are several users or tags match to the condition,
     * all matching tags are returned.
     *
     * @return List with TagDto.
     */
    @GetMapping("most-widely")
    public List<TagDto> getMostWidelyTag() {
        List<TagDto> tags = tagService.getMostWidelyTag();
        tags.forEach(tag -> tag.add(getSelfLink(TagRestController.class, tag.getId())));
        return tags;
    }

    /**
     * Read TagDto by id.
     * Add self links to TagDto.
     *
     * @param id id for TagDto search
     * @return TagDto by id.
     * @throws ServiceException             if TagDto with id does not exist.
     * @throws ConstraintViolationException if id is not positive.
     */
    @GetMapping("/{id}")
    public TagDto readTag(@PathVariable long id) throws ServiceException {
        TagDto tag = tagService.readTagById(id);
        tag.add(getSelfLink(TagRestController.class, tag.getId()));
        return tag;
    }

    /**
     * Create new TagDto.
     * Add self links to TagDto.
     *
     * @param tag TagDto to create.
     * @return Created TagDto
     * @throws ServiceException             TagDto tag name is not unique.
     * @throws ConstraintViolationException if TagDto fields is constraint violation.
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public TagDto crateTag(@RequestBody TagDto tag) throws ServiceException {
        tagService.createTag(tag);
        tag.add(getSelfLink(TagRestController.class, tag.getId()));
        return tag;
    }

    /**
     * Update TagDto
     * Add self links to TagDto.
     *
     * @param id TagDto id.
     * @param tag TagDto to update
     * @return Updated TagDto
     * @throws ServiceException             if tag name is not unique.
     * @throws ConstraintViolationException if TagDto fields is constraint violation
     * @throws ServiceException             if tag name is not unique.
     *                                      If there is a problem with updating tag.
     */
    @PutMapping("/{id}")
    public TagDto updateTag(@PathVariable long id,
                            @RequestBody TagDto tag) throws ServiceException {
        tag.setId(id);
        tagService.updateTag(tag);
        tag.add(getSelfLink(TagRestController.class, tag.getId()));
        return tag;
    }

    /**
     * Delete tag by id.
     *
     * @param id id tag to delete
     * @return informational message about success
     * @throws ServiceException if tag with this id does not exist in repository.
     *                          If tag is linked to any gift certificate.
     */
    @DeleteMapping(value = "/{id}", produces = "text/plain;charset=UTF-8")
    public String deleteTag(@PathVariable Long id) throws ServiceException {
        tagService.deleteTagById(id);
        return messageSource.getMessage("tag.deleted", new Object[]{id}, LocaleContextHolder.getLocale());
    }
}
