import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import pojo.CourierClient;
import specification.Specs;

public class CourierCreatingTests {

    @BeforeClass
    public static void setUp() {
        Specs.instalSpec(Specs.requestSpec("https://qa-scooter.praktikum-services.ru", 443, "/api/v1"), Specs.responseSpec());
    }

    @Test
    @DisplayName("Создание курьера") // имя теста
    @Description("Создание нового курьера с заполнением параметров: login, password, firstName") // описание теста
    public void createCourierTest(){
        CourierClient courierClient = new CourierClient();
        courierClient.setLogin(RandomStringUtils.randomAlphanumeric(2,15));
        courierClient.setFirstName(RandomStringUtils.randomAlphabetic(2,18));
        courierClient.setPassword(RandomStringUtils.randomAlphanumeric(7,15));

        RestAssured
                .given()
                .when()
                    .body(courierClient)
                    .post("/courier")
                .then()
                    .assertThat().statusCode(201).and().body("ok", Matchers.is(true));
    }
    @Test
    @DisplayName("Создание курьера с пустым значением параметра firstName") // имя теста
    @Description("Проверка состояния кода и сообщения при создании курьера с пустым значением параметра firstName") // описание теста
    public void creatingCourierWithoutFirstName(){
        CourierClient courierClient = new CourierClient();
        courierClient.setLogin(RandomStringUtils.randomAlphanumeric(2,15));
        courierClient.setPassword(RandomStringUtils.randomAlphanumeric(7,15));

        RestAssured
                .given()
                .when()
                    .body(courierClient)
                    .post("/courier")
                .then()
                    .assertThat().statusCode(201).and().body("ok", Matchers.is(true));
    }
    @Test
    @DisplayName("Создание курьеров с одинаковыми значениями login")
    @Description("Проверка состояния кода и сообщения при создании двух курьеров с одинаковыми значениями login")
    public void creatingTwoIdenticalLoginCouriers() {
        CourierClient courierClient = new CourierClient();
        courierClient.setLogin("qwerty");
        courierClient.setFirstName("Qwerty");
        courierClient.setPassword("qwerty123");

        RestAssured
                .given()
                .when()
                    .body(courierClient)
                    .post("/courier")
                .then()
                    .assertThat().statusCode(409).and().body("message", Matchers.equalTo("Этот логин уже используется. Попробуйте другой."));
    }
}
