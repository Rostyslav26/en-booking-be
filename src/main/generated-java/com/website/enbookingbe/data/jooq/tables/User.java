/*
 * This file is generated by jOOQ.
 */
package com.website.enbookingbe.data.jooq.tables;


import com.website.enbookingbe.data.jooq.Indexes;
import com.website.enbookingbe.data.jooq.Keys;
import com.website.enbookingbe.data.jooq.Public;
import com.website.enbookingbe.data.jooq.tables.records.UserRecord;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class User extends TableImpl<UserRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.user</code>
     */
    public static final User USER = new User();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserRecord> getRecordType() {
        return UserRecord.class;
    }

    /**
     * The column <code>public.user.id</code>.
     */
    public final TableField<UserRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.user.first_name</code>.
     */
    public final TableField<UserRecord, String> FIRST_NAME = createField(DSL.name("first_name"), SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>public.user.last_name</code>.
     */
    public final TableField<UserRecord, String> LAST_NAME = createField(DSL.name("last_name"), SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>public.user.email</code>.
     */
    public final TableField<UserRecord, String> EMAIL = createField(DSL.name("email"), SQLDataType.VARCHAR(256).nullable(false), this, "");

    /**
     * The column <code>public.user.password</code>.
     */
    public final TableField<UserRecord, String> PASSWORD = createField(DSL.name("password"), SQLDataType.VARCHAR(256).nullable(false), this, "");

    /**
     * The column <code>public.user.image_url</code>.
     */
    public final TableField<UserRecord, String> IMAGE_URL = createField(DSL.name("image_url"), SQLDataType.VARCHAR(256), this, "");

    /**
     * The column <code>public.user.activated</code>.
     */
    public final TableField<UserRecord, Boolean> ACTIVATED = createField(DSL.name("activated"), SQLDataType.BOOLEAN.defaultValue(DSL.field("false", SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>public.user.activation_key</code>.
     */
    public final TableField<UserRecord, String> ACTIVATION_KEY = createField(DSL.name("activation_key"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.user.reset_key</code>.
     */
    public final TableField<UserRecord, String> RESET_KEY = createField(DSL.name("reset_key"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.user.reset_date</code>.
     */
    public final TableField<UserRecord, LocalDateTime> RESET_DATE = createField(DSL.name("reset_date"), SQLDataType.LOCALDATETIME(6), this, "");

    /**
     * The column <code>public.user.lang_key</code>.
     */
    public final TableField<UserRecord, String> LANG_KEY = createField(DSL.name("lang_key"), SQLDataType.VARCHAR(5).defaultValue(DSL.field("'en'::character varying", SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>public.user.created_by</code>.
     */
    public final TableField<UserRecord, String> CREATED_BY = createField(DSL.name("created_by"), SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>public.user.created_date</code>.
     */
    public final TableField<UserRecord, LocalDateTime> CREATED_DATE = createField(DSL.name("created_date"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>public.user.last_modified_by</code>.
     */
    public final TableField<UserRecord, String> LAST_MODIFIED_BY = createField(DSL.name("last_modified_by"), SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>public.user.last_modified_date</code>.
     */
    public final TableField<UserRecord, LocalDateTime> LAST_MODIFIED_DATE = createField(DSL.name("last_modified_date"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)), this, "");

    private User(Name alias, Table<UserRecord> aliased) {
        this(alias, aliased, null);
    }

    private User(Name alias, Table<UserRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.user</code> table reference
     */
    public User(String alias) {
        this(DSL.name(alias), USER);
    }

    /**
     * Create an aliased <code>public.user</code> table reference
     */
    public User(Name alias) {
        this(alias, USER);
    }

    /**
     * Create a <code>public.user</code> table reference
     */
    public User() {
        this(DSL.name("user"), null);
    }

    public <O extends Record> User(Table<O> child, ForeignKey<O, UserRecord> key) {
        super(child, key, USER);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.USER_ACTIVATION_KEY_IDX, Indexes.USER_EMAIL_IDX);
    }

    @Override
    public Identity<UserRecord, Integer> getIdentity() {
        return (Identity<UserRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<UserRecord> getPrimaryKey() {
        return Keys.USER_PKEY;
    }

    @Override
    public User as(String alias) {
        return new User(DSL.name(alias), this);
    }

    @Override
    public User as(Name alias) {
        return new User(alias, this);
    }

    @Override
    public User as(Table<?> alias) {
        return new User(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public User rename(String name) {
        return new User(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public User rename(Name name) {
        return new User(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public User rename(Table<?> name) {
        return new User(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row15 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row15<Integer, String, String, String, String, String, Boolean, String, String, LocalDateTime, String, String, LocalDateTime, String, LocalDateTime> fieldsRow() {
        return (Row15) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function15<? super Integer, ? super String, ? super String, ? super String, ? super String, ? super String, ? super Boolean, ? super String, ? super String, ? super LocalDateTime, ? super String, ? super String, ? super LocalDateTime, ? super String, ? super LocalDateTime, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function15<? super Integer, ? super String, ? super String, ? super String, ? super String, ? super String, ? super Boolean, ? super String, ? super String, ? super LocalDateTime, ? super String, ? super String, ? super LocalDateTime, ? super String, ? super LocalDateTime, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
