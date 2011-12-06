/*
 * (c) Copyright 2005-2011 JAXIO, www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-backend:src/test/java/domain/ModelTest.e.vm.java
 */
package com.yourcompany.yourproject.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.yourcompany.yourproject.util.ValueGenerator;

/**
 * Basic tests for ContactInfo
 */
public class ContactInfoTest {

    // test unique primary key
    @Test
    public void newInstanceHasNoPrimaryKey() {
        ContactInfo model = new ContactInfo();
        assertFalse(model.isPrimaryKeySet());
    }

    @Test
    public void isPrimaryKeySetReturnsTrue() {
        ContactInfo model = new ContactInfo();
        model.setId(ValueGenerator.getUniqueNumeric(Integer.class, "2147483647"));
        assertNotNull(model.getId());
        assertTrue(model.isPrimaryKeySet());
    }

    // test columns methods

    @Test
    public void toStringNotNull() {
        ContactInfo model = new ContactInfo();
        assertNotNull(model.toString());
    }

    @Test
    public void equalsUsingBusinessKey() {
        ContactInfo model1 = new ContactInfo();
        ContactInfo model2 = new ContactInfo();
        String accountId = ValueGenerator.getUniqueString(32);
        Account parent = new Account();
        parent.setPrimaryKey(accountId);
        model1.setParent(parent);
        model2.setParent(parent);
        assertTrue(model1.equals(model2));
        assertTrue(model2.equals(model1));
        assertTrue(model1.hashCode() == model2.hashCode());
    }
}