package com.example;

import com.example.api.client.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Тест на ручку "Логин курьера"
 */
public class CourierLoginTest extends BaseTest {

    private Courier courier;

    private final CourierClient courierClient = new CourierClient();

    @Before
    public void setUp() {
        courier = courierClient.registerCourier();
    }

    @After
    public void afterMethod() {
        Response response = courierClient.getResponseForRegisterRequest(courier);
        JsonPath jsonPath = new JsonPath(response.asString());
        String userId = jsonPath.getString("id");
        courierClient.deleteCourier(userId);
    }

    @Test
    @DisplayName("Check response for correct login and password")
    public void testResponseForCorrectLoginData() {
        Response response = courierClient.getResponseForLoginRequest(courier);
        response.then().assertThat().statusCode(HttpURLConnection.HTTP_OK).and().assertThat().body("id", notNullValue());
    }

    @Test
    @DisplayName("Check response for incorrect login")
    public void testResponseForIncorrectLogin() {
        Response response = courierClient.getResponseForLoginRequest(new Courier("incorrectLogin", courier.getPassword()));
        response.then().assertThat().statusCode(HttpURLConnection.HTTP_NOT_FOUND).and().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Check response for incorrect password")
    public void testResponseForIncorrectPassword() {
        Response response = courierClient.getResponseForLoginRequest(new Courier(courier.getLogin(), "incorrectPassword"));
        response.then().assertThat().statusCode(HttpURLConnection.HTTP_NOT_FOUND).and().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Check response for incorrect login and password")
    public void testResponseForNonExistsCourier() {
        String randomWord = RandomStringUtils.randomAlphabetic(10);
        Courier courier = new Courier(randomWord, randomWord);
        Response response = courierClient.getResponseForLoginRequest(courier);
        response.then().assertThat().statusCode(HttpURLConnection.HTTP_NOT_FOUND).and().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Check response for login without login field")
    public void testResponseForAuthWithoutLoginField() {
        String password = "pass";
        String registerBody = "{\"password\":\"" + password + "\"}";
        Response response = courierClient.getResponseForLoginWithCustomRequest(registerBody);
        response.then().assertThat().statusCode(HttpURLConnection.HTTP_BAD_REQUEST).and().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }
}