package com.example;

import com.example.api.client.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.notNullValue;

/**
 * Тест на ручку, которая получает список заказов
 */
public class GetOrderListTest extends BaseTest {

    @Test
    @DisplayName("Check response for get orders")
    public void testCheckResponseForGetOrderList() {
        OrderClient orderClient = new OrderClient();
        Response response = orderClient.getResponseForOrders();
        response.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and()
                .body("orders", notNullValue());
    }
}