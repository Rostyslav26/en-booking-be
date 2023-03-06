package com.website.enbookingbe.core.user.management.mapper;

import com.website.enbookingbe.core.user.management.domain.Role;
import com.website.enbookingbe.core.user.management.domain.User;
import com.website.enbookingbe.data.jooq.tables.records.UserRecord;
import org.jooq.Record2;
import org.jooq.RecordMapper;

import java.util.Set;

import static com.website.enbookingbe.data.jooq.tables.User.USER;

public class UserRecordMapper implements RecordMapper<Record2<UserRecord, Set<Role>>, User> {

    @Override
    public User map(Record2<UserRecord, Set<Role>> record) {
        UserRecord userRecord = record.value1();
        Set<Role> roles = record.value2();

        final User user = new User();
        user.setId(userRecord.get(USER.ID));
        user.setEmail(userRecord.get(USER.EMAIL));
        user.setPassword(userRecord.get(USER.PASSWORD));
        user.setFirstName(userRecord.get(USER.FIRST_NAME));
        user.setLastName(userRecord.get(USER.LAST_NAME));
        user.setActivated(userRecord.get(USER.ACTIVATED));
        user.setActivationKey(userRecord.get(USER.ACTIVATION_KEY));
        user.setImageUrl(userRecord.get(USER.IMAGE_URL));
        user.setRoles(roles);
        user.setResetKey(userRecord.get(USER.RESET_KEY));
        user.setResetDate(userRecord.get(USER.RESET_DATE));
        user.setCreatedBy(userRecord.get(USER.CREATED_BY));
        user.setCreatedDate(userRecord.get(USER.CREATED_DATE));
        user.setLastModifiedBy(userRecord.get(USER.LAST_MODIFIED_BY));
        user.setLastModifiedDate(userRecord.get(USER.LAST_MODIFIED_DATE));
        user.setLangKey(userRecord.get(USER.LANG_KEY));

        return user;
    }
}
