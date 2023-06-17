/*
 * This file is generated by jOOQ.
 */
package com.website.enbookingbe.data.jooq;


import com.website.enbookingbe.data.jooq.tables.User;
import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables in public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index USER_ACTIVATION_KEY_IDX = Internal.createIndex(DSL.name("user_activation_key_idx"), User.USER, new OrderField[] { User.USER.ACTIVATION_KEY }, false);
    public static final Index USER_EMAIL_IDX = Internal.createIndex(DSL.name("user_email_idx"), User.USER, new OrderField[] { User.USER.EMAIL }, true);
}
