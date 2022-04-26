package com.epam.esm.controller.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class TagController {
    private final TagService service;

    public TagController(TagService service) {
        this.service = service;
    }

    @GetMapping("/tags")
    public List<Tag> readAllTags() throws ValidateException, ServiceException {
        return service.readAllTags();
    }

    @GetMapping("tags/{id}")
    public Tag readTag(@PathVariable String id)  throws ValidateException, ServiceException {
        return service.readTag(id);
    }

    @PostMapping("/tags")
    public Tag crateTag (@RequestBody Tag tag)  throws ValidateException, ServiceException {
        service.createTag(tag);
        return tag;
    }

    @PutMapping("/tags")
    public Tag updateTag (@RequestBody Tag tag)  throws ValidateException, ServiceException {
        service.updateTag(tag);
        return tag;
    }
}
