import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import pojo.Courier;
import io.restassured.response.Response;

public class CourierCreatingTests {
    private CourierClient courierClient;
    private String login;
    private String password;
    private String firstName;
    private String courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        courierClient = new CourierClient();
        login = RandomStringUtils.randomAlphanumeric(2, 15);
        password = RandomStringUtils.randomAlphanumeric(7, 15);
        firstName = RandomStringUtils.randomAlphabetic(2, 18);
    }

    @After
    public void tearDown() {
        if (courierId != null) {
            // Удаление курьера по его ID
            Response deleteResponse = RestAssured
                    .given()
                    .when()
                    .delete("/api/v1/courier/{id}", courierId);

            deleteResponse.then().log().all().assertThat().statusCode(200).and().body("ok", Matchers.is(true));
        }
    }

    @Test
    @DisplayName("Создание учетной записи курьера")
    @Description("Проверка состояния кода и значений для полей /api/v1/courier")
    public void createCourierTest() {
        Response postRequestCreateCourier = courierClient.getPostRequestCreateCourier(new Courier(login, password, firstName));
        postRequestCreateCourier.then().log().all().assertThat().statusCode(201).and().body("ok", Matchers.is(true));

        // Получение ID только что созданного курьера
        courierId = postRequestCreateCourier.jsonPath().get("id");
    }

    @Test
    @DisplayName("Создание курьера без имени курьера")
    @Description("Проверка состояния кода и сообщения при создании курьера без имени курьера")
    public void creatingCourierWithoutFirstName() {
        Response postRequestCreateCourier = courierClient.getPostRequestCreateCourier(new Courier(login, password));
        postRequestCreateCourier.then().log().all().assertThat().statusCode(201).and().body("ok", Matchers.is(true));

        // Получение ID только что созданного курьера
        courierId = postRequestCreateCourier.jsonPath().get("id");
    }

    @Test
    @DisplayName("Создание курьеров с одинаковыми логинами")
    @Description("Проверка состояния кода и сообщения при создании двух курьеров с одинаковыми логинами")
    public void creatingTwoIdenticalLoginCouriers() {
        String login = "ws8GU00Jtu0KN";
        String password = "3N69OLn";
        String firstName = "RFcELgDi";
        Response postRequestCreateCourier = courierClient.getPostRequestCreateCourier(new Courier(login, password, firstName));
        postRequestCreateCourier.then().log().all().assertThat().statusCode(409).and().body("message", Matchers.notNullValue());

        // Не устанавливаем courierId, так как курьер не создан
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка состояния кода и сообщения при создании курьера без логина")
    public void creatingCourierWithoutLogin() {
        String login = "";
        Response postRequestCreateCourier = courierClient.getPostRequestCreateCourier(new Courier(login, password, firstName));
        postRequestCreateCourier.then().log().all().assertThat().statusCode(400).and().body("message", Matchers.is("Недостаточно данных для создания учетной записи"));

        // Не устанавливаем courierId, так как курьер не создан
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка состояния кода и сообщения при создании курьера без пароля")
    public void creatingCourierWithoutPassword() {
        String password = "";
        Response postRequestCreateCourier = courierClient.getPostRequestCreateCourier(new Courier(login, password, firstName));
        postRequestCreateCourier.then().log().all().assertThat().statusCode(400).and().body("message", Matchers.is("Недостаточно данных для создания учетной записи"));

        // Не устанавливаем courierId, так как курьер не создан
    }

    @Test
    @DisplayName("Создание курьера без логина и пароля")
    @Description("Проверка состояния кода и сообщения при создании курьера без логина и пароля")
    public void creatingCourierWithoutLoginAndPassword() {
        String login = "";
        String password = "";
        Response postRequestCreateCourier = courierClient.getPostRequestCreateCourier(new Courier(login, password, firstName));
        postRequestCreateCourier.then().log().all().assertThat().statusCode(400).and().body("message", Matchers.is("Недостаточно данных для создания учетной записи"));

        // Не устанавливаем courierId, так как курьер не создан
    }
}