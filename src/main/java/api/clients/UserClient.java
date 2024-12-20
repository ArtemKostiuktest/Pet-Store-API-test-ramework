package api.clients;

import api.BaseClient;
import api.pojo.requests.CreateUpdateUserBuilder;
import api.pojo.responses.CreateUpdateDeleteUserResponse;
import api.pojo.responses.GetUserResponse;
import io.qameta.allure.Step;

public class UserClient extends BaseClient {

    public UserClient(String url) {
        super(url);
    }

    @Step("Create New User with username: '{createUpdateUserBuilder.username}'. Expected status code '{expectedStatusCode}'")
    public CreateUpdateDeleteUserResponse createNewUser(CreateUpdateUserBuilder createUpdateUserBuilder, int expectedStatusCode) {
        return sendPost("/user", createUpdateUserBuilder, expectedStatusCode, CreateUpdateDeleteUserResponse.class);
    }

    @Step("Get existing user with username: '{username}'. Expected status code '{expectedStatusCode}'")
    public GetUserResponse getUser(String username, int expectedStatusCode) {
        return sendGet("/user/" + username, expectedStatusCode, GetUserResponse.class);
    }

    @Step("Update existing user with username: '{username}'. Expected status code '{expectedStatusCode}'")
    public CreateUpdateDeleteUserResponse updateUser(CreateUpdateUserBuilder createUpdateUserBuilder, String username, int expectedStatusCode) {
        return sendPut("/user/" + username, createUpdateUserBuilder, expectedStatusCode, CreateUpdateDeleteUserResponse.class);
    }

    @Step("Delete existing user with username: '{username}'. Expected status code '{expectedStatusCode}'")
    public CreateUpdateDeleteUserResponse deleteUser(String username, int expectedStatusCode) {
        return sendDelete("/user/" + username, expectedStatusCode, CreateUpdateDeleteUserResponse.class);
    }
}
