package diplom_api;

import diplom_api.helper.UserRequestTestHelper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static diplom_api.helper.TestUtils.*;
import static diplom_api.helper.UserRequestTestHelper.loginUserRequest;
import static org.hamcrest.Matchers.equalTo;

public class LoginUserApiTest {

    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = STELLAR_BURGERS_URL;
        UserRequestTestHelper.createUserRequest(prepareCreateUserRequest(EMAIL, PASSWORD, NAME))
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    public void loginUserReturnSuccess() {
        Response loginResponse = loginUserRequest(prepareEmailPasswordRequest(EMAIL, PASSWORD));
        accessToken = loginResponse.getBody().jsonPath().getString("accessToken");
        loginResponse
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    public void loginUserWithIncorrectEmailReturnError() {
        loginUserRequest(prepareEmailPasswordRequest("Missing email", PASSWORD))
                .then()
                .statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    public void loginUserWithIncorrectPasswordReturnError() {
        loginUserRequest(prepareEmailPasswordRequest(EMAIL, "error_password"))
                .then()
                .statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }

    @After
    public void deleteUser() {
        if (accessToken == null) {
            accessToken = loginUserRequest(prepareEmailPasswordRequest(EMAIL, PASSWORD))
                    .getBody().jsonPath().getString("accessToken");
        }

        if (accessToken != null) {
            UserRequestTestHelper.deleteUser(accessToken);
        }
    }

}


