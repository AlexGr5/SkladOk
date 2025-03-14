package ru.alexey.zadanie.TekstileApp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


/**
 * Класс для проецирования таблицы базы данных на объект класса java
 */
@Entity                     // Пометка, что этот класс является сущностью
@Table(name = "skladok")    // Имя таблицы в базе данных
public class SkladOk {

    /**
     * Первичный ключ
     */
    @Id                                                     // Это поле является первичным ключем
    @Column(name = "pk_skladok")                            // Имя колонки в бд
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // Как происходит генерация ПК
    private int pk_skladok;

    /**
     * Цвет изделия
     */
    @Column(name = "itemColor", length = 255, nullable = false)
    @NotEmpty(message = "Color should not be empty")
    @Size(min = 1, max = 255, message = "Color should be between 1 and 255 size")
    private String itemColor;

    /**
     * Процент материала от 0 до 100
     */
    @Column(name = "materialPercentage", nullable = false)
    @Min(value = 0, message = "Percentage should be greater then 0")    // Минимальное и
    @Max(value = 100, message = "Percentage should be less than 100")   // максимальное значения
    private Integer materialPercentage;

    /**
     * Количество товара
     */
    @Column(name = "units", nullable = false)
    @Min(value = 0, message = "Units should be greater then 0")
    private Integer units;

    /**
     * Конструктор по-умолчанию
     */
    public SkladOk() {
    }

    /**
     * Конструктор со всеми параметрами
     * @param pk_skladok            - ПК
     * @param itemColor             - цвет
     * @param materialPercentage    - состав
     * @param units                 - количество
     */
    public SkladOk(int pk_skladok, String itemColor, Integer materialPercentage, Integer units) {
        this.pk_skladok = pk_skladok;
        this.itemColor = itemColor;
        this.materialPercentage = materialPercentage;
        this.units = units;
    }

    /**
     * Получить ПК
     * @return  - ПК
     */
    public int getPk_skladok() {
        return pk_skladok;
    }

    /**
     * Установить ПК
     * @param pk_skladok    - ПК
     */
    public void setPk_skladok(int pk_skladok) {
        this.pk_skladok = pk_skladok;
    }

    /**
     * Получить цвет
     * @return  - цвет строка
     */
    public String getItemColor() {
        return itemColor;
    }

    /**
     * Установить цвет
     * @param itemColor - цвет
     */
    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    /**
     * получить процент материала
     * @return  - процент
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
     * @return  - количество
     */
    public Integer getUnits() {
        return units;
    }

    /**
     * Установить количество
     * @param units - количество
     */
    public void setUnits(Integer units) {
        this.units = units;
    }

    /**
     * Для отображения в констоль
     * @return  - объект в виде строки
     */
    @Override
    public String toString() {
        return "SkladOk{" +
                "pk_skladok=" + pk_skladok +
                ", itemColor='" + itemColor + '\'' +
                ", materialPercentage=" + materialPercentage +
                ", units=" + units +
                '}';
    }
}
