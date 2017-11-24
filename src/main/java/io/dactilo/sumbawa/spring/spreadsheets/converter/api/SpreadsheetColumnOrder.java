package io.dactilo.sumbawa.spring.spreadsheets.converter.api;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotate your getters with {@link SpreadsheetColumnOrder} to define
 * their order priorities.
 * The lowest numbers will appear first in the resulting {@link Spreadsheet}
 */
@Retention(value = RUNTIME)
@Target(value = {METHOD})
@Documented
public @interface SpreadsheetColumnOrder {
    int order();
}