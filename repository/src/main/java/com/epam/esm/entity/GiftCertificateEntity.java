package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * Gift certificate entity object
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private List<TagEntity> tags = new ArrayList<>();

    public void addTag(TagEntity tag) {
        this.tags.add(tag);
    }
}
