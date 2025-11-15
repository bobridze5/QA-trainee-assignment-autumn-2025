# Задание 2.1

<table border="1" cellpadding="5" cellspacing="0" style="border-collapse: collapse; width: 100%;">
<thead>
<tr>
<th>Идентификатор</th>
<th>Приоритет</th>
<th>Название</th>
<th>Описание</th>
<th>Предусловия</th>
<th>Тестовые данные</th>
<th>Шаги</th>
<th>Ожидаемый результат</th>
</tr>
</thead>
<tbody>
<tr>
<td rowspan="3">TR001</td>
<td rowspan="3">Высокий</td>
<td rowspan="3">Успешное сохранение объявления</td>
<td rowspan="3">Тест проверяет работу API:<br>POST <a href="https://qa-internship.avito.com/api/1/item">https://qa-internship.avito.com/api/1/item</a><br>при различных валидных данных.</td>
<td rowspan="3">Пользователь с <code>sellerID = {sellerID}</code> создан заранее.<br>Указаны параметры запроса. (таблица 1.7)</td>
<td rowspan="3">

```json

{
  "sellerID": {sellerID},
  "name": {name},
  "price": {price},
  "statistics": {
    "likes": {likes},
    "viewCount": {viewCount},
    "contacts": {contacts}
  }
}

```
</td>
<td>Отправить POST запрос на <a href="https://qa-internship.avito.com/api/1/item">https://qa-internship.avito.com/api/1/item</a></td>
<td>Запрос успешно отправлен на сервер (запрос успешно выполнен)</td>
</tr>
<tr>
<td>Проверить код состояния</td>
<td>HTTP Status: 200 OK</td>
</tr>
<tr>
<td>Проверить тело ответа от сервера</td>
<td>Объявление было успешно сохранено<br>Происходит формирование ответа.<br>Тело ответа в формате JSON возвращается от сервера и будет иметь следующий вид:<br>

```json

{
  "status": "Сохранили объявление - 20084839-8520-4704-92e4-14d07cda6ffe"
}

```
</td>
</tr>
<tr>
<td rowspan="3">TR002</td>
<td rowspan="3">Высокий</td>
<td rowspan="3">Неуспешное сохранение объявления</td>
<td rowspan="3">Тест проверяет работу API:<br>POST <a href="https://qa-internship.avito.com/api/1/item">https://qa-internship.avito.com/api/1/item</a><br>при различных невалидных данных.</td>
<td rowspan="3">Пользователь с <code>sellerID = {sellerID}</code> создан заранее.<br>Указаны параметры запроса. (таблица 1.7)</td>
<td rowspan="3">

```json

{
  "sellerID": {sellerID},
  "name": {name},
  "price": {price},
  "statistics": {
    "likes": {likes},
    "viewCount": {viewCount},
    "contacts": {contacts}
  }
}

```
</td>
<td>Отправить POST запрос на <a href="https://qa-internship.avito.com/api/1/item">https://qa-internship.avito.com/api/1/item</a></td>
<td>Запрос успешно отправлен на сервер (запрос успешно выполнен)</td>
</tr>
<tr>
<td>Проверить код состояния</td>
<td>HTTP Status: 400 Bad Request</td>
</tr>
<tr>
<td>Проверить тело ответа от сервера</td>
<td>Объявление не было сохранено.<br>Происходит формирование ответа.<br>Тело ответа в формате JSON возвращается от сервера и будет иметь следующий вид:<br>

