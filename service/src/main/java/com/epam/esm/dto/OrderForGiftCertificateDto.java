package com.epam.esm.dto;

import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "users")
public class OrderForGiftCertificateDto extends RepresentationModel<OrderForGiftCertificateDto> {
    private long id;

    private Timestamp orderTime;

    private BigDecimal totalAmount;

    private UserDto user;

    private List<GiftCertificateDto> gifts = new ArrayList<>();
}
