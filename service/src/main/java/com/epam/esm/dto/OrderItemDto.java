package com.epam.esm.dto;

import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.OrderForGiftCertificateEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "order item")
public class OrderItemDto  extends RepresentationModel<OrderItemDto> {
    private long id;

    private GiftCertificateDto giftCertificate;

    @Positive
    private int quantity;
}