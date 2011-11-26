/*
 * (c) Copyright 2005-2011 JAXIO, www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-backend:src/test/java/domain/CompositePkTest.cpk.vm.java
 */
package com.yourcompany.yourproject.domain;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.Serializable;
import com.yourcompany.yourproject.util.ValueGenerator;

/**
 * Basic tests for LegacyPk
 */
public class LegacyPkTest {

    @Test
    public void compositePrimaryKeycode_test1() {
        LegacyPk cpk = new LegacyPk();
        assertFalse(cpk.isCodeSet());
        assertNull(cpk.getCode());
        assertTrue(cpk.isEmpty());
    }

    @Test
    public void compositePrimaryKeycode_test2() {
        LegacyPk cpk = new LegacyPk();
        cpk.setCode(ValueGenerator.getUniqueString(8));

        assertTrue(cpk.isCodeSet());
        assertNotNull(cpk.getCode());
        assertFalse(cpk.isEmpty());
    }

    @Test
    public void compositePrimaryKeydept_test1() {
        LegacyPk cpk = new LegacyPk();
        assertFalse(cpk.isDeptSet());
        assertNull(cpk.getDept());
        assertTrue(cpk.isEmpty());
    }

    @Test
    public void compositePrimaryKeydept_test2() {
        LegacyPk cpk = new LegacyPk();
        cpk.setDept(ValueGenerator.getUniqueNumeric(Integer.class, "2147483647"));

        assertTrue(cpk.isDeptSet());
        assertNotNull(cpk.getDept());
        assertFalse(cpk.isEmpty());
    }

    @Test
    public void compositePrimaryKeyname_test1() {
        LegacyPk cpk = new LegacyPk();
        assertFalse(cpk.isNameSet());
        assertNull(cpk.getName());
        assertTrue(cpk.isEmpty());
    }

    @Test
    public void compositePrimaryKeyname_test2() {
        LegacyPk cpk = new LegacyPk();
        cpk.setName(ValueGenerator.getUniqueString(16));

        assertTrue(cpk.isNameSet());
        assertNotNull(cpk.getName());
        assertFalse(cpk.isEmpty());
    }

    @Test
    public void isEmptyTrue() {
        LegacyPk cpk = new LegacyPk();
        assertTrue(cpk.isEmpty());
    }

    @Test
    public void isEmptyFalse() {
        LegacyPk cpk = new LegacyPk();
        cpk.setCode(ValueGenerator.getUniqueString(8));
        cpk.setDept(ValueGenerator.getUniqueNumeric(Integer.class, "2147483647"));
        cpk.setName(ValueGenerator.getUniqueString(16));
        assertFalse(cpk.isEmpty());
    }

    @Test
    public void toStringNotNullWhenNew() {
        LegacyPk cpk = new LegacyPk();
        assertNotNull(cpk.toString());
    }

    @Test
    public void toStringHasLength() {
        LegacyPk cpk = new LegacyPk();
        cpk.setCode(ValueGenerator.getUniqueString(8));
        cpk.setDept(ValueGenerator.getUniqueNumeric(Integer.class, "2147483647"));
        cpk.setName(ValueGenerator.getUniqueString(16));
        assertNotNull(cpk.toString());
        assertTrue(cpk.toString().length() > 0);
    }

    @Test
    public void equality_test1() {
        LegacyPk cpk = new LegacyPk();
        assertEquals(cpk, cpk);
        assertEquals(cpk.hashCode(), cpk.hashCode());
        assertEquals(0, cpk.compareTo(cpk));
    }

    @Test
    public void equality_test2() {
        LegacyPk cpk = new LegacyPk();
        assertFalse(cpk.equals(null));
        try {
            cpk.compareTo(null);
            fail();
        } catch (Throwable t) {
            // expected exception
        }
    }

    @Test
    public void equality_test3() {
        LegacyPk cpk1 = new LegacyPk();
        LegacyPk cpk2 = new LegacyPk();

        String code = ValueGenerator.getUniqueString(8);
        cpk1.setCode(code);
        cpk2.setCode(code);

        Integer dept = ValueGenerator.getUniqueNumeric(Integer.class, "2147483647");
        cpk1.setDept(dept);
        cpk2.setDept(dept);

        String name = ValueGenerator.getUniqueString(16);
        cpk1.setName(name);
        cpk2.setName(name);

        assertTrue(cpk1.hashCode() == cpk2.hashCode());
        assertTrue(cpk1.equals(cpk2));
        assertTrue(cpk2.equals(cpk1));
        assertEquals(0, cpk1.compareTo(cpk2));
        assertEquals(0, cpk2.compareTo(cpk1));
    }
}