package com.website.enbookingbe.management.repository;

import com.website.enbookingbe.data.jooq.tables.records.UserRoleRecord;
import com.website.enbookingbe.management.domain.Role;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.website.enbookingbe.data.jooq.tables.UserRole.USER_ROLE;

@Component
@RequiredArgsConstructor
public class RoleRepository {
    private final DSLContext dsl;

    public void save(int userId, List<Role> roles) {
        dsl.deleteFrom(USER_ROLE)
            .where(USER_ROLE.USER_ID.eq(userId))
            .execute();

        List<UserRoleRecord> records = roles.stream()
            .map(Role::getId)
            .map(role -> new UserRoleRecord(userId, role))
            .toList();

        dsl.batchInsert(records).execute();
    }
}