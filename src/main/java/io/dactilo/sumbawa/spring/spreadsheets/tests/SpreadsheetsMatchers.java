package io.dactilo.sumbawa.spring.spreadsheets.tests;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.hamcrest.core.IsCollectionContaining.hasItems;

public class SpreadsheetsMatchers {
    public static Matcher<byte[]> isActuallyAnExcelFile() {
        return new BaseMatcher<byte[]>() {
            @Override
            public boolean matches(Object o) {
                final List<String> names = new ArrayList<>();

                try (ZipInputStream zipStream = new ZipInputStream(new ByteArrayInputStream((byte[]) o))) {
                    ZipEntry zipEntry;
                    while ((zipEntry = zipStream.getNextEntry()) != null) {
                        names.add(zipEntry.getName());
                        zipStream.closeEntry();
                    }
                } catch (IOException e) {
                    return false;
                }

                return hasItems("_rels/.rels", "docProps/app.xml", "xl/styles.xml", "xl/workbook.xml").matches(names);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Given binary data corresponds to an excel file");
            }
        };
    }
}
