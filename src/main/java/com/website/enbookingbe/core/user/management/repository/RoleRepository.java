package com.website.enbookingbe.core.user.management.repository;

import com.website.enbookingbe.core.user.management.domain.Role;
import com.website.enbookingbe.data.jooq.tables.records.UserRoleRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class RoleRepository {
    private final DSLContext dsl;

    public void assignRoles(Set<Role> roles, Integer userId) {
        final List<UserRoleRecord> records = roles.stream()
                .map(role -> new UserRoleRecord(userId, role.getAuthority()))
                .toList();

        dsl.batchInsert(records).execute();
    }
}
