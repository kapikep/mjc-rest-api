package com.epam.esm.entity;

import lombok.*;

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
@ToString(exclude = "giftCertificates")
@EqualsAndHashCode(exclude = "giftCertificates")
//@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tag")
public class TagEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @NonNull
    private int id;
//    @NonNull
    private String name;

    public TagEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private List<GiftCertificateEntity> giftCertificates = new ArrayList<>();
}
