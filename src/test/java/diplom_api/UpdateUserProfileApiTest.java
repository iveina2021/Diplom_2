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

public class UpdateUserProfileApiTest {

    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = STELLAR_BURGERS_URL;
    }

    @Test
    public void updateUserProfileWithAuthorization() {
        UserRequestTestHelper.createUserRequest(prepareCreateUserRequest(EMAIL, PASSWORD, NAME))
                .then()
                .statusCode(200)
                .body("success", equalTo(true));

        Response loginResponse = loginUserRequest(prepareEmailPasswordRequest(EMAIL, PASSWORD));
        accessToken = loginResponse.getBody().jsonPath().getString("accessToken");

        loginResponse.then().statusCode(200);

        UserRequestTestHelper.updateUserProfileRequest(prepareEmailPasswordRequest(EMAIL, PASSWORD), accessToken)
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    public void updateUserProfileWithoutAuthorization() {
        UserRequestTestHelper.updateUserProfileRequestWithoutAuthorization(prepareEmailPasswordRequest(EMAIL, PASSWORD))
                .then()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
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
