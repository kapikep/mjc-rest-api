package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = "order")
@Entity
@Table(name = "order_item")
public class OrderItemEntity  extends AuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
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
