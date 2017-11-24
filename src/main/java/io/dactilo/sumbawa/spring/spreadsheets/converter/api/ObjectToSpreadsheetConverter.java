package io.dactilo.sumbawa.spring.spreadsheets.converter.api;

import java.beans.Introspector;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * Converts a list of Java Beans into a {@link Spreadsheet} description.
 * This class allows to use the following annotation on the Java Bean getter description:
 * <ul>
 *     <li>{@link SpreadsheetColumnIgnore}</li>
 *     <li>{@link SpreadsheetColumnName}</li>
 *     <li>{@link SpreadsheetColumnOrder}</li>
 *     <li>{@link SpreadsheetColumnSerializer}</li>
 * </ul>
 */
public class ObjectToSpreadsheetConverter implements SpreadsheetConverter {
    @Override
    public Spreadsheet convert(List<?> input) {
        final List<Map<String, Object>> inputsAsMap =
                input.stream()
                     .map(this::convertValue)
                     .collect(Collectors.toList());

        final Map<String, Integer> columnNumberFromName = fetchColumnOrder(inputsAsMap);

        final List<List<Object>> rows = fetchRows(inputsAsMap, columnNumberFromName);
        final List<String> header = fetchHeader(columnNumberFromName);

        return new Spreadsheet(header, rows);
    }

    private Map<String, Object> convertValue(Object readRow) {
        final Class<?> clazz = readRow.getClass();
        final Map<String, Object> results = new LinkedHashMap<>();

        Method[] methods = clazz.getDeclaredMethods();
        Arrays.sort(methods, this::comparisonBetween);

        for (Method method : methods) {
            if(method.getAnnotation(SpreadsheetColumnIgnore.class) == null) {
                if (isGetter(method.getName())) {
                    try {
                        Object methodResult = method.invoke(readRow);

                        SpreadsheetColumnSerializer spreadsheetColumnSerializer = method.getAnnotation(SpreadsheetColumnSerializer.class);

                        if (spreadsheetColumnSerializer == null) {
                            if (methodResult instanceof Date) {
                                methodResult = ((Date) methodResult).toInstant();
                            }
                        } else {
                            methodResult = spreadsheetColumnSerializer.serializer().getDeclaredConstructor().newInstance().serialize(methodResult);
                        }

                        results.put(getPropertyName(method), methodResult);
                    } catch (ReflectiveOperationException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            }
        }

        return results;
    }

    private int comparisonBetween(Method method1, Method method2) {
        SpreadsheetColumnOrder spreadsheetColumnOrder1 = method1.getAnnotation(SpreadsheetColumnOrder.class);
        SpreadsheetColumnOrder spreadsheetColumnOrder2 = method2.getAnnotation(SpreadsheetColumnOrder.class);

        if(spreadsheetColumnOrder1 == null && spreadsheetColumnOrder2 == null) {
            return method1.getName().compareTo(method2.getName());
        } else if(spreadsheetColumnOrder1 == null) {
            return -1;
        } else if(spreadsheetColumnOrder2 == null) {
            return 1;
        } else {
            return spreadsheetColumnOrder2.order() - spreadsheetColumnOrder1.order();
        }
    }

    private boolean isGetter(String methodName) {
        return methodName.startsWith("get") || methodName.startsWith("is");
    }

    private String getPropertyName(Method method) {
        SpreadsheetColumnName spreadsheetColumnName = method.getAnnotation(SpreadsheetColumnName.class);
        final String methodName = method.getName();
        return spreadsheetColumnName == null ? Introspector.decapitalize(methodName.substring(methodName.startsWith("is") ? 2 : 3)) : spreadsheetColumnName.name();
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
