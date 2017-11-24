package io.dactilo.sumbawa.spring.spreadsheets.converter.api;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * If a getter is annotated with {@link SpreadsheetColumnIgnore},
 * the field won't appear in the resulting {@link Spreadsheet}
 */
@Retention(value = RUNTIME)
@Target(value = {METHOD})
@Documented
public @interface SpreadsheetColumnIgnore {

}