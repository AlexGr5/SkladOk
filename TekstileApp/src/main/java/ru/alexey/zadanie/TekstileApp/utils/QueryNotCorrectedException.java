package ru.alexey.zadanie.TekstileApp.utils;

/**
 * Исключение для неправильного запроса от пользователя
 */
public class QueryNotCorrectedException extends RuntimeException {

    /**
     * Конструктор по-умолчанию
     */
    public QueryNotCorrectedException() {}

    /**
     * Конструктор с сообщением
     * @param msg   - сообщение
     */
    public QueryNotCorrectedException(String msg) {
        super(msg);
    }
}
