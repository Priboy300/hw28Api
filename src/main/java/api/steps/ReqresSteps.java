package api.steps;

import api.models.RegisterUserPayload;
import api.models.SingleUserResponse;
import api.models.UpdateSingleUser;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;
import static api.steps.Specification.config;

public class ReqresSteps {

    @Step("Отправить запрос GET reqres.in/api/users/id с параметром id = {0}")
    public static SingleUserResponse getSingleUserSuccess(String user) {

        return given()
                .spec(Specification.getRequestSpec())
                .when()
                .get(config.singleUserUrl(), user)
                .then()
                .spec(Specification.getResponseSpec(200))
                .extract().body().jsonPath().getObject(".", SingleUserResponse.class);

    }
    @Step("Проверка того, что окончание поля avatar id-image.jpg включает в себя правильный id пользователя.")
    public void checkSingleUserAvatar(SingleUserResponse response) {
        String end_avatar = String.format("%s-image.jpg", response.getData().getId());
        Assertions.assertTrue(response.getData().getAvatar().endsWith(end_avatar),"Поле avatar содержит неверный id");
    }

    @Step("Отправить запрос GET reqres.in/api/users/id с параметром id = {0}")
    public static Response getSingleUserUnsuccess(String user) {

        return given()
                .spec(Specification.getRequestSpec())
                .when()
                .get(config.singleUserUrl(), user)
                .then()
                .spec(Specification.getResponseSpec(404))
                .extract().response();
    }
    @Step("Проверка того, что запрос пользователя с несуществующим id правильно обработался")
    public void checkSingleUserBody(Response response) {
        Assertions.assertEquals(response.getBody().asString(), "{}","Поле body несуществующего пользователя содержит информацию");
    }

    @Step("Отправить запрос POST reqres.in/api/register с параметром payload = {0}")
    public static Response postUserSuccess(RegisterUserPayload payload) {

        return given()
                .spec(Specification.getRequestSpec())
                .when()
                .body(payload)
                .post(config.registerUserUrl())
                .then()
                .spec(Specification.getResponseSpec(200))
                .extract()
                .response();
    }
    @Step("Проверка того, что зарегистрированному пользователю присвоились уникальные id и token")
    public void checkRegisterUser(Response response) {
        Assertions.assertNotEquals(response.jsonPath().get("id"), Integer.valueOf(0),"Id пользователя равен 0");
        Assertions.assertNotEquals(response.jsonPath().get("token"), " ", "Отсутствует token пользователя");
    }

    @Step("Отправить запрос POST reqres.in/api/register с параметром payload = {0}")
    public static Response postUserUnSuccess(RegisterUserPayload payload) {

        return given()
                .spec(Specification.getRequestSpec())
                .when()
                .body(payload)
                .post(config.registerUserUrl())
                .then()
                .spec(Specification.getResponseSpec(400))
                .extract()
                .response();
    }
    @Step("Проверка того, что вывилось корректное сообщение об отказе в регистрации пользователю с неккоректными данными")
    public void checkUnregisterUser(Response response) {
        Assertions.assertEquals(response.jsonPath().get("error"), "Note: Only defined users succeed registration", "Несуществующий пользователь был найден");
    }

    @Step("Отправить запрос DELETE reqres.in/api/users/id с параметром id = {0}")
    public static Response deleteUserSuccess(String user) {
        return given()
                .spec(Specification.getRequestSpec())
                .when()
                .delete(config.singleUserUrl(), user)
                .then()
                .spec(Specification.getResponseSpec(204))
                .extract()
                .response();
    }
    @Step("Отправить запрос DELETE reqres.in/api/users/id с параметром id = {0}")
    public static Response deleteUserUnsuccess(String user) {
        return given()
                .spec(Specification.getRequestSpec())
                .when()
                .delete(config.singleUserUrl(), user)
                .then()
                .spec(Specification.getResponseSpec(404))
                .extract()
                .response();
    }

    @Step("Отправить запрос PUT reqres.in/api/users/id с параметром id = {0}")
    public static Response putUpdateSuccess(String id, UpdateSingleUser payload) {

        return given()
                .spec(Specification.getRequestSpec())
                .when()
                .body(payload)
                .put(config.singleUserUrl(), id)
                .then()
                .spec(Specification.getResponseSpec(200))
                .extract()
                .response();
    }

    @Step("Проверка того, что информация о пользователе корректно обновились")
    public void checkUpdateUser(Response response, UpdateSingleUser payload) {
        Assertions.assertEquals(payload.getJob(), response.jsonPath().get("job"), "Поле работа(job) не совпадает");
        Assertions.assertEquals(payload.getName(), response.jsonPath().get("name"), "Поле имя(name) не совпадает");
    }

    @Step("Отправить запрос PUT reqres.in/api/users/id с параметром id = {0}")
    public static Response putUpdateUnsuccess(String id, UpdateSingleUser payload) {

        return given()
                .spec(Specification.getRequestSpec())
                .when()
                .body(payload)
                .put(config.singleUserUrl(), id)
                .then()
                .spec(Specification.getResponseSpec(400))
                .extract()
                .response();
    }

    @Step("Проверка того, что запрос на обновление несущестующего пользователя корректно обработался")
    public void checkNotUpdateUser(Response response, UpdateSingleUser payload) {
        Assertions.assertEquals(payload.getJob(), response.jsonPath().get("job"), "Поле работа(job) не совпадает");
        Assertions.assertEquals(payload.getName(), response.jsonPath().get("name"), "Поле имя(name) не совпадает");
    }
}