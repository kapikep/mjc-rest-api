package com.epam.esm.controller.impl;

import com.epam.esm.service.interf.TagService;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private final TagService service;
    private final MessageSource source;

    public UserRestController(TagService service, MessageSource source) {
        this.service = service;
        this.source = source;
    }

    @GetMapping(value = "/{customerId}/orders")
    public void getOrdersByUser(@PathVariable final String customerId){

    }


}
