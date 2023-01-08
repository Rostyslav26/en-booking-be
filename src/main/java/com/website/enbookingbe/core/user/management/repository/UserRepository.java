package com.website.enbookingbe.core.user.management.repository;

import com.website.enbookingbe.core.user.management.domain.Role;
import com.website.enbookingbe.core.user.management.domain.User;
import com.website.enbookingbe.core.user.management.mapper.RoleRecordMapper;
import com.website.enbookingbe.core.user.management.mapper.UserRecordMapper;
import com.website.enbookingbe.data.jooq.tables.records.UserRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static com.website.enbookingbe.data.jooq.Tables.ROLE;
import static com.website.enbookingbe.data.jooq.tables.User.USER;
import static com.website.enbookingbe.data.jooq.tables.UserRole.USER_ROLE;
import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.select;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final DSLContext dsl;
    private final RoleRecordMapper roleMapper = new RoleRecordMapper();
    private final UserRecordMapper userMapper = new UserRecordMapper();

    public boolean existsByEmail(String email) {
        return dsl.fetchExists(USER, USER.EMAIL.eq(email));
    }

    public Optional<User> findByActivationKey(String activationKey) {
        return findBy(USER.ACTIVATION_KEY, activationKey)
            .fetchOptional(userMapper);
    }

    public Optional<User> findByEmail(String email) {
        return findBy(USER.EMAIL, email)
            .fetchOptional(userMapper);
    }

    public void save(User user) {
        user.setCreatedDate(LocalDateTime.now());
        user.setLastModifiedDate(LocalDateTime.now());

        dsl.insertInto(USER)
            .set(USER.EMAIL, user.getEmail())
            .set(USER.PASSWORD, user.getPassword())
            .set(USER.FIRST_NAME, user.getFirstName())
            .set(USER.LAST_NAME, user.getLastName())
            .set(USER.IMAGE_URL, user.getImageUrl())
            .set(USER.ACTIVATION_KEY, user.getActivationKey())
            .set(USER.ACTIVATED, user.getActivated())
            .set(USER.LANG_KEY, user.getLangKey())
            .set(USER.CREATED_BY, user.getCreatedBy())
            .set(USER.CREATED_DATE, user.getCreatedDate())
            .set(USER.LAST_MODIFIED_BY, user.getLastModifiedBy())
            .set(USER.LAST_MODIFIED_DATE, user.getLastModifiedDate())
            .set(USER.RESET_DATE, user.getResetDate())
            .set(USER.RESET_KEY, user.getResetKey())
            .returning()
            .fetchOptional()
            .ifPresent(record -> {
                user.setId(record.getId());
            });
    }

    private <T> SelectConditionStep<Record2<UserRecord, Set<Role>>> findBy(TableField<UserRecord, T> field, T value) {
        return dsl.select(
                USER,
                getRolesSelect())
            .from(USER)
            .where(field.eq(value));
    }

    private Field<Set<Role>> getRolesSelect() {
        return multiset(
            select(USER_ROLE.ROLE_ID.as(ROLE.ID))
                .from(USER_ROLE)
                .where(USER_ROLE.USER_ID.eq(USER.ID)))
            .as("roles")
            .convertFrom(f -> f.intoSet(roleMapper));
    }

    public void update(User user) {
        user.setLastModifiedDate(LocalDateTime.now());

        dsl.update(USER)
            .set(USER.FIRST_NAME, user.getFirstName())
            .set(USER.LAST_NAME, user.getLastName())
            .set(USER.IMAGE_URL, user.getImageUrl())
            .set(USER.ACTIVATION_KEY, user.getActivationKey())
            .set(USER.ACTIVATED, user.getActivated())
            .set(USER.LANG_KEY, user.getLangKey())
            .set(USER.LAST_MODIFIED_BY, user.getLastModifiedBy())
            .set(USER.LAST_MODIFIED_DATE, user.getLastModifiedDate())
            .set(USER.RESET_DATE, user.getResetDate())
            .set(USER.RESET_KEY, user.getResetKey())
            .where(USER.ID.eq(user.getId()))
            .execute();
    }
}
