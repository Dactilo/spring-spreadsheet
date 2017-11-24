package io.dactilo.sumbawa.spring.spreadsheets.fiters.excel;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Add this annotation to your spring web configuration to enable Excel support
 */
@Retention(value = RUNTIME)
@Target(value = {TYPE})
@Documented
@Import({ExcelFormatterConfiguration.class})
public @interface EnableExcelFormatter {
}