```json

{
  "result": {
    "message": "поле {field} обязательно",
    "messages": {}
  },
  "status": "400"
}

```
</td>
</tr>
<tr>
<td rowspan="3">TR003</td>
<td rowspan="3">Высокий</td>
<td rowspan="3">Успешное получение данных объявления по id</td>
<td rowspan="3">Тест проверяет работу API:<br>GET <a href="https://qa-internship.avito.com/api/1/item/{id}">https://qa-internship.avito.com/api/1/item/{id}</a><br>при валидном параметре запроса <code>id = {id}</code>.</td>
<td rowspan="3">Пользователь с <code>sellerID = {sellerID}</code> создан заранее.<br>Объявление с <code>id = {id}</code> создано заранее.</td>
<td rowspan="3">Параметр запроса <code>id = {id}</code></td>
<td>Отправить GET запрос на <a href="https://qa-internship.avito.com/api/1/item/{id}">https://qa-internship.avito.com/api/1/item/{id}</a></td>
<td>Запрос успешно отправлен на сервер (запрос успешно выполнен)</td>
</tr>
<tr>
<td>Проверить код состояния</td>
<td>HTTP Status: 200 OK</td>
</tr>
<tr>
<td>Проверить тело ответа от сервера</td>
<td>Объявление было успешно получено.<br>Происходит формирование ответа.<br>Тело ответа в формате JSON возвращается от сервера и будет иметь следующий вид:<br>

```json

[
  {
    "createdAt": "2025-11-14 16:47:15.91926 +0300 +0300",
    "id": "20084839-8520-4704-92e4-14d07cda6ffe",
    "name": "test",
    "price": 1,
    "sellerId": 123321,
    "statistics": {
      "contacts": 1,
      "likes": 1,
      "viewCount": 1
    }
  }
]

```
</td>
</tr>
<tr>
<td rowspan="3">TR004</td>
<td rowspan="3">Высокий</td>
<td rowspan="3">Неуспешное получение данных объявления при невалидном id объявления</td>
<td rowspan="3">Тест проверяет работу API:<br>GET <a href="https://qa-internship.avito.com/api/1/item/{id}">https://qa-internship.avito.com/api/1/item/{id}</a><br>при невалидном параметре запроса <code>id = {id}</code>.</td>
<td rowspan="3">Пользователь с <code>sellerID = {sellerID}</code> создан заранее.<br>Объявление с <code>id = {id}</code> не существует.</td>
<td rowspan="3">Параметр запроса <code>id = {id}</code></td>
<td>Отправить GET запрос на <a href="https://qa-internship.avito.com/api/1/item/{id}">https://qa-internship.avito.com/api/1/item/{id}</a></td>
<td>Запрос успешно отправлен на сервер (запрос успешно выполнен)</td>
</tr>
<tr>
<td>Проверить код состояния</td>
<td>HTTP Status: 400 Bad Request</td>
</tr>
<tr>
<td>Проверить тело ответа от сервера</td>
<td>Пользователь не получил данные объявления.<br>Происходит формирование ответа.<br>Тело ответа в формате JSON возвращается от сервера и будет иметь следующий вид:<br>

```json

{
  "result": {
    "message": "ID айтема не UUID: {id}",
    "messages": {}
  },
  "status": "400"
}

```
</td>
</tr>
<tr>
<td rowspan="3">TR005</td>
<td rowspan="3">Высокий</td>
<td rowspan="3">Неуспешное получение данных объявления при валидном id объявления (не найдено)</td>
<td rowspan="3">Тест проверяет работу API:<br>GET <a href="https://qa-internship.avito.com/api/1/item/{id}">https://qa-internship.avito.com/api/1/item/{id}</a><br>при валидном, но несуществующем <code>id = {id}</code>.</td>
<td rowspan="3">Пользователь с <code>sellerID = {sellerID}</code> создан заранее.<br>Объявление с <code>id = {id}</code> не существует.</td>
<td rowspan="3">Параметр запроса <code>id = {id}</code></td>
<td>Отправить GET запрос на <a href="https://qa-internship.avito.com/api/1/item/{id}">https://qa-internship.avito.com/api/1/item/{id}</a></td>
<td>Запрос успешно отправлен на сервер (запрос успешно выполнен)</td>
</tr>
<tr>
<td>Проверить код состояния</td>
<td>HTTP Status: 404 Not Found</td>
</tr>
<tr>
<td>Проверить тело ответа от сервера</td>
<td>Пользователь не получил данные объявления.<br>Происходит формирование ответа.<br>Тело ответа в формате JSON возвращается от сервера и будет иметь следующий вид:<br>

