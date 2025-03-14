package ru.alexey.zadanie.TekstileApp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexey.zadanie.TekstileApp.dto.QueryDTO;
import ru.alexey.zadanie.TekstileApp.dto.SkladOkDTO;
import ru.alexey.zadanie.TekstileApp.forDB.SkladOkRepo;
import ru.alexey.zadanie.TekstileApp.models.SkladOk;
import ru.alexey.zadanie.TekstileApp.utils.QueryNotCorrectedException;
import ru.alexey.zadanie.TekstileApp.utils.QueryNotEnoughGoodsInStockException;
import ru.alexey.zadanie.TekstileApp.utils.SkladOkNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Класс для работы с БД
 */
@Service
@Component
@Transactional
public class SkladOkDAO {

    /**
     * Реализация от Hibernate подключения к БД
     */
    @Autowired
    private SkladOkRepo skladOkRepo;

    /**
     * Получения всех записей в таблице
     * @return      - все записи
     */
    public List<SkladOk> findAll() {
        return (List<SkladOk>) skladOkRepo.findAll();
    }

    /**
     * Получение записи по id(ПК)
     * @param id    - первичный ключ
     * @return      - запись, если нашлась
     */
    public SkladOk findOne(int id) {
        Optional<SkladOk> optionalSkladOk = skladOkRepo.findById(id);
        return optionalSkladOk.orElseThrow(SkladOkNotFoundException::new);
    }

    /**
     * Сохранение сущности в таблицу
     * @param skladOk   - модель записи
     */
    @Transactional
    public void save(SkladOk skladOk) {
        skladOkRepo.save(skladOk);
    }

    /**
     * Найти записи по цвету и проценту материала
     * @param color         - цвет
     * @param percentage    - процент материала
     * @return              - записи, если нашлись
     */
    public List<SkladOk> findByColorAndPercentage(String color, int percentage) {
        return skladOkRepo.findByItemColorAndMaterialPercentage(color, percentage);
    }

    /**
     * Добавление новой записи в таблицу, если нет записи с таким же цветом и процентом.
     * Если есть, то складывается количество и сохраняется новое.
     * Если нет, то создается новая запись.
     * @param dto       - данные от пользователя
     */
    @Transactional
    public void registerIncoming(SkladOkDTO dto) {
        List<SkladOk> existingItems = findByColorAndPercentage(dto.getItemColor(), dto.getMaterialPercentage());

        if (!existingItems.isEmpty()) {
            // Если хотя бы один элемент найден, обновляем его
            SkladOk skladOk = existingItems.get(0);  // Можно выбрать любой элемент из списка, если их несколько
            skladOk.setUnits(skladOk.getUnits() + dto.getUnits());
            save(skladOk);
        } else {
            // Если элементов нет, создаем новый
            save(convertSkladOkDTOToSkladOk(dto));
        }
    }

    /**
     * Вычитание количества вещей по цвету и процентам.
     * Если вычитаем больше, чем есть, то исключение.
     * @param dto   - данные от пользователя
     */
    @Transactional
    public void registerOutgoing(SkladOkDTO dto) {
        List<SkladOk> existingItems = findByColorAndPercentage(dto.getItemColor(), dto.getMaterialPercentage());

        if (existingItems.isEmpty()) {
            throw new SkladOkNotFoundException("Units not found");
        }

        // Можно выбрать первый элемент из списка, если их несколько
        SkladOk skladOk = existingItems.get(0);

        if (skladOk.getUnits() < dto.getUnits()) {
            throw new QueryNotEnoughGoodsInStockException("Not enough goods in stock");
        }

        skladOk.setUnits(skladOk.getUnits() - dto.getUnits());
        save(skladOk);
    }

    /**
     * Получить количество вещей удовлетворяющих запрос от пользователя
     * @param queryDTO      - запрос от пользователя
     * @return              - количество записей, если есть, иначе 0
     */
    public int getTotalUnitsByQuery(QueryDTO queryDTO) {
        List<SkladOk> results;

        /*
        gt – greater than (больше чем)
        lt – less than (меньше чем)
        eq – equal (равно)
         */
        switch (queryDTO.getCompareType()) {
            case "gt":
                results = skladOkRepo.findByItemColorAndMaterialPercentageGreaterThan(
                        queryDTO.getItemColor(), queryDTO.getMaterialPercentage());
                break;
            case "lt":
                results = skladOkRepo.findByItemColorAndMaterialPercentageLessThan(
                        queryDTO.getItemColor(), queryDTO.getMaterialPercentage());
                break;
            case "eq":
                results = skladOkRepo.findByItemColorAndMaterialPercentage(
                        queryDTO.getItemColor(), queryDTO.getMaterialPercentage());
                break;
            default:
                throw new QueryNotCorrectedException("Not correct Compare Type");
        }
        return results.stream().mapToInt(SkladOk::getUnits).sum();
    }

    /**
     * Преобразовать ДТО в модель
     * @param dto   - данные от пользователя
     * @return      - модель
     */
    public SkladOk convertSkladOkDTOToSkladOk(SkladOkDTO dto) {
        return new SkladOk(0, dto.getItemColor(), dto.getMaterialPercentage(), dto.getUnits());
    }
}
