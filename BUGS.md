# Задание 2.1

## Баг-репорты

<table>
<thead>
<tr>
<td>Идентификатор</td>
<td>Описание</td>
<td>Шаги воспроизведения</td>
<td>Фактический результат</td>
<td>Ожидаемый результат</td>
<td>Приоритет</td>
<td>Серьёзность</td>
<td>Комментарий</td>
</tr>
</thead>
<tbody>
<tr>
<td>BA001</td>
<td>Возможность создать объявления для sellerID < 0</td>
<td>

1. Сформировать тело запроса следующего вида:

```json
{
  "sellerID": -1,
  "name": "name",
  "price": 1,
  "statistics": {
    "likes": 1,
    "viewCount": 1,
    "contacts": 1
  }
}
```

2. Отправить POST запрос на https://qa-internship.avito.com/api/1/item
</td>
<td>

Статус код - 200 OK<br>

```json
{
    "status": "Сохранили объявление - 20084839-8520-4704-92e4-14d07cda6ffe"
}
```

</td>
<td>

Статус код - 400 BAD_REQUEST<br>

```json
{
    "result": {
        "message": "поле {sellerID} должно быть >= 0",
        "messages": {}
    },
    "status": "400"
}
```
</td>
<td>Высокий</td>
<td>Серьёзный</td>
<td>Должна происходит валидация поля sellerID >= 0</td>
</tr>

<tr>
<td>BA002</td>
<td>Возможность создания объявления с price <= 0</td>
<td>

1. Сформировать тело запроса следующего вида:

```json
{
  "sellerID": 123456789,
  "name": "name",
  "price": 0,
  "statistics": {
    "likes": 1,
    "viewCount": 1,
    "contacts": 1
  }
}
```

2. Отправить POST запрос на https://qa-internship.avito.com/api/1/item

</td>
<td>

Статус код - 200 OK<br>

```json
{
    "status": "Сохранили объявление - 20084839-8520-4704-92e4-14d07cda6ffe"
}
```

</td>

<td>


Статус код - 400 BAD_REQUEST<br>

```json
{
    "result": {
        "message": "поле price должно быть > 0",
        "messages": {}
    },
    "status": "400"
}
```

</td>
<td>Высокий</td>
<td>Серьёзный</td>
<td></td>
</tr>

<tr>
<td>BA003</td>
<td>Возможность создания объявления с likes < 0</td>
<td>

1. Сформировать тело запроса следующего вида:

```json
{
  "sellerID": 123456789,
  "name": "name",
  "price": 1,
  "statistics": {
    "likes": -1,
    "viewCount": 1,
    "contacts": 1
  }
}
```

2. Отправить POST запрос на https://qa-internship.avito.com/api/1/item

</td>
<td>

Статус код - 200 OK<br>

```json
{
    "status": "Сохранили объявление - 20084839-8520-4704-92e4-14d07cda6ffe"
}
```

</td>

<td>

Статус код - 400 BAD_REQUEST<br>

```json
{
    "result": {
        "message": "поле likes должно быть >= 0",
        "messages": {}
    },
    "status": "400"
}
```
</td>
<td>Высокий</td>
<td>Серьёзный</td>
<td>По идее поле likes должно быть 0 при создании нового объявления, а для изменения его значения должен быть другой эндпоинт</td>
</tr>

<tr>
<td>BA004</td>
<td>Невозможно создать объявление с likes = 0</td>
<td>

1. Сформировать тело запроса следующего вида:

```json
{
  "sellerID": 123456789,
  "name": "name",
  "price": 1,
  "statistics": {
    "likes": 0,
    "viewCount": 1,
    "contacts": 1
  }
}
```

2. Отправить POST запрос на https://qa-internship.avito.com/api/1/item

</td>
<td>

Статус код - 400 BAD_REQUEST<br>

```json
{
    "result": {
        "message": "поле likes обязательно",
        "messages": {}
    },
    "status": "400"
}
```

