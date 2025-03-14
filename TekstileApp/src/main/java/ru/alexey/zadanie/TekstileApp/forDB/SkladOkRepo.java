package ru.alexey.zadanie.TekstileApp.forDB;

import org.springframework.data.repository.CrudRepository;
import ru.alexey.zadanie.TekstileApp.models.SkladOk;

import java.util.List;
import java.util.Optional;

/**
 * Отдаем реализацию подключения к БД - Hibernate
 */
public interface SkladOkRepo extends CrudRepository<SkladOk, Integer> {

    /*
    Методы, содержащие GreaterThan, LessThan, Equals,
    Like и другие ключевые слова, распознаются Spring Data JPA, и
    Hibernate генерирует соответствующие SQL-запросы.
     */

    /**
     * Ищет все записи в таблице sklad_ok, у которых цвет (item_color) совпадает с переданным
     * значением и процент материала (material_percentage) точно равен переданному значению
     * @param itemColor             - цвет
     * @param materialPercentage    - процент
     * @return                      - количество вещей на складе
     */
    public List<SkladOk> findByItemColorAndMaterialPercentage(String itemColor, int materialPercentage);

    /**
     * Цвет (item_color) совпадает.
     * Процент материала (material_percentage) больше, чем переданное значение.
     * @param itemColor             - цвет
     * @param materialPercentage    - процент
     * @return                      - количество вещей
     */
    public List<SkladOk> findByItemColorAndMaterialPercentageGreaterThan(String itemColor, int materialPercentage);

    /**
     * Цвет (item_color) совпадает.
     * Процент материала (material_percentage) меньше, чем переданное значение.
     * @param itemColor             - цвет
     * @param materialPercentage    - процент
     * @return                      - количество
     */
    public List<SkladOk> findByItemColorAndMaterialPercentageLessThan(String itemColor, int materialPercentage);
}
