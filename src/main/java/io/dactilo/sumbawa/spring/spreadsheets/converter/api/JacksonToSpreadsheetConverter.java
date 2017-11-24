package io.dactilo.sumbawa.spring.spreadsheets.converter.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.*;
import java.util.stream.Collectors;

public class JacksonToSpreadsheetConverter implements SpreadsheetConverter {
    private final ObjectMapper objectMapper;

    public JacksonToSpreadsheetConverter() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Override
    public <I> Spreadsheet convert(List<I> input) {

        final List<Map<String, Object>> inputsAsMap =
                input.stream()
                     .map(readRow -> objectMapper.<Map<String, Object>> convertValue(readRow, new TypeReference<Map<String, Object>>() {}))
                     .collect(Collectors.toList());

        final Map<String, Integer> columnNumberFromName = fetchColumnOrder(inputsAsMap);

        final List<List<Object>> rows = fetchRows(inputsAsMap, columnNumberFromName);
        final List<String> header = fetchHeader(columnNumberFromName);

        return new Spreadsheet(header, rows);
    }

    private List<String> fetchHeader(Map<String, Integer> columnNumberFromName) {
        final List<String> headers = new ArrayList<>(
                Collections.nCopies(columnNumberFromName.size(), "")
        );

        for (Map.Entry<String, Integer> stringIntegerEntry : columnNumberFromName.entrySet()) {
            headers.set(stringIntegerEntry.getValue(), stringIntegerEntry.getKey());
        }

        return headers;
    }

    private List<List<Object>> fetchRows(Iterable<Map<String, Object>> inputsAsMap,
                                   Map<String, Integer> columnNumberFromName) {
        final List<List<Object>> rows = new LinkedList<>();
        for (Map<String, Object> inputAsMap: inputsAsMap) {
            final List<Object> row = new ArrayList<>(Collections.nCopies(columnNumberFromName.size(), null));

            for (Map.Entry<String, Object> csvRow : inputAsMap.entrySet()) {
                row.set(columnNumberFromName.get(csvRow.getKey()), csvRow.getValue());
            }
            rows.add(row);
        }
        return rows;
    }

    private Map<String, Integer> fetchColumnOrder(Iterable<Map<String, Object>> inputsAsMap) {
        final Map<String, Integer> columnNumberFromName = new HashMap<>();
        for (Map<String, Object> inputAsMap : inputsAsMap) {
            for(String key: inputAsMap.keySet()) {
                if(!columnNumberFromName.containsKey(key)) {
                    columnNumberFromName.put(key, columnNumberFromName.size());
                }
            }
        }
        return columnNumberFromName;
    }
}
