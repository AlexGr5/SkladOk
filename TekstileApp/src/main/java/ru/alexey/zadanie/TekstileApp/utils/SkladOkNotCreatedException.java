package ru.alexey.zadanie.TekstileApp.utils;

/**
 * Исключение для неудачного создания записи
 */
public class SkladOkNotCreatedException extends RuntimeException {

    /**
     * Конструктор по-умолчанию
     */
    public SkladOkNotCreatedException() {}

    /**
     * Конструктор с сообщением
     * @param msg   - сообщение
     */
    public SkladOkNotCreatedException(String msg) {
        super(msg);
    }
}
