package com.epam.esm.entity;

import java.io.Serializable;
import java.util.Objects;
/**
 * Tag entity object
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public class TagEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    int id;
    private String name;

    public TagEntity() {
    }

    public TagEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public TagEntity(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagEntity tag = (TagEntity) o;
        return id == tag.id && Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
