package com.website.enbookingbe.management.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.website.enbookingbe.DBTest;
import com.website.enbookingbe.management.domain.Role;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.website.enbookingbe.data.jooq.Tables.ROLE;
import static com.website.enbookingbe.data.jooq.Tables.USER_ROLE;
import static org.assertj.core.api.Assertions.assertThat;

@DBTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DSLContext dslContext;

    @Test
    @DataSet(value = {"datasets/user/users.xml", "datasets/role/roles.xml", "datasets/user-role/user-roles.xml"})
    void testSave() {
        final int userId = 1;
        roleRepository.save(userId, List.of(new Role("WRITER")));

        final List<String> roleIds = dslContext.select(ROLE.ID)
            .from(ROLE)
            .join(USER_ROLE).on(USER_ROLE.ROLE_ID.eq(ROLE.ID))
            .where(USER_ROLE.USER_ID.eq(userId))
            .fetchInto(String.class);

        assertThat(roleIds).containsExactly("WRITER");
    }
}