package com.example;

import com.example.api.client.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.equalTo;

/**
 * Тест на ручку "Создание курьера"
 */
public class RegisterCourierTest extends BaseTest {
    final private String login = RandomStringUtils.randomAlphabetic(10);
    final private String password = RandomStringUtils.randomAlphabetic(10);
    final private String firstName = RandomStringUtils.randomAlphabetic(10);

    private final CourierClient courierClient = new CourierClient();

    @After
    public void afterMethod() {
        Courier courier = new Courier(login, password);
        Response response = courierClient.getResponseForRegisterRequest(courier);
        JsonPath jsonPath = new JsonPath(response.asString());
        String userId = jsonPath.getString("id");
        courierClient.deleteCourier(userId);
    }

    @Test
    @DisplayName("Check response for create courier with valid data")
    public void testCreateCourierWithValidData() {
        Courier courier = new Courier(login, password, firstName);
        Response response = courierClient.getResponseForRegisterRequest(courier);
        response.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .and()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Check response for create courier duplicate")
    public void testCreateDuplicateCourier() {
        Courier courier = new Courier(login, password, firstName);
        // Первая регистрация курьера
        Response firstRegistrationResponse = courierClient.getResponseForRegisterRequest(courier);
        firstRegistrationResponse.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .and()
                .body("ok", equalTo(true));
        // Повторная регистрация курьера с теми же данными
        Response secondRegistrationResponse = courierClient.getResponseForRegisterRequest(courier);
        secondRegistrationResponse.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Check response for login when password field is missing")
    public void testCreateCourierWithoutFillInPassword() {
        String registerBody = "{\"login\":\"" + login + "\","
                + "\"firstName\":\"" + firstName + "\"}";
        Response response = courierClient.getResponseForRegisterWithCustomBodyRequest(registerBody);
        response.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Check response for login when login field is missing")
    public void testCreateCourierWithoutFillInLogin() {
        String registerBody = "{\"password\":\"" + password + "\","
                + "\"firstName\":\"" + firstName + "\"}";
        Response response = courierClient.getResponseForRegisterWithCustomBodyRequest(registerBody);
        response.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

}