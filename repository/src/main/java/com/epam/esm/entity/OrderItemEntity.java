package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString(exclude = "order")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "gift_certificate_id",
            referencedColumnName = "id", nullable = false)
    private GiftCertificateEntity giftCertificate;

    @ManyToOne
    @JoinColumn(name = "order_id",
            referencedColumnName = "id", nullable = false)
    private OrderForGiftCertificateEntity order;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
