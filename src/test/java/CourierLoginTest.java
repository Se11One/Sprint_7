import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import pojo.Courier;

public class CourierLoginTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Курьер авторизирован")
    @Description("Проверка авторизации курьера с корректным логином и паролем")
    public void checkCreatingCourierLoginTest() {
        CourierClient courierClient = new CourierClient();
        String login = "ws8GU00Jtu0KN";
        String password = "3N69OLn";
        String firstName = "RFcELgDi";
        Response postRequestCourierLogin = courierClient.getPostRequestCourierLogin(new Courier(login, password, firstName));
        postRequestCourierLogin.then().log().all().assertThat().statusCode(200).and().body("id", Matchers.notNullValue());
    }

    @Test
    @DisplayName("Курьер авторизирован без логина")
    @Description("Проверка авторизации курьера без ввода логина")
    public void checkVerificationWithoutLoginAuthorization() {
        CourierClient courierClient = new CourierClient();
        String login = "";
        String password = "3N69OLn";
        String firstName = "RFcELgDi";
        Response postRequestCourierLogin = courierClient.getPostRequestCourierLogin(new Courier(login, password, firstName));
        postRequestCourierLogin.then().log().all().assertThat().statusCode(400).and().body("message", Matchers.is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Курьер авторизирован без пароля")
    @Description("Проверка авторизации курьера без ввода пароля")
    public void checkVerificationWithoutPasswordAuthorization() {
        CourierClient courierClient = new CourierClient();
        String login = "ws8GU00Jtu0KN";
        String password = "";
        String firstName = "RFcELgDi";
        Response postRequestCourierLogin = courierClient.getPostRequestCourierLogin(new Courier(login, password, firstName));
        postRequestCourierLogin.then().log().all().assertThat().statusCode(400).and().body("message", Matchers.is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Курьер авторизирован под несуществующим логином")
    @Description("Проверка авторизации курьера в системе под несуществующим пользователем")
    public void checkAuthorizationUnderIncorrectLogin() {
        CourierClient courierClient = new CourierClient();
        String login = "А а";
        String password = "А а";
        String firstName = "Спэйгла спэйгла";
        Response postRequestCourierLogin = courierClient.getPostRequestCourierLogin(new Courier(login, password, firstName));
        postRequestCourierLogin.then().log().all().assertThat().statusCode(404).and().body("message", Matchers.is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Курьер авторизирован под некорректным логином")
    @Description("Проверка авторизации курьера в системе, если неправильно указать логин")
    public void checkEnteringInvalidLogin() {
        CourierClient courierClient = new CourierClient();
        String login = "Ws8GU00Jtu0Kn";
        String password = "3N69OLn";
        String firstName = "RFcELgDi";
        Response postRequestCourierLogin = courierClient.getPostRequestCourierLogin(new Courier(login, password, firstName));
        postRequestCourierLogin.then().log().all().assertThat().statusCode(404).and().body("message", Matchers.is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Курьер авторизирован под некорректным паролем")
    @Description("Проверка авторизации курьера в системе, если неправильно указать пароль")
    public void checkEnteringInvalidPassword() {
        CourierClient courierClient = new CourierClient();
        String login = "ws8GU00Jtu0KN";
        String password = "qwerty123";
        String firstName = "RFcELgDi";
        Response postRequestCourierLogin = courierClient.getPostRequestCourierLogin(new Courier(login, password, firstName));
        postRequestCourierLogin.then().log().all().assertThat().statusCode(404).and().body("message", Matchers.is("Учетная запись не найдена"));
    }
}