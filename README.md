# SkladOk
Практическое задание СкладОк

Реализована серверная чать.

Возможно два запуска: через Docker и на хост компьютере.
1) Docker
   - Зайдя в каталог проекта, вводим команду в консоли: docker-compose up --build (Всё должно заработать)
(в общем-то все готово, в том числе и jar файл (TekstileApp-0.0.1-SNAPSHOT.jar - он нужен для запуска в Docker), но его
 можно пересобрать (если нужно): mvn clean package  )
  
2) Хост
  - нужено установить PostgreSQL и Intellij IDEA
  - в PostgreSQL создаем базу данных с именем: db_skladok
  - в файле resources/application.properties убедитесь, что у вас такой же порт к БД, им БД, имя и пароль:
  - Если есть отличия в имени БД, то нужно создать БД с нужным имененм
  - Если отличаются порт (5432), имя и пароль, то ввести в файле актуальные данные.
# address for DB
spring.datasource.url=jdbc:postgresql://localhost:5432/db_skladok
# Username
spring.datasource.username=postgres
# Password
spring.datasource.password=1
  - Запустить через IDEA класс: TekstileAppApplication.java
  - (Должно заработать)

______________________________________
Тесты API можно сделать через Postman.

Описание:
Технические спецификации API:
1. Регистрация поступления товара
Запрос: POST /storage/items/incoming Тело запроса (JSON):
{
  "itemColor": "строковое_значение",
  "materialPercentage": целое_число_0_100,
  "units": целое_положительное_число
}
Коды ответа:
•	200 - Операция выполнена успешно
•	400 - Некорректные параметры запроса
•	500 - Внутренняя ошибка сервера
2. Регистрация выбытия товара
Запрос: POST /storage/items/outgoing Тело запроса: аналогично предыдущему пункту Коды ответа: аналогично предыдущему пункту
3. Получение информации о товаре
Запрос: GET /storage/items Параметры запроса:
•	itemColor - строковое значение цвета
•	compareType - тип сравнения для процентного содержания материала (значения: "gt", "lt", "eq")
•	materialPercentage - числовое значение для сравнения
Пример запросов:
•	/storage/items?itemColor=crimson&compareType=gt&materialPercentage=85
•	/storage/items?itemColor=navy&compareType=lt&materialPercentage=15
Результат: Общее количество единиц товара, соответствующего заданным критериям (числовое значение)

