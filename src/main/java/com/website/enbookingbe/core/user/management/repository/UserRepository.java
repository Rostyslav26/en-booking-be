package com.website.enbookingbe.core.user.management.repository;

import com.google.common.collect.ImmutableSet;
import com.website.enbookingbe.core.repository.UpdatableRepository;
import com.website.enbookingbe.core.user.management.domain.User;
import com.website.enbookingbe.core.user.management.mapper.UserRecordMapper;
import com.website.enbookingbe.core.user.management.model.UserInfo;
import com.website.enbookingbe.core.utils.FieldsHolder;
import com.website.enbookingbe.data.jooq.tables.records.UserRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.Record;
import org.jooq.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static com.website.enbookingbe.data.jooq.tables.User.USER;
import static org.springframework.util.CollectionUtils.isEmpty;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UpdatableRepository<User, UserRecord> {
    private final DSLContext dsl;

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

    public User update(User user, Set<TableField<UserRecord, ?>> fields) {
        user.setLastModifiedDate(LocalDateTime.now());
        final UserRecord record = dsl.newRecord(USER, userMapper.unmap(user));

        if (isEmpty(fields)) {
            record.update();
        } else {
            final Set<Field<?>> data = ImmutableSet.<Field<?>>builder()
                .addAll(fields)
                .add(USER.LAST_MODIFIED_DATE)
                .build();

            record.update(data);
        }

        return user;
    }

    public void save(User user) {
        final UserRecord record = dsl.newRecord(USER, userMapper.unmap(user));
        record.store();
        user.setId(record.getId());
    }

    public Optional<UserInfo> getUserInfoById(Integer id) {
        return dsl.select(USER.ID, USER.EMAIL, USER.FIRST_NAME, USER.LAST_NAME, USER.IMAGE_URL)
            .from(USER)
            .where(USER.ID.eq(id))
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

    private <T> SelectConditionStep<Record> findBy(TableField<UserRecord, T> field, T value) {
        return dsl.select(USER.fields())
            .select(FieldsHolder.USER_ROLES)
            .from(USER)
            .where(field.eq(value));
    }
}
