# MstMarket
Приложение - продуктовый маркет.
При запуске приложения появляется главная страница магазина с приветствием.
На главной странцие есть несколько разделов, например, можно положить несколько
продуктов на выбор в корзину и указать их количество.
Заказ может сделать только авторизованный пользователь, в этом случае все
продукты из гостевой корзины перейдут в корзину авторизованного пользователя.
Можно просматривать список своих заказов.
Есть возможность зарегистрировать нового пользователя.
Также существует функционал для пользователей - админов.
Они могут редактировать список продуктов, удалять пользователей
и назначать роли.

Пользователи по умолчанию для тестирования приложения: 
1) логин: bob, пароль: 100 (обычный пользователь),
2) логин: john, пароль: 100 (пользователь - админ).

При первом запуске возможен баг - не отображается список продуктов, в этом
случае нажмите "Выйти" в табличке регистрации пользователя.

Запуск приложения:
1. git clone https://github.com/Kashevar128/MstMarket.git
2. cd MstMarket
3. mvn clean install
4. sudo docker-compose up -d
5. Откыть в браузере http://localhost:3000/market-front

Использованные технологии:

* Java
* Spring Boot
* Maven
* Spring Cloud Gateway
* Spring Web Servies
* Spring Security
* PostgreSQL
* Flyway Migration
* Redis
* Lombok
* Swagger
* Docker
* Spring Data JPA
* JUnit









