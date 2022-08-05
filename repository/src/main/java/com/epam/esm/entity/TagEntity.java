package com.epam.esm.entity;

import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Tag entity object
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "giftCertificates")
@EqualsAndHashCode(exclude = "giftCertificates", callSuper = false)
@Entity
@Table(name = "tag")
public class TagEntity extends AuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<GiftCertificateEntity> giftCertificates = new ArrayList<>();

    public TagEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
