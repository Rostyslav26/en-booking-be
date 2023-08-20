/*
 * This file is generated by jOOQ.
 */
package com.website.enbookingbe.data.jooq.tables.records;


import com.website.enbookingbe.data.jooq.tables.QuizCard;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class QuizCardRecord extends UpdatableRecordImpl<QuizCardRecord> implements Record3<Integer, Integer, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.quiz_card.quiz_id</code>.
     */
    public void setQuizId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.quiz_card.quiz_id</code>.
     */
    public Integer getQuizId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.quiz_card.card_id</code>.
     */
    public void setCardId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.quiz_card.card_id</code>.
     */
    public Integer getCardId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.quiz_card.status</code>.
     */
    public void setStatus(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.quiz_card.status</code>.
     */
    public String getStatus() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<Integer, Integer> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, Integer, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Integer, Integer, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return QuizCard.QUIZ_CARD.QUIZ_ID;
    }

    @Override
    public Field<Integer> field2() {
        return QuizCard.QUIZ_CARD.CARD_ID;
    }

    @Override
    public Field<String> field3() {
        return QuizCard.QUIZ_CARD.STATUS;
    }

    @Override
    public Integer component1() {
        return getQuizId();
    }

    @Override
    public Integer component2() {
        return getCardId();
    }

    @Override
    public String component3() {
        return getStatus();
    }

    @Override
    public Integer value1() {
        return getQuizId();
    }

    @Override
    public Integer value2() {
        return getCardId();
    }

    @Override
    public String value3() {
        return getStatus();
    }

    @Override
    public QuizCardRecord value1(Integer value) {
        setQuizId(value);
        return this;
    }

    @Override
    public QuizCardRecord value2(Integer value) {
        setCardId(value);
        return this;
    }

    @Override
    public QuizCardRecord value3(String value) {
        setStatus(value);
        return this;
    }

    @Override
    public QuizCardRecord values(Integer value1, Integer value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached QuizCardRecord
     */
    public QuizCardRecord() {
        super(QuizCard.QUIZ_CARD);
    }

    /**
     * Create a detached, initialised QuizCardRecord
     */
    public QuizCardRecord(Integer quizId, Integer cardId, String status) {
        super(QuizCard.QUIZ_CARD);

        setQuizId(quizId);
        setCardId(cardId);
        setStatus(status);
        resetChangedOnNotNull();
    }
}
