package com.website.enbookingbe.core.user.management.mapper;

import com.website.enbookingbe.core.user.management.domain.Role;
import com.website.enbookingbe.core.user.management.domain.User;
import com.website.enbookingbe.core.utils.FieldsHolder;
import com.website.enbookingbe.data.jooq.tables.records.UserRecord;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordUnmapper;
import org.jooq.exception.MappingException;

import java.util.Set;

import static com.website.enbookingbe.data.jooq.tables.User.USER;

public class UserRecordMapper implements RecordMapper<Record, User>, RecordUnmapper<User, UserRecord> {

    @Override
    public User map(Record record) {
        Set<Role> roles = record.get(FieldsHolder.USER_ROLES);

        final User user = new User();
        user.setId(record.get(USER.ID));
        user.setEmail(record.get(USER.EMAIL));
        user.setPassword(record.get(USER.PASSWORD));
        user.setFirstName(record.get(USER.FIRST_NAME));
        user.setLastName(record.get(USER.LAST_NAME));
        user.setActivated(record.get(USER.ACTIVATED));
        user.setActivationKey(record.get(USER.ACTIVATION_KEY));
        user.setImageUrl(record.get(USER.IMAGE_URL));
        user.setRoles(roles);
        user.setResetKey(record.get(USER.RESET_KEY));
        user.setResetDate(record.get(USER.RESET_DATE));
        user.setCreatedBy(record.get(USER.CREATED_BY));
        user.setCreatedDate(record.get(USER.CREATED_DATE));
        user.setLastModifiedBy(record.get(USER.LAST_MODIFIED_BY));
        user.setLastModifiedDate(record.get(USER.LAST_MODIFIED_DATE));
        user.setLangKey(record.get(USER.LANG_KEY));

        return user;
    }

    @Override
    public UserRecord unmap(User source) throws MappingException {
        final UserRecord target = new UserRecord();
        target.setId(source.getId());
        target.setEmail(source.getEmail());
        target.setPassword(source.getPassword());
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setActivated(source.getActivated());
        target.setActivationKey(source.getActivationKey());
        target.setImageUrl(source.getImageUrl());
        target.setResetKey(source.getResetKey());
        target.setResetDate(source.getResetDate());
        target.setCreatedBy(source.getCreatedBy());
        target.setCreatedDate(source.getCreatedDate());
        target.setLastModifiedBy(source.getLastModifiedBy());
        target.setLastModifiedDate(source.getLastModifiedDate());
        target.setLangKey(source.getLangKey());

        return target;
    }
}
