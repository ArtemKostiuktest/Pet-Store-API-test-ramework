package tests.usertests;

import api.clients.UserClient;
import api.pojo.requests.CreateUpdateUserBuilder;
import api.pojo.responses.CreateUpdateDeleteUserResponse;
import api.pojo.responses.GetUserResponse;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.TestInit;

import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;
import static listeners.TestListener.logger;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.AssertJUnit.assertEquals;
import static utils.DataGeneration.generateRandomUser;

public class UserWorkflowTest extends TestInit {
    private UserClient userClient;
    private CreateUpdateUserBuilder createUserBody;
    private CreateUpdateDeleteUserResponse createdUser;
    private GetUserResponse getUserData;
    private String username;

    @BeforeMethod(description = "Set up the test environment by initializing the UserClient and any necessary preconditions.")
    public void setUpTestPreconditions() {
        userClient = new UserClient(baseUrl);
        username = null;
    }

    @Test(description = "Test the creation of a new user by sending a request and validating the response data.")
    public void createUser() {
        createUserBody = generateRandomUser(1);

        createdUser = userClient.createNewUser(createUserBody, HTTP_OK);
        assertEquals("User's ID are different", createUserBody.id, Integer.parseInt(createdUser.message));

        getUserData = userClient.getUser(createUserBody.username, HTTP_OK);

        assertThat(createUserBody).usingRecursiveComparison().isEqualTo(getUserData);
        username = getUserData.username;
    }

    @Test(description = "Test updating an existing user by sending an update request and validating the updated user data.")
    public void updateUser() {
        createUserBody = generateRandomUser(1);

        CreateUpdateUserBuilder updateUserBody = generateRandomUser(2);

        createdUser = userClient.createNewUser(createUserBody, HTTP_OK);
        CreateUpdateDeleteUserResponse updatedUser = userClient.updateUser(updateUserBody, createUserBody.username, HTTP_OK);
        assertEquals("User's ID are different", updateUserBody.id, Integer.parseInt(updatedUser.message));

        getUserData = userClient.getUser(updateUserBody.username, HTTP_OK);

        assertThat(updateUserBody).usingRecursiveComparison().isEqualTo(getUserData);
        username = getUserData.username;
    }

    @Test(description = "Test deleting a user by sending a delete request and confirming that the user no longer exists.")
    public void deleteUser() {
        createUserBody = generateRandomUser(1);

        userClient.createNewUser(createUserBody, HTTP_OK);

        String deleteMessage = userClient.deleteUser(createUserBody.username, HTTP_OK).message;
        assertEquals("Username of the user doesn't equals", createUserBody.username, deleteMessage);

        userClient.getUser(createUserBody.username, HTTP_NOT_FOUND);
        username = null;
    }

    @AfterMethod(description = "Tear down method to clean up after each test.")
    public void tearDown() {
        if (username != null) {
            try {
                userClient.deleteUser(username, HTTP_OK);
                logger.info("User " + username + " deleted.");
            } catch (Exception e) {
                logger.info("User " + username + " does not exist or deletion failed.");
            }
        }
    }
}
