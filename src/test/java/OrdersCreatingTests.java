import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.Orders;

import static io.restassured.RestAssured.given;


@RunWith(Parameterized.class)
public class OrdersCreatingTests {


    private final Orders orders;

    public OrdersCreatingTests(Orders orders) {
        this.orders = orders;
    }


    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}")
    public static Object[][] getTestData() {
        return new Object[][]{{new Orders("Питер", "Паркер", "Челси-стрит", "Статен-Айленд", "123456789", 2, "2024-08-10", "Сори за паутину", new String[]{"BLACK"})}, {new Orders("Майлз", "Моралез", "земля-42", "Бруклин стейт", "987654321", 7, "2024-09-10", "Побыстрее!!", new String[]{"GREY"})}, {new Orders("Гвен", "Стейси", "Нью-Джерси", "Гудзон-Каунти", "0101010101", 7, "2024-08-10", "", new String[]{"BLACK", "GREY"})}, {new Orders("Анатоний", "Старков", "Манхэттен", "890 Пятая авеню", "8005353535", 1, "2024-08-10", "Паучек, твой выход", new String[]{})},};
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказа с различными данными")
    public void checkCreateOrder() {
        Response response = given().log().all().header("Content-type", "application/json").body(orders).when().post("/api/v1/orders");
        response.then().log().all().assertThat().and().statusCode(201).body("track", Matchers.notNullValue());
    }
}

