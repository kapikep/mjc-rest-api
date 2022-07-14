package com.epam.esm.dto;

import com.epam.esm.service.validator.groups.OnCreate;
import com.epam.esm.service.validator.groups.OnUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "tags")
public class TagDto extends RepresentationModel<TagDto> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Positive(groups = OnUpdate.class)
    private long id;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    @Size(groups = {OnCreate.class, OnUpdate.class}, max = 25)
    private String name;
}


