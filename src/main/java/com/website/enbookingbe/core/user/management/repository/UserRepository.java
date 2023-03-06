package com.website.enbookingbe.core.user.management.repository;

import com.website.enbookingbe.core.repository.UpdatableRepository;
import com.website.enbookingbe.core.user.management.domain.Role;
import com.website.enbookingbe.core.user.management.domain.User;
import com.website.enbookingbe.core.user.management.mapper.RoleRecordMapper;
import com.website.enbookingbe.core.user.management.mapper.UserRecordMapper;
import com.website.enbookingbe.core.user.management.mapper.UserRecordUnmapper;
import com.website.enbookingbe.core.user.management.model.UserInfo;
import com.website.enbookingbe.data.jooq.tables.records.UserRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.website.enbookingbe.data.jooq.Tables.ROLE;
import static com.website.enbookingbe.data.jooq.tables.User.USER;
import static com.website.enbookingbe.data.jooq.tables.UserRole.USER_ROLE;
import static org.apache.commons.lang3.ArrayUtils.isEmpty;
import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.select;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UpdatableRepository<User, UserRecord> {
    private final DSLContext dsl;

    private final RoleRecordMapper roleMapper = new RoleRecordMapper();
    private final UserRecordMapper userMapper = new UserRecordMapper();
    private final UserRecordUnmapper userUnmapper = new UserRecordUnmapper();

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

    @SafeVarargs
    public final void update(User user, TableField<UserRecord, ?>... fields) {
        user.setLastModifiedDate(LocalDateTime.now());
        final UserRecord record = dsl.newRecord(USER, userUnmapper.unmap(user));

        if (isEmpty(fields)) {
            record.update();
        } else {
            final Set<Field<?>> data = new HashSet<>(Arrays.asList(fields));
            data.add(USER.LAST_MODIFIED_DATE);
            record.update(data);
        }
    }

    public void save(User user) {
        final UserRecord record = dsl.newRecord(USER, userUnmapper.unmap(user));
        record.store();
        user.setId(record.getId());
    }

    public Optional<UserInfo> getUserInfoByEmail(String email) {
        return dsl.select(USER.ID, USER.EMAIL, USER.FIRST_NAME, USER.LAST_NAME, USER.IMAGE_URL)
            .from(USER)
            .where(USER.EMAIL.eq(email))
            .fetchOptional(r -> {
                final UserInfo userInfo = new UserInfo();
                userInfo.setUserId(r.get(USER.ID));
                userInfo.setEmail(r.get(USER.EMAIL));
                userInfo.setFirstName(r.get(USER.FIRST_NAME));
                userInfo.setLastName(r.get(USER.LAST_NAME));
                userInfo.setImageUrl(r.get(USER.IMAGE_URL));

                return userInfo;
            });
    }

    private <T> SelectConditionStep<Record2<UserRecord, Set<Role>>> findBy(TableField<UserRecord, T> field, T value) {
        return dsl.select(USER, getRolesSelect())
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
}
