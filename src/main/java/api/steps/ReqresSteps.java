package api.steps;

import api.models.RegisterUserPayload;
import api.models.SingleUserResponse;
import api.models.UpdateSingleUser;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;
import static api.steps.Specification.config;

public class ReqresSteps {


    public static SingleUserResponse getSingleUserSuccess(String user) {

        return given()
                .spec(Specification.getRequestSpec())
                .when()
                .get(config.singleUserUrl(), user)
                .then()
                .spec(Specification.getResponseSpec(200))
                .extract().body().jsonPath().getObject(".", SingleUserResponse.class);

    }

    public void checkSingleUserAvatar(SingleUserResponse response) {
        String end_avatar = String.format("%s-image.jpg", response.getData().getId());
        Assertions.assertTrue(response.getData().getAvatar().endsWith(end_avatar),"Поле avatar содержит неверный id");
    }


    public static Response getSingleUserUnsuccess(String user) {

        return given()
                .spec(Specification.getRequestSpec())
                .when()
                .get(config.singleUserUrl(), user)
                .then()
                .spec(Specification.getResponseSpec(404))
                .extract().response();
    }

    public void checkSingleUserBody(Response response) {
        Assertions.assertEquals(response.getBody().asString(), "{}","Поле body несуществующего пользователя содержит информацию");
    }


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

    public void checkRegisterUser(Response response) {
        Assertions.assertNotEquals(response.jsonPath().get("id"), Integer.valueOf(0),"Id пользователя равен 0");
        Assertions.assertNotEquals(response.jsonPath().get("token"), " ", "Отсутствует token пользователя");
    }


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

    public void checkUnregisterUser(Response response) {
        Assertions.assertEquals(response.jsonPath().get("error"), "Note: Only defined users succeed registration", "Несуществующий пользователь был найден");
    }


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

    public void checkUpdateUser(Response response, UpdateSingleUser payload) {
        Assertions.assertEquals(payload.getJob(), response.jsonPath().get("job"), "Поле работа(job) не совпадает");
        Assertions.assertEquals(payload.getName(), response.jsonPath().get("name"), "Поле имя(name) не совпадает");
    }

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

    public void checkNotUpdateUser(Response response, UpdateSingleUser payload) {
        Assertions.assertEquals(payload.getJob(), response.jsonPath().get("job"), "Поле работа(job) не совпадает");
        Assertions.assertEquals(payload.getName(), response.jsonPath().get("name"), "Поле имя(name) не совпадает");
    }
}