package com.website.enbookingbe.core.user.management.mapper;

import com.website.enbookingbe.core.user.management.model.UserInfo;
import org.jooq.Record;
import org.jooq.RecordMapper;

import static com.website.enbookingbe.data.jooq.tables.User.USER;

public class PersonRecordMapper implements RecordMapper<Record, UserInfo> {

    @Override
    public UserInfo map(Record record) {
        final UserInfo userInfo = new UserInfo();

        userInfo.setUserId(record.get(USER.ID));
        userInfo.setFirstName(record.get(USER.FIRST_NAME));
        userInfo.setLastName(record.get(USER.LAST_NAME));
        userInfo.setImageUrl(record.get(USER.IMAGE_URL));
        userInfo.setEmail(record.get(USER.EMAIL));

        return userInfo;
    }
}
