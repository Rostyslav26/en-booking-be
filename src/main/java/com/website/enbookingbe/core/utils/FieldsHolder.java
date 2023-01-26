package com.website.enbookingbe.core.utils;

import com.website.enbookingbe.core.user.management.mapper.PersonRecordMapper;
import com.website.enbookingbe.core.user.management.model.UserInfo;
import com.website.enbookingbe.data.jooq.tables.User;
import com.website.enbookingbe.data.jooq.tables.records.UserRecord;
import org.jooq.SelectField;
import org.jooq.TableField;

import javax.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

import static java.util.Objects.isNull;
import static org.jooq.impl.DSL.row;

public class FieldsHolder {

    private static final PersonRecordMapper PERSON_RECORD_MAPPER = new PersonRecordMapper();

    FieldsHolder() { }

    public static SelectField<UserInfo> getUserInfoSelectRow() {
        return getUserInfoSelectRow(null);
    }

    public static SelectField<UserInfo> getUserInfoSelectRow(@Nullable User userByAlias) {
        final User user = isNull(userByAlias) ? User.USER : userByAlias;

        return row(getUserInfoFields(user))
            .as("user")
            .convertFrom(PERSON_RECORD_MAPPER);
    }

    public static List<? extends TableField<UserRecord, ? extends Serializable>> getUserInfoFields(@Nullable User userByAlias) {
        final User user = isNull(userByAlias) ? User.USER : userByAlias;

        return List.of(user.ID, user.FIRST_NAME, user.LAST_NAME, user.EMAIL, user.IMAGE_URL, user.ACTIVATED);
    }
}
