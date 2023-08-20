package com.website.enbookingbe.utils;

import com.website.enbookingbe.management.domain.Role;
import com.website.enbookingbe.management.repository.mapper.RoleRecordMapper;
import org.jooq.Field;
import org.jooq.Record;

import javax.annotation.Nullable;
import java.util.List;

import static com.website.enbookingbe.data.jooq.Tables.USERS;
import static com.website.enbookingbe.data.jooq.tables.Role.ROLE;
import static com.website.enbookingbe.data.jooq.tables.UserRole.USER_ROLE;
import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.select;

public class DBUtils {
    public static final Field<List<Role>> ROLES_FIELD = getRolesField();
    public static final List<Field<?>> USER_FIELDS = getUserFields();

    private static final RoleRecordMapper roleMapper = new RoleRecordMapper();

    private DBUtils() {
    }

    @Nullable
    public static <T> T getValue(Record record, Field<T> field) {
        return hasField(record, field) ? record.get(field) : null;
    }

    public static boolean hasField(Record record, Field<?> field) {
        return record.indexOf(field) >= 0;
    }

    private static List<Field<?>> getUserFields() {
        return List.of(
            USERS.ID,
            USERS.FIRST_NAME,
            USERS.LAST_NAME,
            USERS.EMAIL,
            USERS.PASSWORD,
            USERS.IMAGE_URL,
            USERS.ACTIVATED,
            USERS.ACTIVATION_KEY,
            USERS.RESET_KEY,
            USERS.RESET_DATE,
            USERS.LANG_KEY,
            USERS.CREATED_BY,
            USERS.CREATED_DATE,
            USERS.LAST_MODIFIED_BY,
            USERS.LAST_MODIFIED_DATE,
            ROLES_FIELD
        );
    }

    private static Field<List<Role>> getRolesField() {
        return multiset(select(ROLE.fields())
            .from(ROLE)
            .join(USER_ROLE).on(ROLE.ID.eq(USER_ROLE.ROLE_ID))
            .where(ROLE.ID.eq(USER_ROLE.ROLE_ID)))
            .as("roles").convertFrom(r -> r.map(roleMapper));
    }
}
