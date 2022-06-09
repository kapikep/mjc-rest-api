package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Objects;
/**
 * Tag entity object
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
}
