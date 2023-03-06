package com.website.enbookingbe.core.repository;

import org.jooq.Record;
import org.jooq.TableField;

public interface UpdatableRepository <E, R extends Record> {
    void update(E entity, TableField<R, ?> ... fields);
}
