package com.website.enbookingbe.core.repository;

import org.jooq.Record;
import org.jooq.TableField;

import java.util.Collections;
import java.util.Set;

public interface UpdatableRepository <E, R extends Record> {
    E update(E entity, Set<TableField<R, ?>> fields);

    default E update(E entity) {
        return update(entity, Collections.emptySet());
    }
}
