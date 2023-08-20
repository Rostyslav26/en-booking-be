package com.website.enbookingbe.management.repository;

import com.website.enbookingbe.data.jooq.tables.records.UsersRecord;
import com.website.enbookingbe.management.domain.User;
import com.website.enbookingbe.management.repository.mapper.UserRecordMapper;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.website.enbookingbe.data.jooq.Tables.USERS;
import static com.website.enbookingbe.utils.DBUtils.USER_FIELDS;

@Component
@RequiredArgsConstructor
public class UserRepository {
    private final UserRecordMapper mapper = new UserRecordMapper();
    private final DSLContext dsl;

    public User save(User user) {
        return dsl.insertInto(USERS)
            .set(USERS.EMAIL, user.getEmail())
            .set(USERS.FIRST_NAME, user.getFirstName())
            .set(USERS.LAST_NAME, user.getLastName())
            .set(USERS.PASSWORD, user.getPassword())
            .set(USERS.IMAGE_URL, user.getImageUrl())
            .set(USERS.ACTIVATED, user.getActivated())
            .set(USERS.ACTIVATION_KEY, user.getActivationKey())
            .set(USERS.RESET_KEY, user.getResetKey())
            .set(USERS.RESET_DATE, user.getResetDate())
            .set(USERS.LANG_KEY, user.getLangKey())
            .set(USERS.CREATED_BY, user.getCreatedBy())
            // need to do with default values
            .returning(USERS.asterisk())
            .fetchOne(mapper);
    }

    public User update(User user, Field<?>... fields) {
        final UsersRecord userRecord = dsl.newRecord(USERS, mapper.unmap(user));
        userRecord.setLastModifiedDate(LocalDateTime.now());
        userRecord.update(fields);

        return mapper.map(userRecord);
    }

    public boolean existsByEmail(String email) {
        return dsl.fetchExists(dsl.selectFrom(USERS).where(USERS.EMAIL.eq(email)));
    }

    public Optional<User> findByEmail(String email) {
        return dsl.select(USER_FIELDS)
            .from(USERS)
            .where(USERS.EMAIL.eq(email))
            .fetchOptional(mapper);
    }

    public Optional<User> findByActivationKey(String activationKey) {
        return dsl.select(USER_FIELDS)
            .from(USERS)
            .where(USERS.ACTIVATION_KEY.eq(activationKey))
            .fetchOptional(mapper);
    }
}
