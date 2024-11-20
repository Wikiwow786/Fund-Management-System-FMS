package com.fms.fund_management_system.models;

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
    protected OffsetDateTime createdAt;
    protected OffsetDateTime updatedAt;
    protected User createdBy;
    protected User updatedBy;

    public BaseModel(BaseEntity baseEntity){
        this.createdAt = baseEntity.getCreatedAt();
        this.updatedAt = baseEntity.getUpdatedAt();
        this.createdBy = baseEntity.getCreatedBy();
        this.createdBy = baseEntity.getCreatedBy() != null ? baseEntity.getCreatedBy() : null;
        this.updatedBy = baseEntity.getUpdatedBy() != null ? baseEntity.getUpdatedBy() : null;

    }
}
