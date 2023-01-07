package com.website.enbookingbe.core.utils;

import com.website.enbookingbe.core.user.management.mapper.PersonRecordMapper;
import com.website.enbookingbe.core.user.management.model.Person;
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

    public static SelectField<Person> getPersonSelectRow() {
        return getPersonSelectRow(null);
    }

    public static SelectField<Person> getPersonSelectRow(@Nullable User userByAlias) {
        final User user = isNull(userByAlias) ? User.USER : userByAlias;

        return row(getPersonFields(user))
            .as("person")
            .convertFrom(PERSON_RECORD_MAPPER);
    }

    public static List<? extends TableField<UserRecord, ? extends Serializable>> getPersonFields() {
        return getPersonFields(null);
    }

    public static List<? extends TableField<UserRecord, ? extends Serializable>> getPersonFields(@Nullable User userByAlias) {
        final User user = isNull(userByAlias) ? User.USER : userByAlias;

        return List.of(user.ID, user.FIRST_NAME, user.LAST_NAME, user.EMAIL, user.IMAGE_URL, user.ACTIVATED);
    }
}
