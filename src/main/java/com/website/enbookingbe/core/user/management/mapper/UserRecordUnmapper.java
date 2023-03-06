package com.website.enbookingbe.core.user.management.mapper;

import com.website.enbookingbe.core.user.management.domain.User;
import com.website.enbookingbe.data.jooq.tables.records.UserRecord;
import org.jooq.RecordUnmapper;
import org.jooq.exception.MappingException;

public class UserRecordUnmapper implements RecordUnmapper<User, UserRecord> {

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
