package ru.alexey.zadanie.TekstileApp.utils;

/**
 * Класс для отправки сообщения об ошибке пользователю
 */
public class SkladOkErrorResponse {

    /**
     * Сообщение об ошибке
     */
    private String message;

    /**
     * Конструктор с сообщением
     * @param message   - сообщение
     */
    public SkladOkErrorResponse(String message) {
        this.message = message;
    }

    /**
     * Получить сообщение
     * @return  - сообщение
     */
    public String getMessage() {
        return message;
    }

    /**
     * Установить сообщение
     * @param message   - сообщение
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
