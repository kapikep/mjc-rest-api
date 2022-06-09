package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDto implements Serializable {
    private static final long serialVersionUID = 1L;

//    @Digits(integer = 3, fraction = 0, message = "Id null")
    @Min(value = 1)
    @Max(value = 3)
//    @Size(
//            min = 1,
//            max = 5,
//            message = "incorrect id"
//    )
    private int id;

    @NotBlank(message = "MESSAGE")
    private String name;
}


