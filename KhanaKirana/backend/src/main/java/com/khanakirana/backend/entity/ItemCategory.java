package com.khanakirana.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by vavasthi on 25/9/15.
 */
@Entity
public class ItemCategory {
    public ItemCategory(Long parentId, String name, String description) {
        this.parentId = parentId;
        this.name = name;
        this.description = description;
    }

    public ItemCategory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ItemCategory{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Id
    private Long id;
    @Index
    private Long parentId;
    @Index
    private String name;
    private String description;
}
