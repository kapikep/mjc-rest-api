package com.epam.esm.dto;

import com.epam.esm.service.validator.groups.OnCreate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
    @Min(value = 0)
    private long id;
    @NotBlank
    @Size(max = 45)
    private String name;
    @NotBlank
    @Size(max = 300)
    private String description;
//    @NotNull(groups = OnCreate.class)
    @Min(value = 1)
    private Double price;
    @Min(value = 1)
    private Integer duration;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate;
    List<@Valid TagDto> tags;
}

