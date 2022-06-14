package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(value = 0)
    private int id;

    @NotBlank
    @Size(max = 3)
    private String name;
}


