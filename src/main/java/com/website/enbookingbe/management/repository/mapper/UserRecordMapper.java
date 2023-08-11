package com.website.enbookingbe.management.repository.mapper;

import com.website.enbookingbe.data.jooq.tables.records.UserRecord;
import com.website.enbookingbe.management.domain.User;
import org.jooq.RecordMapper;

import javax.annotation.Nonnull;

public class UserRecordMapper implements RecordMapper<UserRecord, User> {

    @Override
    @Nonnull
    public User map(UserRecord record) {
        final User user = new User();
        // TODO: implement this method
        user.setId(record.getId());
        user.setEmail(record.getEmail());
        user.setFirstName(record.getFirstName());
        user.setLastName(record.getLastName());
        user.setActivated(record.getActivated());
        user.setActivationKey(record.getActivationKey());
        user.setLastModifiedBy(record.getLastModifiedBy());
//        user.setLastModifiedDate(record.getLastModifiedDate().toInstant(ZoneId.systemDefault()));
        user.setLangKey(record.getLangKey());
        user.setPassword(record.getPassword());

        return user;
    }
}