</td>

<td>

Статус код - 200 OK<br>

```json
{
  "status": "Сохранили объявление - 20084839-8520-4704-92e4-14d07cda6ffe"
}
```

</td>
<td>Высокий</td>
<td>Серьёзный</td>
<td></td>
</tr>

<tr>
<td>BA005</td>
<td>Возможность создать объявление с viewCount < 0</td>
<td>

1. Сформировать тело запроса следующего вида:

```json
{
  "sellerID": 123456789,
  "name": "name",
  "price": 1,
  "statistics": {
    "likes": 1,
    "viewCount": -1,
    "contacts": 1
  }
}
```

2. Отправить POST запрос на https://qa-internship.avito.com/api/1/item

</td>
<td>

Статус код - 200 OK<br>

```json
{
  "status": "Сохранили объявление - 20084839-8520-4704-92e4-14d07cda6ffe"
}
```

</td>

<td>

Статус код - 400 BAD_REQUEST<br>

```json
{
    "result": {
        "message": "поле viewCount должно быть >= 0",
        "messages": {}
    },
    "status": "400"
}
```

</td>
<td>Высокий</td>
<td>Серьёзный</td>
<td></td>
</tr>

<tr>
<td>BA006</td>
<td>Невозможно создать объявление с viewCount = 0</td>
<td>

1. Сформировать тело запроса следующего вида:

```json
{
  "sellerID": 123456789,
  "name": "name",
  "price": 1,
  "statistics": {
    "likes": 1,
    "viewCount": 0,
    "contacts": 1
  }
}
```

2. Отправить POST запрос на https://qa-internship.avito.com/api/1/item

</td>
<td>

Статус код - 400 BAD_REQUEST<br>

```json
{
    "result": {
        "message": "поле viewCount обязательно",
        "messages": {}
    },
    "status": "400"
}
```

</td>

<td>

Статус код - 200 OK<br>

```json
{
  "status": "Сохранили объявление - 20084839-8520-4704-92e4-14d07cda6ffe"
}
```

</td>
<td>Высокий</td>
<td>Серьёзный</td>
<td></td>
</tr>

<tr>
<td>BA007</td>
<td>Возможность создать объявление с contacts < 0</td>
<td>

1. Сформировать тело запроса следующего вида:

```json
{
  "sellerID": 123456789,
  "name": "name",
  "price": 1,
  "statistics": {
    "likes": 1,
    "viewCount": 1,
    "contacts": -1
  }
}
```

2. Отправить POST запрос на https://qa-internship.avito.com/api/1/item

</td>
<td>

Статус код - 200 OK<br>

```json
{
  "status": "Сохранили объявление - 20084839-8520-4704-92e4-14d07cda6ffe"
}
```

</td>

<td>

Статус код - 400 BAD_REQUEST<br>

```json
{
    "result": {
        "message": "поле viewCount должно быть >= 0",
        "messages": {}
    },
    "status": "400"
}
```

</td>
<td>Высокий</td>
<td>Серьёзный</td>
<td></td>
</tr>

<tr>
<td>BA008</td>
<td>Невозможно создать объявление с contacts = 0</td>
<td>

1. Сформировать тело запроса следующего вида:

```json
{
  "sellerID": 123456789,
  "name": "name",
  "price": 1,
  "statistics": {
    "likes": 1,
    "viewCount": 1,
    "contacts": 0
  }
}
```

2. Отправить POST запрос на https://qa-internship.avito.com/api/1/item

</td>
<td>

Статус код - 400 BAD_REQUEST<br>

```json
{
    "result": {
        "message": "поле contacts обязательно",
        "messages": {}
    },
    "status": "400"
}
```

</td>

<td>

Статус код - 200 OK<br>

```json
{
  "status": "Сохранили объявление - 20084839-8520-4704-92e4-14d07cda6ffe"
}
```

