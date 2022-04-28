package com.epam.esm.controller.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.TagService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService service;

    public TagController(TagService service) {
        this.service = service;
    }

    @GetMapping
    public List<Tag> readAllTags() throws ValidateException, ServiceException {
        return service.readAllTags();
    }

    @GetMapping("/{id}")
    public Tag readTag(@PathVariable String id)  throws ValidateException, ServiceException {
        return service.readTag(id);
    }

    @PostMapping
    public Tag crateTag (@RequestBody Tag tag)  throws ValidateException, ServiceException {
        service.createTag(tag);
        return tag;
    }

    @PutMapping
    public Tag updateTag (@RequestBody Tag tag)  throws ValidateException, ServiceException {
        service.updateTag(tag);
        return tag;
    }

    @DeleteMapping("/{id}")
    public String deleteTag(@PathVariable String id)  throws ValidateException, ServiceException {
        service.deleteTag(id);
        return id;
    }
}
