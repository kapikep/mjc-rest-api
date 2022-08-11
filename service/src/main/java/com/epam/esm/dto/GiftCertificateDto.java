package com.epam.esm.dto;

import com.epam.esm.service.validator.groups.OnCreate;
import com.epam.esm.service.validator.groups.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Gift certificate data transfer object
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "gift certificates")
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {
    @Positive(groups = OnUpdate.class)
    private long id;

    @NotBlank(groups = OnCreate.class)
    @Size(groups = {OnCreate.class, OnUpdate.class}, max = 45)
    private String name;

    @NotBlank(groups = OnCreate.class)
    @Size(groups = {OnCreate.class, OnUpdate.class}, max = 300)
    private String description;

    @NotNull(groups = OnCreate.class)
    @Min(groups = {OnCreate.class, OnUpdate.class}, value = 1)
    private Double price;

    @NotNull(groups = OnCreate.class)
    @Positive(groups = {OnCreate.class, OnUpdate.class})
    private Integer duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate;

    List<TagDto> tags;
}

