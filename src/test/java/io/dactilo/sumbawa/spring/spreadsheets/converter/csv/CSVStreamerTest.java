package io.dactilo.sumbawa.spring.spreadsheets.converter.csv;

import io.dactilo.sumbawa.spring.spreadsheets.converter.api.SpreadsheetStreamerTest;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CSVStreamerTest extends SpreadsheetStreamerTest {
    private final CSVPrinterFactory csvPrinterFactory = new CSVPrinterFactory();
    private final CSVStreamer csvStreamer = new CSVStreamer(csvPrinterFactory);

    @Test
    public void testSimpleObject_conversionToCSVIsSuccessful() throws ParseException, IOException {
        final List<ObjectExampleDTO> data = Arrays.asList(
                new ObjectExampleDTO("field 1", 2, createDate(), true),
                new ObjectExampleDTO("field 1 2", 23, createDate(), false)
        );

        assertEquals("field1;field2;field3;field4\r\n" +
                        "field 1;2;2006-04-05T00:00:00;true\r\n" +
                        "field 1 2;23;2006-04-05T00:00:00;false\r\n",
                new String(csvStreamer.toByteArray(spreadsheetConverter.convert(data)))
        );
    }

    @Test
    public void testSimpleObjectCustomName_conversionToCSVIsSuccessful() throws ParseException, IOException {
        final List<ObjectExampleCustomNamesDTO> data = Arrays.asList(
                new ObjectExampleCustomNamesDTO("field 1", 2, createDate(), true),
                new ObjectExampleCustomNamesDTO("field 1 2", 23, createDate(), false)
        );

        assertEquals("Maison;field2;field3;field4\r\n" +
                        "field 1;2;2006-04-05T00:00:00;true\r\n" +
                        "field 1 2;23;2006-04-05T00:00:00;false\r\n",
                new String(csvStreamer.toByteArray(spreadsheetConverter.convert(data)))
        );
    }

    @Test
    public void testSimpleObjectSerializer_conversionToCSVIsSuccessful() throws ParseException, IOException {
        final List<ObjectExampleWithSerliazerDTO> data = Arrays.asList(
                new ObjectExampleWithSerliazerDTO("field 1", 2, createDate(), true, new ObjectExampleDTO("field 1", 2, createDate(), true)),
                new ObjectExampleWithSerliazerDTO("field 1 2", 23, createDate(), false, new ObjectExampleDTO("field 4", 2, createDate(), true))
        );

        assertEquals("field1;field2;field3;field5;field4\r\n" +
                        "field 1;2;2006-04-05T00:00:00;FIELD1-> field 1;true\r\n" +
                        "field 1 2;23;2006-04-05T00:00:00;FIELD1-> field 4;false\r\n",
                new String(csvStreamer.toByteArray(spreadsheetConverter.convert(data)))
        );
    }

}