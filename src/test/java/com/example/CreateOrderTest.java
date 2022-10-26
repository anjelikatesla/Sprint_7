package com.example;

import com.example.api.client.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.notNullValue;

/**
 * Тест на ручку "Создать заказ"
 */
@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseTest {

    private final String[] color;
    private int track;

    private final OrderClient orderClient = new OrderClient();

    public CreateOrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][]{
                {new String[]{"BLACK", "GREY"}},
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{}}
        };
    }

    @After
    public void afterMethod() {
        orderClient.cancelOrder(track);
    }

    @Test
    @DisplayName("Check response when data is valid")
    public void testCheckResponseCreateOrderWithValidData() {
        Order order = new Order(color);
        order.setUpFieldsForRequest();
        Response response = orderClient.getResponseForOrders(order);
        response.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .and()
                .body("track", notNullValue());
        String responseString = response.asString();
        JsonPath jsonPath = new JsonPath(responseString);
        track = jsonPath.getInt("track");
    }
}