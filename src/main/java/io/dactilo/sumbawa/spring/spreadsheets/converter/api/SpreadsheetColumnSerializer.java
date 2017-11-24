package io.dactilo.sumbawa.spring.spreadsheets.converter.api;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Allows your to serialize complex objects into a string
 * Applies a {@link SpreadsheetColumnSerializerFunction} to a Spreadsheet value before the conversion
 */
@Retention(value = RUNTIME)
@Target(value = {METHOD})
@Documented
public @interface SpreadsheetColumnSerializer {
    Class<? extends SpreadsheetColumnSerializerFunction> serializer();
}