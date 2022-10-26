package com.example.api.client;

import io.qameta.allure.Step;
import io.qameta.allure.internal.shadowed.jackson.annotation.JsonInclude;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient {

    private final static String ORDERS_PATH = "/api/v1/orders";

    private final static String ORDER_CANCEL = "/api/v1/orders/cancel";

    @Step("Get response for orders")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Response getResponseForOrders(Object body) {
        return given().header("Content-type", "application/json").and().body(body).when().post(ORDERS_PATH);
    }

    @Step("Get response for orders")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Response getResponseForOrders() {
        return given().header("Content-type", "application/json").and().when().get(ORDERS_PATH);
    }

    @Step("Cancel order")
    public void cancelOrder(int track) {
        String cancelBody = "{\"track\":" + track + "}";
        given()
                .body(cancelBody)
                .when()
                .put(ORDER_CANCEL);
    }
}
