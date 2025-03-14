package ru.alexey.zadanie.TekstileApp.utils;

/**
 * Исключение для неудачного поиска записи
 */
public class SkladOkNotFoundException extends RuntimeException {

    /**
     * Конструктор по-умолчанию
     */
    public SkladOkNotFoundException() {}

    /**
     * Конструктор с сообщением
     * @param msg   - сообщение
     */
    public SkladOkNotFoundException(String msg) {
        super(msg);
    }
}
