package com.sanjnan.server.sanjnan.entity;

import com.sanjnan.server.config.mapper.annotations.SkipPatching;
import com.sanjnan.server.entity.PatchableEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by vinay on 1/28/16.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity  extends PatchableEntity  {
  public BaseEntity(String name) {
    this.name = name;
  }

  public BaseEntity() {
  }

  @SkipPatching
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public DateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(DateTime createdAt) {
    this.createdAt = createdAt;
  }

  public DateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(DateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "BaseEntity{" +
        "id=" + id +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", createdBy='" + createdBy + '\'' +
        ", updatedBy='" + updatedBy + '\'' +
        ", name='" + name + '\'' +
        '}';
  }

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;
  @CreatedDate
  @Column(columnDefinition = "DATETIME")
  @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
  private DateTime createdAt;
  @LastModifiedDate
  @Column(columnDefinition = "DATETIME")
  @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
  private DateTime updatedAt;
  @CreatedBy
  private String createdBy;
  @LastModifiedBy
  private String updatedBy;
  private String name;
}
