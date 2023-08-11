package com.website.enbookingbe.management.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public abstract class AbstractAuditingEntity {
    private String createdBy;
    private Instant createdDate = Instant.now();
    private String lastModifiedBy;
    private Instant lastModifiedDate = Instant.now();
}
