package diplom_api;

import diplom_api.helper.UserRequestTestHelper;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static diplom_api.helper.TestUtils.*;
import static diplom_api.helper.UserRequestTestHelper.loginUserRequest;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserApiTest {

    @Test
    public void createUniqueUserReturnSuccess() {
        UserRequestTestHelper.createUserRequest(prepareCreateUserRequest(EMAIL, PASSWORD, NAME))
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    public void createTwoIdenticalUsersReturnError() {
        UserRequestTestHelper.createUserRequest(prepareCreateUserRequest(EMAIL, PASSWORD, NAME))
                .then()
                .statusCode(200);

        UserRequestTestHelper.createUserRequest(prepareCreateUserRequest(EMAIL, PASSWORD, NAME))
                .then()
                .statusCode(403)
                .body("message", equalTo("User already exists"));
    }

    @Test
    public void createUserWithoutEmailReturnError() {
        UserRequestTestHelper.createUserRequest(prepareCreateUserRequest("", PASSWORD, NAME))
                .then()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void createUserWithoutPasswordReturnError() {
        UserRequestTestHelper.createUserRequest(prepareCreateUserRequest(EMAIL, "", NAME))
                .then()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void createUserWithoutNameReturnError() {
        UserRequestTestHelper.createUserRequest(prepareCreateUserRequest(EMAIL, PASSWORD, ""))
                .then()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @After
    public void deleteUser() {
        Response loginResponse = loginUserRequest(prepareEmailPasswordRequest(EMAIL, PASSWORD));
        if (loginResponse.getStatusCode() == 200) {
            String accessToken = loginResponse.getBody().jsonPath().getString("accessToken");
            UserRequestTestHelper.deleteUser(accessToken);
        }
    }
}