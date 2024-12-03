package com.fms.fund_management_system.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fms.fund_management_system.entities.BaseEntity;
import com.fms.fund_management_system.entities.User;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class BaseModel {
    public static final String DATE_TIME_FORMATTER = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMATTER)
    protected OffsetDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMATTER)
    protected OffsetDateTime updatedAt;
    @JsonIgnore
    protected User createdBy;
    @JsonIgnore
    protected User updatedBy;

    public BaseModel(BaseEntity baseEntity){
        this.createdAt = baseEntity.getCreatedAt();
        this.updatedAt = baseEntity.getUpdatedAt();
        this.createdBy = baseEntity.getCreatedBy() != null ? baseEntity.getCreatedBy() : null;
        this.updatedBy = baseEntity.getUpdatedBy() != null ? baseEntity.getUpdatedBy() : null;

    }
}
