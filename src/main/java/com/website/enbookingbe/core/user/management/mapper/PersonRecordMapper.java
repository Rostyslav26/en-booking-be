package com.website.enbookingbe.core.user.management.mapper;

import com.website.enbookingbe.core.user.management.model.Person;
import org.jooq.Record;
import org.jooq.RecordMapper;

import static com.website.enbookingbe.data.jooq.tables.User.USER;

public class PersonRecordMapper implements RecordMapper<Record, Person> {

    @Override
    public Person map(Record record) {
        final Person person = new Person();

        person.setUserId(record.get(USER.ID));
        person.setFirstName(record.get(USER.FIRST_NAME));
        person.setLastName(record.get(USER.LAST_NAME));
        person.setImageUrl(record.get(USER.IMAGE_URL));

        return person;
    }
}
