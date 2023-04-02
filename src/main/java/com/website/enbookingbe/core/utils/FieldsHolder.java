package com.website.enbookingbe.core.utils;

import com.website.enbookingbe.core.user.management.domain.Role;
import com.website.enbookingbe.core.user.management.mapper.RoleRecordMapper;
import org.jooq.Field;

import java.util.Set;

import static com.website.enbookingbe.data.jooq.Tables.ROLE;
import static com.website.enbookingbe.data.jooq.tables.User.USER;
import static com.website.enbookingbe.data.jooq.tables.UserRole.USER_ROLE;
import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.select;

public class FieldsHolder {
    private static final RoleRecordMapper roleMapper = new RoleRecordMapper();

    public static final Field<Set<Role>> USER_ROLES;

    static {
        USER_ROLES = getUserRolesField();
    }

    private FieldsHolder() {
    }

    private static Field<Set<Role>> getUserRolesField() {
        return multiset(
            select(USER_ROLE.ROLE_ID.as(ROLE.ID))
                .from(USER_ROLE)
                .where(USER_ROLE.USER_ID.eq(USER.ID)))
            .as("roles")
            .convertFrom(f -> f.intoSet(roleMapper));
    }
}
