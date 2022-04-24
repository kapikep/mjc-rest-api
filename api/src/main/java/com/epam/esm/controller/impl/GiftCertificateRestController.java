package com.epam.esm.controller.impl;

import com.epam.esm.controller.interf.GiftCertificateController;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.interf.GiftCertificateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class GiftCertificateRestController implements GiftCertificateController {
    private final GiftCertificateService service;

    public GiftCertificateRestController(GiftCertificateService service) {
        this.service = service;
    }

    @GetMapping("/gifts")
    public List<GiftCertificate> readAll() {
        List<GiftCertificate>giftCertificates = service.readAll();
        return giftCertificates;
    }

    @GetMapping("/gift")
    public List<GiftCertificate> read() {
        List<GiftCertificate>giftCertificates1 = new ArrayList<>();
        giftCertificates1.add(new GiftCertificate(1, "sdds", "sds", 2.0, 2, LocalDateTime.now(), LocalDateTime.now()));
        giftCertificates1.add(new GiftCertificate());
        return giftCertificates1;
    }

    @GetMapping ("/tag")
    public Tag readTag(){
        Tag tag = new Tag();
        tag.setName("first");
        System.out.println(tag);
        return tag;
    }
}