```json

{
  "result": {
    "message": "item {id} not found",
    "messages": null
  },
  "status": "404"
}

```

</td>
</tr>
<tr>
<td rowspan="3">TR006</td>
<td rowspan="3">Высокий</td>
<td rowspan="3">Успешное получение всех объявлений пользователя</td>
<td rowspan="3">Тест проверяет работу API:<br>GET <a href="https://qa-internship.avito.com/api/1/{sellerID}/item">https://qa-internship.avito.com/api/1/{sellerID}/item</a><br>при валидном параметре запроса <code>sellerID = {sellerID}</code>.</td>
<td rowspan="3">Пользователь с <code>sellerID = {sellerID}</code> создан заранее.<br>Создано <code>{count}</code> объявлений для пользователя с <code>sellerID = {sellerID}</code>.</td>
<td rowspan="3">Параметр запроса <code>sellerID = {sellerID}</code></td>
<td>Отправить GET запрос на <a href="https://qa-internship.avito.com/api/1/{sellerID}/item">https://qa-internship.avito.com/api/1/{sellerID}/item</a></td>
<td>Запрос успешно отправлен на сервер (запрос успешно выполнен)</td>
</tr>
<tr>
<td>Проверить код состояния</td>
<td>HTTP Status: 200 OK</td>
</tr>
<tr>
<td>Проверить тело ответа от сервера</td>
<td>Объявления были успешно получены.<br>Происходит формирование ответа.<br>Тело ответа в формате JSON возвращается от сервера и будет иметь следующий вид:<br>

```json

[
  {
    "createdAt": "2025-11-15 13:31:23.581565 +0300 +0300",
    "id": "d2e7634c-8f90-4449-8e18-057452cb25db",
    "name": "delete1",
    "price": 1,
    "sellerId": 111198,
    "statistics": {
      "contacts": 1,
      "likes": 1,
      "viewCount": 1
    }
  },
  {
    "createdAt": "2025-11-15 13:31:25.730113 +0300 +0300",
    "id": "020696da-f22e-4244-8f1d-4adeade082ff",
    "name": "delete2",
    "price": 1,
    "sellerId": 111198,
    "statistics": {
      "contacts": 1,
      "likes": 1,
      "viewCount": 1
    }
  }
]

```
</td>
</tr>
<tr>
<td rowspan="3">TR007</td>
<td rowspan="3">Высокий</td>
<td rowspan="3">Неуспешное получение всех объявлений пользователя (некорректный sellerID)</td>
<td rowspan="3">Тест проверяет работу API:<br>GET <a href="https://qa-internship.avito.com/api/1/{sellerID}/item">https://qa-internship.avito.com/api/1/{sellerID}/item</a><br>при некорректном параметре запроса <code>sellerID = {sellerID}</code>.</td>
<td rowspan="3">Пользователь с <code>sellerID = {sellerID}</code> создан заранее.</td>
<td rowspan="3">Параметр запроса <code>sellerID = {sellerID}</code></td>
<td>Отправить GET запрос на <a href="https://qa-internship.avito.com/api/1/{sellerID}/item">https://qa-internship.avito.com/api/1/{sellerID}/item</a></td>
<td>Запрос успешно отправлен на сервер (запрос успешно выполнен)</td>
</tr>
<tr>
<td>Проверить код состояния</td>
<td>HTTP Status: 400 Bad Request</td>
</tr>
<tr>
<td>Проверить тело ответа от сервера</td>
<td>Пользователь не получил данные объявлений.<br>Происходит формирование ответа.<br>Тело ответа в формате JSON возвращается от сервера и будет иметь следующий вид:<br>

