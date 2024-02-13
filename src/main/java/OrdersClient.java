import io.restassured.response.Response;
import io.qameta.allure.Step;
import pojo.Orders;

import static io.restassured.RestAssured.given;

public class OrdersClient {
    private static final String ORDERS_ENDPOINT = "/api/v1/orders";

    @Step("Создание заказа")
    public Response createOrder(Orders orders) {
        return given().log().all().header("Content-type", "application/json").body(orders).when().post(ORDERS_ENDPOINT);
    }
}