</td>
<td>Высокий</td>
<td>Серьёзный</td>
<td></td>
</tr>

<tr>
<td>BA009</td>
<td>Некорректный формат json ответа от сервера при получении объявления по id</td>
<td>

1. Сформировать тело запроса следующего вида:

Тело запроса:

```json
    {
    "sellerID": 123321,
    "name": "test",
    "price": 1,
    "statistics": {
    "likes": 1,
    "viewCount": 1,
    "contacts": 1
    }
}
```

2. Отправить POST Отправить POST запрос на https://qa-internship.avito.com/api/1/item
3. Получить от сервера id объявления
4. Отправить GET запрос на https://qa-internship.avito.com/api/1/item/{id}, передав вместо id = id объявления
</td>
<td>

Статус код - 200 OK<br>

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

<td>

Статус код - 200 OK<br>

```json
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
```

</td>
<td>Высокий</td>
<td>Незначительный</td>
<td>При получении объявления по id должен возвращаться один объект, а не массив из одного объекта.</td>
</tr>

<tr>
<td>BA010</td>
<td>Несоответствие стандарту ISO-8601 при выводе времени создания объявления</td>
<td>

1. Сформировать тело запроса следующего вида:

Тело запроса:

```json
    {
    "sellerID": 123321,
    "name": "test",
    "price": 1,
    "statistics": {
    "likes": 1,
    "viewCount": 1,
    "contacts": 1
    }
}
```

2. Отправить POST Отправить POST запрос на https://qa-internship.avito.com/api/1/item
3. Получить от сервера id объявления
4. Отправить GET запрос на https://qa-internship.avito.com/api/1/item/{id}, передав вместо id = id объявления
</td>
<td>

Статус код - 200 OK<br>

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

<td>

Статус код - 200 OK<br>

```json
  {
    "createdAt": "2025-11-14 16:47:15.91926 +0300",
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
```

</td>
<td>Средний</td>
<td>Незначительный</td>
<td>Дублирование +300 (смещение)</td>
</tr>

<tr>
<td>BA011</td>
<td>Некорректное сообщение об ошибке</td>
<td>

1. Отправить GET запрос на https://qa-internship.avito.com/api/1/item/{id}, передав id некорректный UUID
<td>

Статус код - 400 BAD_REQUEST<br>

```json
{
  "result": {
    "message": "ID айтема не UUID: a644ac2-6536-4caf-b09d-f350c9695d23",
    "messages": {}
  },
  "status": "400"
}
```

</td>

<td>

Статус код - 400 BAD_REQUEST<br>

```json
{
  "result": {
    "message": "UUID айтема не ID: a644ac2-6536-4caf-b09d-f350c9695d23",
    "messages": {}
  },
  "status": "400"
}
```

</td>
<td>Низкий</td>
<td>Критичный</td>
<td>В параметры запроса необходимо передать невалидный UUID. Например: gggggggg-gggg-gggg-gggg-gggggggggggg.</td>
</tr>

<tr>
<td>BA012</td>
<td>Некорректный формат json ответа от сервера при получении статистики объявления по id</td>
<td>

1. Сформировать тело запроса следующего вида:

Тело запроса:

```json
    {
    "sellerID": 123321,
    "name": "test",
    "price": 1,
    "statistics": {
    "likes": 1,
    "viewCount": 1,
    "contacts": 1
    }
}
```

2. Отправить POST запрос на https://qa-internship.avito.com/api/1/item
3. Получить от сервера id объявления
4. Отправить GET запрос на https://qa-internship.avito.com/api/1/statistic/:id, подставив из п.3 id вместо :id
<td>

Статус код - 200 OK<br>

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

<td>

Статус код - 200 OK<br>

```json
  {
  "contacts": 1,
  "likes": 1,
  "viewCount": 1
}
```

</td>
<td>Высокий</td>
<td>Незначительный</td>
<td>Тоже самое в BA009</td>
</tr>

</tbody>
</table>