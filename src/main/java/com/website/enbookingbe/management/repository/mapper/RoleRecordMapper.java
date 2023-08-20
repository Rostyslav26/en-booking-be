package com.website.enbookingbe.management.repository.mapper;

import com.website.enbookingbe.management.domain.Role;
import org.jooq.Record;
import org.jooq.RecordMapper;

import static com.website.enbookingbe.data.jooq.tables.Role.ROLE;

public class RoleRecordMapper implements RecordMapper<Record, Role> {

    @Override
    public Role map(Record record) {
        return new Role(record.get(ROLE.ID));
    }
}
