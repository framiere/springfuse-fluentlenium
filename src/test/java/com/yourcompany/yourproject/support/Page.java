package com.yourcompany.yourproject.support;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates the field is a page and should be initialized with the driver
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface Page {

}
