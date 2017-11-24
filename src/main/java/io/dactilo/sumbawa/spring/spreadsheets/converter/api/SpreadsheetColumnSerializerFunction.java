package io.dactilo.sumbawa.spring.spreadsheets.converter.api;

/**
 * Serialize any object of type {@link T}
 * @param <T> The type of object to serialize
 */
@FunctionalInterface
public interface SpreadsheetColumnSerializerFunction<T> {
    String serialize(T string);
}
