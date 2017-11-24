package io.dactilo.sumbawa.spring.spreadsheets.converter.excel;

import io.dactilo.sumbawa.spring.spreadsheets.converter.api.SpreadsheetStreamerTest;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static io.dactilo.sumbawa.spring.spreadsheets.tests.SpreadsheetsMatchers.isActuallyAnExcelFile;
import static org.junit.Assert.assertThat;

public class ExcelStreamerTest extends SpreadsheetStreamerTest {
    private final ExcelStreamer excelStreamer = new ExcelStreamer(new XSFFWorkbokFactory());

    @Test
    public void testSimpleObject_checkThatDocumentIsAValidZipFile() throws ParseException, IOException {
        final List<SpreadsheetStreamerTest.ObjectExampleDTO> data = Arrays.asList(
                new SpreadsheetStreamerTest.ObjectExampleDTO("field 1", 2, createDate(), true),
                new SpreadsheetStreamerTest.ObjectExampleDTO("field 1 2", 23, createDate(), false)
        );

        byte[] bytes = excelStreamer.toByteArray(spreadsheetConverter.convert(data));
        assertThat(bytes, isActuallyAnExcelFile());
    }

}