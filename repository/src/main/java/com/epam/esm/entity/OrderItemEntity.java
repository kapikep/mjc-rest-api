package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * Order item entity object
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = "order")
@ToString(exclude = "order")
@Entity
@Table(name = "order_item")
public class OrderItemEntity extends AuditingEntity implements Serializable {
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
