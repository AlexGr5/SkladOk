package ru.alexey.zadanie.TekstileApp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Класс для получения запросов от пользователей
 */
public class QueryDTO {

    /**
     * Цвет
     */
    @NotEmpty(message = "Color should not be empty")
    @Size(min = 1, max = 255, message = "Color should be between 1 and 255 size")
    private String itemColor;

    /**
     * Процент материала
     */
    @Min(value = 0, message = "Percentage should be greater then 0")    // Минимальное и
    @Max(value = 100, message = "Percentage should be less than 100")   // максимальное значения
    private Integer materialPercentage;

    /**
     * Для сравнения процента
     * gt – greater than (больше чем)
     * lt – less than (меньше чем)
     * eq – equal (равно)
     */
    @NotEmpty(message = "Compare Type should not be empty")
    @Pattern(regexp = "gt|lt|eq", message = "Compare type must be 'gt', 'lt', or 'eq'")
    private String compareType;

    /**
     * Конструктор по-умолчанию
     */
    public QueryDTO() {
    }

    /**
     * Конструктор со всеми параметрами
     * @param itemColor             - цвет
     * @param materialPercentage    - процент
     * @param compareType           - тип сравнения
     */
    public QueryDTO(String itemColor, Integer materialPercentage, String compareType) {
        this.itemColor = itemColor;
        this.materialPercentage = materialPercentage;
        this.compareType = compareType;
    }

    /**
     * Получить цвет
     * @return      - цвет
     */
    public String getItemColor() {
        return itemColor;
    }

    /**
     * Установить цвет
     * @param itemColor     - цвет
     */
    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    /**
     * Получить процент
     * @return      - процент
     */
    public Integer getMaterialPercentage() {
        return materialPercentage;
    }

    /**
     * Установить процент
     * @param materialPercentage    - процент
     */
    public void setMaterialPercentage(Integer materialPercentage) {
        this.materialPercentage = materialPercentage;
    }

    /**
     * Получить тип сравнения
     * @return      - тип сравнения
     */
    public String getCompareType() {
        return compareType;
    }

    /**
     * Установить тип сравнения
     * @param compareType       - тип сравнения
     */
    public void setCompareType(String compareType) {
        this.compareType = compareType;
    }
}
