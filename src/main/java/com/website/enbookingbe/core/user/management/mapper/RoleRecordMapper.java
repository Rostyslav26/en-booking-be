package com.website.enbookingbe.core.user.management.mapper;

import com.website.enbookingbe.core.user.management.domain.Role;
import org.jooq.Record;
import org.jooq.RecordMapper;

import static com.website.enbookingbe.data.jooq.tables.Role.*;

public class RoleRecordMapper implements RecordMapper<Record, Role> {

    @Override
    public Role map(Record record) {
        return Role.valueOf(record.get(ROLE.ID));
    }
}