```json

{
  "result": {
    "message": "передан некорректный идентификатор продавца",
    "messages": {}
  },
  "status": "400"
}

```
</td>
</tr>
<tr>
<td rowspan="3">TR008</td>
<td rowspan="3">Высокий</td>
<td rowspan="3">Успешное получение статистики объявления по id</td>
<td rowspan="3">Тест проверяет работу API:<br>GET <a href="https://qa-internship.avito.com/api/1/statistic/{id}">https://qa-internship.avito.com/api/1/statistic/{id}</a><br>при валидном параметре запроса <code>id = {id}</code>.</td>
<td rowspan="3">Пользователь с <code>sellerID = {sellerID}</code> создан заранее.<br>Объявление с <code>id = {id}</code> создано заранее.</td>
<td rowspan="3">Параметр запроса <code>id = {id}</code></td>
<td>Отправить GET запрос на <a href="https://qa-internship.avito.com/api/1/statistic/{id}">https://qa-internship.avito.com/api/1/statistic/{id}</a></td>
<td>Запрос успешно отправлен на сервер (запрос успешно выполнен)</td>
</tr>
<tr>
<td>Проверить код состояния</td>
<td>HTTP Status: 200 OK</td>
</tr>
<tr>
<td>Проверить тело ответа от сервера</td>
<td>Статистика была успешно получена.<br>Происходит формирование ответа.<br>Тело ответа в формате JSON возвращается от сервера и будет иметь следующий вид:<br>

```json

[
  {
    "contacts": 1,
    "likes": 1,
    "viewCount": 1
  }
]

```

</td>
</tr>
<tr>
<td rowspan="3">TR009</td>
<td rowspan="3">Высокий</td>
<td rowspan="3">Неуспешное получение статистики объявления при невалидном id</td>
<td rowspan="3">Тест проверяет работу API:<br>GET <a href="https://qa-internship.avito.com/api/1/statistic/{id}">https://qa-internship.avito.com/api/1/statistic/{id}</a><br>при невалидном параметре запроса <code>id = {id}</code>.</td>
<td rowspan="3">Пользователь с <code>sellerID = {sellerID}</code> создан заранее.</td>
<td rowspan="3">Параметр запроса <code>id = {id}</code></td>
<td>Отправить GET запрос на <a href="https://qa-internship.avito.com/api/1/statistic/{id}">https://qa-internship.avito.com/api/1/statistic/{id}</a></td>
<td>Запрос успешно отправлен на сервер (запрос успешно выполнен)</td>
</tr>
<tr>
<td>Проверить код состояния</td>
<td>HTTP Status: 400 Bad Request</td>
</tr>
<tr>
<td>Проверить тело ответа от сервера</td>
<td>Статистика не была получена.<br>Происходит формирование ответа.<br>Тело ответа в формате JSON возвращается от сервера и будет иметь следующий вид:<br>

```json
{
  "result": {
    "message": "передан некорректный идентификатор объявления",
    "messages": {}
  },
  "status": "400"
}
```

</td>
</tr>
<tr>
<td rowspan="3">TR010</td>
<td rowspan="3">Высокий</td>
<td rowspan="3">Неуспешное получение статистики объявления при валидном id</td>
<td rowspan="3">Тест проверяет работу API:<br>GET <a href="https://qa-internship.avito.com/api/1/statistic/{id}">https://qa-internship.avito.com/api/1/statistic/{id}</a><br>при валидном, но несуществующем <code>id = {id}</code>.</td>
<td rowspan="3">Пользователь с <code>sellerID = {sellerID}</code> создан заранее.<br>Объявление с <code>id = {id}</code> не существует.</td>
<td rowspan="3">Параметр запроса <code>id = {id}</code></td>
<td>Отправить GET запрос на <a href="https://qa-internship.avito.com/api/1/statistic/{id}">https://qa-internship.avito.com/api/1/statistic/{id}</a></td>
<td>Запрос успешно отправлен на сервер (запрос успешно выполнен)</td>
</tr>
<tr>
<td>Проверить код состояния</td>
<td>HTTP Status: 404 Not Found</td>
</tr>
<tr>
<td>Проверить тело ответа от сервера</td>
<td>Статистика не была получена.<br>Происходит формирование ответа.<br>Тело ответа в формате JSON возвращается от сервера и будет иметь следующий вид:<br>

```json
{
  "result": {
    "message": "statistic {id} not found",
    "messages": null
  },
  "status": "404"
}
```

</td>
</tr>
</tbody>
</table>