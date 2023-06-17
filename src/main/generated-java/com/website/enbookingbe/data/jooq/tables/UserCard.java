/*
 * This file is generated by jOOQ.
 */
package com.website.enbookingbe.data.jooq.tables;


import com.website.enbookingbe.data.jooq.Keys;
import com.website.enbookingbe.data.jooq.Public;
import com.website.enbookingbe.data.jooq.tables.records.UserCardRecord;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserCard extends TableImpl<UserCardRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.user_card</code>
     */
    public static final UserCard USER_CARD = new UserCard();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserCardRecord> getRecordType() {
        return UserCardRecord.class;
    }

    /**
     * The column <code>public.user_card.card_id</code>.
     */
    public final TableField<UserCardRecord, Integer> CARD_ID = createField(DSL.name("card_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.user_card.user_id</code>.
     */
    public final TableField<UserCardRecord, Integer> USER_ID = createField(DSL.name("user_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.user_card.learned</code>.
     */
    public final TableField<UserCardRecord, Boolean> LEARNED = createField(DSL.name("learned"), SQLDataType.BOOLEAN, this, "");

    /**
     * The column <code>public.user_card.favorite</code>.
     */
    public final TableField<UserCardRecord, Boolean> FAVORITE = createField(DSL.name("favorite"), SQLDataType.BOOLEAN, this, "");

    private UserCard(Name alias, Table<UserCardRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserCard(Name alias, Table<UserCardRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.user_card</code> table reference
     */
    public UserCard(String alias) {
        this(DSL.name(alias), USER_CARD);
    }

    /**
     * Create an aliased <code>public.user_card</code> table reference
     */
    public UserCard(Name alias) {
        this(alias, USER_CARD);
    }

    /**
     * Create a <code>public.user_card</code> table reference
     */
    public UserCard() {
        this(DSL.name("user_card"), null);
    }

    public <O extends Record> UserCard(Table<O> child, ForeignKey<O, UserCardRecord> key) {
        super(child, key, USER_CARD);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<UserCardRecord> getPrimaryKey() {
        return Keys.USER_CARD_PKEY;
    }

    @Override
    public List<ForeignKey<UserCardRecord, ?>> getReferences() {
        return Arrays.asList(Keys.USER_CARD__USER_CARD_CARD_ID_FKEY, Keys.USER_CARD__USER_CARD_USER_ID_FKEY);
    }

    private transient Card _card;
    private transient User _user;

    /**
     * Get the implicit join path to the <code>public.card</code> table.
     */
    public Card card() {
        if (_card == null)
            _card = new Card(this, Keys.USER_CARD__USER_CARD_CARD_ID_FKEY);

        return _card;
    }

    /**
     * Get the implicit join path to the <code>public.user</code> table.
     */
    public User user() {
        if (_user == null)
            _user = new User(this, Keys.USER_CARD__USER_CARD_USER_ID_FKEY);

        return _user;
    }

    @Override
    public UserCard as(String alias) {
        return new UserCard(DSL.name(alias), this);
    }

    @Override
    public UserCard as(Name alias) {
        return new UserCard(alias, this);
    }

    @Override
    public UserCard as(Table<?> alias) {
        return new UserCard(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserCard rename(String name) {
        return new UserCard(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserCard rename(Name name) {
        return new UserCard(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserCard rename(Table<?> name) {
        return new UserCard(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, Integer, Boolean, Boolean> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function4<? super Integer, ? super Integer, ? super Boolean, ? super Boolean, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function4<? super Integer, ? super Integer, ? super Boolean, ? super Boolean, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
