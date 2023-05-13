package reqres;

import api.models.RegisterUserPayload;
import api.models.SingleUserResponse;
import api.models.UpdateSingleUser;
import api.steps.ReqresSteps;
import io.qameta.allure.*;

import io.restassured.response.Response;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

@Epic("Api tests")
@Feature("Reqres service")
@Story("Методы для работы с пользователем")
@Link(name = "Документация сервиса", url = "https://reqres.in/")
@Owner("Tomas T")
public class TestReqres {


    private final ReqresSteps reqres_steps = new ReqresSteps();

    @ParameterizedTest
    @Description("Проверяем, что поле avatar содержит верный id пользователя")
    @DisplayName("Проверка поля avatar")
    @Severity(SeverityLevel.NORMAL)
    @ValueSource(strings = {"1", "2"})
    public void successCheckUserAvatar(String user) {
        SingleUserResponse response = reqres_steps.getSingleUserSuccess(user);
        reqres_steps.checkSingleUserAvatar(response);
    }

    @ParameterizedTest
    @Description("Проверяем, что запрос информации о несуществущем пользователе правильно обработался")
    @DisplayName("Проверка несуществующего пользователя")
    @Severity(SeverityLevel.NORMAL)
    @ValueSource(strings = {"55", "44"})
    public void unsuccessCheckUserBody(String user) {
        Response response = reqres_steps.getSingleUserUnsuccess(user);
        reqres_steps.checkSingleUserBody(response);
    }


    @ParameterizedTest
    @Description("Проверяем, что пользователь с корректными данными зарегистрировался")
    @DisplayName("Регистрация при передаче корректных данных")
    @Severity(SeverityLevel.NORMAL)
    @CsvFileSource(resources = "/test-data/register-user-data.csv", numLinesToSkip = 1)
    public void successRegisterUser(String email, String password) {
        RegisterUserPayload payload = new RegisterUserPayload(email, password);
        Response response = reqres_steps.postUserSuccess(payload);
        reqres_steps.checkRegisterUser(response);
    }

    @ParameterizedTest
    @Description("Проверяем, что пользователь с некорректными данными не смог зарегистрироваться")
    @DisplayName("Отказ в регистрации при передаче некорректных данных")
    @Severity(SeverityLevel.NORMAL)
    @CsvFileSource(resources = "/test-data/unregister-user-data.csv", numLinesToSkip = 1)
    public void unsuccessRegisterUser(String email, String password) {
        RegisterUserPayload payload = new RegisterUserPayload(email, password);
        Response response = reqres_steps.postUserUnSuccess(payload);
        reqres_steps.checkUnregisterUser(response);
    }


    @ParameterizedTest
    @Description("Проверяем, что запрос на удаление существующего пользователя корректно обработался")
    @DisplayName("Удаление существующего пользователя")
    @Severity(SeverityLevel.NORMAL)
    @ValueSource(strings = {"1", "2"})
    public void successDeleteUser(String user) {
        Response response = reqres_steps.deleteUserSuccess(user);
    }

    @ParameterizedTest
    @Description("Проверяем, что запрос на удаление несуществующего пользователь корректно обработался")
    @DisplayName("Удаление несуществующего пользователя")
    @Severity(SeverityLevel.NORMAL)
    @ValueSource(strings = {"ddd", "dddd"})
    public void unsuccessDeleteUser(String user) {
        Response response = reqres_steps.deleteUserUnsuccess(user);
    }


    @ParameterizedTest
    @Description("Проверяем, что запрос на обновление информации существующего пользователь корректно обработался")
    @DisplayName("Обновление информации существующего пользователя")
    @Severity(SeverityLevel.NORMAL)
    @CsvFileSource(resources = "/test-data/update-user-data.csv", numLinesToSkip = 1)
    public void successUpdateUser(String id, String name, String job) {
        UpdateSingleUser payload = new UpdateSingleUser(name, job);
        Response response = reqres_steps.putUpdateSuccess(id, payload);
        reqres_steps.checkUpdateUser(response, payload);
    }

    @ParameterizedTest
    @Description("Проверяем, что запрос на обновление информации несуществующего пользователь корректно обработался")
    @DisplayName("Обновление информации несуществующего пользователя")
    @Severity(SeverityLevel.NORMAL)
    @CsvFileSource(resources = "/test-data/not-update-user-data.csv", numLinesToSkip = 1)
    public void unsuccessUpdateUser(String id, String name, String job) {
        UpdateSingleUser payload = new UpdateSingleUser(name, job);
        Response response = reqres_steps.putUpdateUnsuccess(id, payload);
        reqres_steps.checkNotUpdateUser(response, payload);
    }
}
