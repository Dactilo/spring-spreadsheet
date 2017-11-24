package io.dactilo.sumbawa.spring.spreadsheets.converter.api;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotate your getters with {@link SpreadsheetColumnName} to override
 * the name of the field that will appear in the Spreadsheet
 */
@Retention(value = RUNTIME)
@Target(value = {METHOD})
@Documented
public @interface SpreadsheetColumnName {
    String name();
}