package ru.alexey.zadanie.TekstileApp.utils;

/**
 * Исключение для нехватки товара для вычитания
 */
public class QueryNotEnoughGoodsInStockException extends RuntimeException {

    /**
     * Конструктор по-умолчанию
     */
    public QueryNotEnoughGoodsInStockException() {}

    /**
     * Конструктор с сообщением
     * @param msg   - сообщение
     */
    public QueryNotEnoughGoodsInStockException(String msg) {
        super(msg);
    }

}
