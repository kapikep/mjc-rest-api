package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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
public class GiftCertificateDto {
    @Min(value = 0)
    private int id;
    @NotBlank
    @Size(max = 45)
    private String name;
    @NotBlank
    @Size(max = 45)
    private String description;
    @Min(value = 0)
    private Double price;
    @Min(value = 0)
    private Integer duration;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate;
    List<TagDto> tags;
}
