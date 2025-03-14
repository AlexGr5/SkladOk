package ru.alexey.zadanie.TekstileApp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.alexey.zadanie.TekstileApp.dto.QueryDTO;
import ru.alexey.zadanie.TekstileApp.dto.SkladOkDTO;
import ru.alexey.zadanie.TekstileApp.models.SkladOk;
import ru.alexey.zadanie.TekstileApp.dao.SkladOkDAO;
import ru.alexey.zadanie.TekstileApp.utils.QueryNotCorrectedException;
import ru.alexey.zadanie.TekstileApp.utils.QueryNotEnoughGoodsInStockException;
import ru.alexey.zadanie.TekstileApp.utils.SkladOkErrorResponse;
import ru.alexey.zadanie.TekstileApp.utils.SkladOkNotCreatedException;
import ru.alexey.zadanie.TekstileApp.utils.SkladOkNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер (реализация API)
 */
@Controller
@RequestMapping("/storage/items")
public class TekstileController {

    /**
     *  Для работы с БД
     */
    private final SkladOkDAO skladOkDAO;

    /**
     * Бин для проецирования DTO на Model и обратно
     */
    private final ModelMapper modelMapper;

    /**
     * Конструктор, вызывается автоматически
     * @param skladOkDAO    - для работы с БД
     * @param modelMapper   - для проецирования DTO и Model
     */
    @Autowired
    public TekstileController(SkladOkDAO skladOkDAO, ModelMapper modelMapper) {
        this.skladOkDAO = skladOkDAO;
        this.modelMapper = modelMapper;
    }

    /**
     * Вернуть все записи таблицы
     * @return  - JSON с записями
     */
    @GetMapping("/getAll")
    @ResponseBody                           // Возврящаем данные
    public List<SkladOkDTO> getTekstile() {
        return skladOkDAO.findAll().stream().map(this::convertSkladOkToSkladOkDTO)
                .collect(Collectors.toList());
    }

    /**
     * Получить запись по id
     * @param id    - id записи
     * @return      - JSON записи
     */
    @GetMapping("/get/{id}")
    @ResponseBody                           // Возврящаем данные
    public SkladOkDTO getUnit(@PathVariable("id") int id) {
        return convertSkladOkToSkladOkDTO(skladOkDAO.findOne(id));
    }

    /**
     * Регистрация поступления товара
     * @param skladOkDTO        - JSON на добавление
     * @param bindingResult     - для возвращения ошибок
     * @return                  - статус работы
     */
    @PostMapping("/incoming")
    public ResponseEntity<?> registerIncoming(@RequestBody @Valid SkladOkDTO skladOkDTO,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new SkladOkNotCreatedException("Errors into params query");
        }
        skladOkDAO.registerIncoming(skladOkDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Регистрация выбытия товара
     * @param skladOkDTO        - JSON на добавление
     * @param bindingResult     - для возвращения ошибок
     * @return                  - статус работы
     */
    @PostMapping("/outgoing")
    public ResponseEntity<?> registerOutgoing(@RequestBody @Valid SkladOkDTO skladOkDTO,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new SkladOkNotFoundException("Errors into params query");
        }
            skladOkDAO.registerOutgoing(skladOkDTO);
            return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Получение информации о товаре
     * @param queryDTO          - параметры в запросе
     * @param bindingResult     - для возвращения ошибок
     * @return                  - количество найденных записей
     */
    @GetMapping()
    public ResponseEntity<?> getTotalUnits(
            @Valid @ModelAttribute QueryDTO queryDTO, // Используем @ModelAttribute для автоматического биндинга параметров запроса
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new QueryNotCorrectedException("Errors into params query");
        }
        int totalUnits = skladOkDAO.getTotalUnitsByQuery(queryDTO);
        return ResponseEntity.ok(totalUnits);
    }

    /**
     * Перехватывать исключения и вернуть сообщение, что такой записи не найдено
     * @param e     - исключение
     * @return      - ответ пользователю
     */
    @ExceptionHandler(SkladOkNotFoundException.class)
    private ResponseEntity<SkladOkErrorResponse> handleNotFoundException(SkladOkNotFoundException e) {
        SkladOkErrorResponse skladOkErrorResponse = new SkladOkErrorResponse(
                "Unit with this params wasn't found!");
        return new ResponseEntity<>(skladOkErrorResponse, HttpStatus.BAD_REQUEST);    // error 404
    }

    /**
     * Перехватывать исключения и вернуть сообщение, что такой запись не создана
     * @param e     - исключение с сообщением
     * @return      - ответ пользователю
     */
    @ExceptionHandler(SkladOkNotCreatedException.class)
    private ResponseEntity<SkladOkErrorResponse> handleNotCreatedException(SkladOkNotCreatedException e) {
        SkladOkErrorResponse skladOkErrorResponse = new SkladOkErrorResponse(e.getMessage());
        return new ResponseEntity<>(skladOkErrorResponse, HttpStatus.BAD_REQUEST);  // error 400
    }

    /**
     * Перехватывать исключения и вернуть сообщение, что запрос неправильный
     * @param e     - исключение с сообщением
     * @return      - ответ пользователю
     */
    @ExceptionHandler(QueryNotCorrectedException.class)
    private ResponseEntity<SkladOkErrorResponse> handleQueryNotCorrectedException(QueryNotCorrectedException e) {
        SkladOkErrorResponse skladOkErrorResponse = new SkladOkErrorResponse(e.getMessage());
        return new ResponseEntity<>(skladOkErrorResponse, HttpStatus.BAD_REQUEST);    // error 400
    }

    /**
     * Перехватывать исключения и вернуть сообщение, что столько вещей нет на складе
     * @param e     - исключение с сообщением
     * @return      - ответ пользователю
     */
    @ExceptionHandler(QueryNotEnoughGoodsInStockException.class)
    private ResponseEntity<SkladOkErrorResponse> handleQueryNotEnoughGoodsException(QueryNotEnoughGoodsInStockException e) {
        SkladOkErrorResponse skladOkErrorResponse = new SkladOkErrorResponse(e.getMessage());
        return new ResponseEntity<>(skladOkErrorResponse, HttpStatus.BAD_REQUEST);  // error 400
    }

    /**
     * Spring должен сам выбрать более "узкие" исключения и отловка Exception
     * не должна испортить работу других (наследуемых) исключений. Для ошибки 500
     * @param e     - исключение
     * @return      - ответ пользователю
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<SkladOkErrorResponse> handleGlobalException(Exception e) {
        SkladOkErrorResponse errorResponse = new SkladOkErrorResponse(
                "Internal server error: " + e.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }

    /**
     * Преобразование ДТО в модель
     * @param skladOkDTO    - данные от пользователя
     * @return              - модель
     */
    private SkladOk convertSkladOkDTOToSkladOk(SkladOkDTO skladOkDTO) {
        return modelMapper.map(skladOkDTO, SkladOk.class);
    }

    /**
     * Преобразовать модель в ДТО
     * @param skladOk       - модель
     * @return              - данные для пользователя
     */
    private SkladOkDTO convertSkladOkToSkladOkDTO(SkladOk skladOk) {
        return modelMapper.map(skladOk, SkladOkDTO.class);
    }
}
