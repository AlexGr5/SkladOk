package ru.alexey.zadanie.TekstileApp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * Класс для передачи данных пользователям и получения от пользователей
 */
public class SkladOkDTO {

    /**
     * Цвет
     */
    @NotEmpty(message = "Color should not be empty")
    @Size(min = 1, max = 255, message = "Color should be between 1 and 255 size")
    private String itemColor;

    /**
     * Процент
     */
    @Min(value = 0, message = "Percentage should be greater then 0")    // Минимальное и
    @Max(value = 100, message = "Percentage should be less than 100")   // максимальное значения
    private Integer materialPercentage;

    /**
     * Количество
     */
    @Min(value = 0, message = "Units should be greater then 0")
    private Integer units;

    /**
     * Конструктор по-умолчанию
     */
    public SkladOkDTO() {
    }

    /**
     * Конструктор со всеми параметрами
     * @param itemColor             - цвет
     * @param materialPercentage    - процент
     * @param units                 - количество
     */
    public SkladOkDTO(String itemColor, Integer materialPercentage, Integer units) {
        this.itemColor = itemColor;
        this.materialPercentage = materialPercentage;
        this.units = units;
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
     * Получить процент материала
     * @return      - процент
     */
    public Integer getMaterialPercentage() {
        return materialPercentage;
    }

    /**
     * Установить процент материала
     * @param materialPercentage    - процент
     */
    public void setMaterialPercentage(Integer materialPercentage) {
        this.materialPercentage = materialPercentage;
    }

    /**
     * Получить количество
     * @return      - количество
     */
    public Integer getUnits() {
        return units;
    }

    /**
     * Установить количество
     * @param units     - количество
     */
    public void setUnits(Integer units) {
        this.units = units;
    }
}
