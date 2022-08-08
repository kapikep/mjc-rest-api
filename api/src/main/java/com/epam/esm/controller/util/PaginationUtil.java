package com.epam.esm.controller.util;

import com.epam.esm.controller.impl.GiftCertificateRestController;
import com.epam.esm.controller.impl.TagRestController;
import com.epam.esm.controller.impl.UserRestController;
import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class PaginationUtil {
    public static void addPaginationLinks(PagedModel<?> pm) {
        long page = pm.getMetadata().getNumber();
        long totalPages = pm.getMetadata().getTotalPages();

        pm.add(Link.of(ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam("page", "1")
                .build().toUriString(), LinkRelation.of("first")));

        if (page > 1) {
            pm.add(Link.of(ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam("page", page - 1)
                    .build().toUriString(), LinkRelation.of("prev")));
        }

        pm.add(Link.of(ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam("page", page)
                .build().toUriString(), LinkRelation.of("self")));

        if (page < totalPages) {
            pm.add(Link.of(ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam("page", page + 1)
                    .build().toUriString(), LinkRelation.of("next")));
        }

        pm.add(Link.of(ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam("page", totalPages)
                .build().toUriString(), LinkRelation.of("last")));
    }

    public static <T> PagedModel<T> createPagedModel(Collection<T> col, CriteriaDto cr) {
        return PagedModel.of(col, new PagedModel.PageMetadata(cr.getSize(), cr.getPage(), cr.getTotalSize()));
    }

    public static Link getSelfLink(Class<?> controller, long id) {
        return linkTo(controller).slash(id).withSelfRel();
    }

    public static Link getUserOrderLink(long userId, long orderId) {
        return linkTo(UserRestController.class).slash(userId).slash("orders").withSelfRel();
    }

    public static void addGiftCertificateLink(GiftCertificateDto gift) {
        gift.add(getSelfLink(GiftCertificateRestController.class, gift.getId()));
        if (!gift.getTags().isEmpty()) {
            for (TagDto tag : gift.getTags()) {
                tag.add(linkTo(TagRestController.class).slash(tag.getId()).withRel("tag"));
            }
        }
    }
}
