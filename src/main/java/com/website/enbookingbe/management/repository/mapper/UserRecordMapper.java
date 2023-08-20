package com.website.enbookingbe.management.repository.mapper;

import com.website.enbookingbe.data.jooq.tables.records.UsersRecord;
import com.website.enbookingbe.management.domain.User;
import com.website.enbookingbe.utils.DBUtils;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordUnmapper;
import org.jooq.exception.MappingException;

import javax.annotation.Nonnull;

import static com.website.enbookingbe.data.jooq.Tables.USERS;
import static com.website.enbookingbe.utils.DBUtils.ROLES_FIELD;

public class UserRecordMapper implements RecordMapper<Record, User>, RecordUnmapper<User, UsersRecord> {

    @Override
    @Nonnull
    public User map(Record record) {
        User user = new User();

        user.setId(record.get(USERS.ID));
        user.setEmail(record.get(USERS.EMAIL));
        user.setFirstName(record.get(USERS.FIRST_NAME));
        user.setLastName(record.get(USERS.LAST_NAME));
        user.setActivated(record.get(USERS.ACTIVATED));
        user.setImageUrl(record.get(USERS.IMAGE_URL));
        user.setActivationKey(record.get(USERS.ACTIVATION_KEY));
        user.setLastModifiedBy(record.get(USERS.LAST_MODIFIED_BY));
        user.setLastModifiedDate(record.get(USERS.LAST_MODIFIED_DATE));
        user.setResetDate(record.get(USERS.RESET_DATE));
        user.setResetKey(record.get(USERS.RESET_KEY));
        user.setCreatedBy(record.get(USERS.CREATED_BY));
        user.setCreatedDate(record.get(USERS.CREATED_DATE));
        user.setLangKey(record.get(USERS.LANG_KEY));
        user.setPassword(record.get(USERS.PASSWORD));
        user.setRoles(DBUtils.getValue(record, ROLES_FIELD));

        return user;
    }

    @Override
    @Nonnull
    public UsersRecord unmap(User source) throws MappingException {
        UsersRecord userRecord = new UsersRecord();

        userRecord.setId(source.getId());
        userRecord.setEmail(source.getEmail());
        userRecord.setFirstName(source.getFirstName());
        userRecord.setLastName(source.getLastName());
        userRecord.setActivated(source.getActivated());
        userRecord.setActivationKey(source.getActivationKey());
        userRecord.setLastModifiedBy(source.getLastModifiedBy());
        userRecord.setImageUrl(source.getImageUrl());
        userRecord.setResetDate(source.getResetDate());
        userRecord.setResetKey(source.getResetKey());
        userRecord.setCreatedBy(source.getCreatedBy());
        userRecord.setCreatedDate(source.getCreatedDate());
        userRecord.setLangKey(source.getLangKey());
        userRecord.setPassword(source.getPassword());

        return userRecord;
    }
}
