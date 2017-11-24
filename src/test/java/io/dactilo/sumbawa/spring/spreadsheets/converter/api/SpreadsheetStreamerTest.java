package io.dactilo.sumbawa.spring.spreadsheets.converter.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class SpreadsheetStreamerTest {
    protected final SpreadsheetConverter spreadsheetConverter = new ObjectToSpreadsheetConverter();

    protected Date createDate() throws ParseException {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.parse("2006-04-05");
    }

    public static class ObjectExampleDTO {
        private final String field1;
        private final int field2;
        private final Date field3;
        private final boolean field4;

        @JsonCreator
        public ObjectExampleDTO(@JsonProperty("field1") String field1,
                                @JsonProperty("field2") int field2,
                                @JsonProperty("field3") Date field3,
                                @JsonProperty("field4") boolean field4) {
            this.field1 = field1;
            this.field2 = field2;
            this.field3 = field3;
            this.field4 = field4;
        }

        public String getField1() {
            return field1;
        }

        public int getField2() {
            return field2;
        }

        public Date getField3() {
            return field3;
        }

        public boolean isField4() {
            return field4;
        }
    }

    public static class ObjectExampleCustomNamesDTO {
        private final String field1;
        private final int field2;
        private final Date field3;
        private final boolean field4;

        @JsonCreator
        public ObjectExampleCustomNamesDTO(@JsonProperty("field1") String field1,
                                           @JsonProperty("field2") int field2,
                                           @JsonProperty("field3") Date field3,
                                           @JsonProperty("field4") boolean field4) {
            this.field1 = field1;
            this.field2 = field2;
            this.field3 = field3;
            this.field4 = field4;
        }

        @SpreadsheetColumnName(name = "Maison")
        @SpreadsheetColumnOrder(order = 4)
        public String getField1() {
            return field1;
        }

        @SpreadsheetColumnOrder(order = 3)
        public int getField2() {
            return field2;
        }

        @SpreadsheetColumnOrder(order = 2)
        public Date getField3() {
            return field3;
        }

        @SpreadsheetColumnOrder(order = 1)
        public boolean isField4() {
            return field4;
        }
    }

    public static class ObjectExampleWithSerliazerDTO {
        private final String field1;
        private final int field2;
        private final Date field3;
        private final boolean field4;
        private final ObjectExampleDTO field5;

        @JsonCreator
        public ObjectExampleWithSerliazerDTO(@JsonProperty("field1") String field1,
                                             @JsonProperty("field2") int field2,
                                             @JsonProperty("field3") Date field3,
                                             @JsonProperty("field4") boolean field4,
                                             @JsonProperty("field5") ObjectExampleDTO field5) {
            this.field1 = field1;
            this.field2 = field2;
            this.field3 = field3;
            this.field4 = field4;
            this.field5 = field5;
        }

        public String getField1() {
            return field1;
        }

        public int getField2() {
            return field2;
        }

        public Date getField3() {
            return field3;
        }

        public boolean isField4() {
            return field4;
        }

        @SpreadsheetColumnSerializer(serializer = ObjectExampleDTOSerializer.class)
        public ObjectExampleDTO getField5() {
            return field5;
        }
    }

    public static class ObjectExampleDTOSerializer implements SpreadsheetColumnSerializerFunction<ObjectExampleDTO> {
        @Override
        public String serialize(ObjectExampleDTO objectExampleDTO) {
            return "FIELD1-> " + objectExampleDTO.field1;
        }
    }
}