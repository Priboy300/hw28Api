package reqres;

import api.models.RegisterUserPayload;
import api.models.SingleUserResponse;
import api.models.UpdateSingleUser;
import api.steps.ReqresSteps;

import io.restassured.response.Response;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;


public class TestReqres {


    private final ReqresSteps reqres_steps = new ReqresSteps();

    @ParameterizedTest
    @ValueSource(strings = {"1", "2"})
    public void successCheckUserAvatar(String user) {
        SingleUserResponse response = reqres_steps.getSingleUserSuccess(user);
        reqres_steps.checkSingleUserAvatar(response);
    }

    @ParameterizedTest
    @ValueSource(strings = {"55", "44"})
    public void unsuccessCheckUserBody(String user) {
        Response response = reqres_steps.getSingleUserUnsuccess(user);
        reqres_steps.checkSingleUserBody(response);
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/test-data/register-user-data.csv", numLinesToSkip = 1)
    public void successRegisterUser(String email, String password) {
        RegisterUserPayload payload = new RegisterUserPayload(email, password);
        Response response = reqres_steps.postUserSuccess(payload);
        reqres_steps.checkRegisterUser(response);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/test-data/unregister-user-data.csv", numLinesToSkip = 1)
    public void unsuccessRegisterUser(String email, String password) {
        RegisterUserPayload payload = new RegisterUserPayload(email, password);
        Response response = reqres_steps.postUserUnSuccess(payload);
        reqres_steps.checkUnregisterUser(response);
    }


    @ParameterizedTest
    @ValueSource(strings = {"1", "2"})
    public void successDeleteUser(String user) {
        Response response = reqres_steps.deleteUserSuccess(user);
    }

    @ParameterizedTest
    @ValueSource(strings = {"ddd", "dddd"})
    public void unsuccessDeleteUser(String user) {
        Response response = reqres_steps.deleteUserUnsuccess(user);
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/test-data/update-user-data.csv", numLinesToSkip = 1)
    public void successUpdateUser(String id, String name, String job) {
        UpdateSingleUser payload = new UpdateSingleUser(name, job);
        Response response = reqres_steps.putUpdateSuccess(id, payload);
        reqres_steps.checkUpdateUser(response, payload);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/test-data/not-update-user-data.csv", numLinesToSkip = 1)
    public void unsuccessUpdateUser(String id, String name, String job) {
        UpdateSingleUser payload = new UpdateSingleUser(name, job);
        Response response = reqres_steps.putUpdateUnsuccess(id, payload);
        reqres_steps.checkNotUpdateUser(response, payload);
    }
}
