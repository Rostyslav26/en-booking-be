package com.website.enbookingbe.management.repository;

import com.website.enbookingbe.data.jooq.tables.records.UserRecord;
import com.website.enbookingbe.management.domain.User;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.website.enbookingbe.data.jooq.tables.Role.ROLE;
import static com.website.enbookingbe.data.jooq.tables.User.USER;
import static com.website.enbookingbe.data.jooq.tables.UserRole.USER_ROLE;
import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class UserRepository {
    private final DSLContext dsl;

    public Optional<User> findByEmail(String email) {
        final UserRecord userRecord = dsl.select(USER.fields())
            .from(USER)
            .where(USER.EMAIL.eq(email))
            .fetchOneInto(USER);

        if (isNull(userRecord)) {
            return Optional.empty();
        }

        final List<String> roleIds = dsl.select(ROLE.ID)
            .from(ROLE)
            .join(USER).on(USER.ID.eq(USER_ROLE.USER_ID))
            .join(USER_ROLE).on(USER_ROLE.ROLE_ID.eq(ROLE.ID))
            .where(USER.ID.eq(userRecord.getId()))
            .fetchInto(String.class);

    }

    public User save(User user) {
        // TODO: implement this method
        return user;
    }

    public User update(User user, Field<?>... fields) {
        //todo: implement this method
        // add last modified date by default
        return user;
    }

    public boolean existsByEmail(String email) {
        // TODO: implement this method
        return false;
    }

    public Optional<User> findByActivationKey(String activationKey) {
        // TODO: implement this method
        return null;
    }
}
