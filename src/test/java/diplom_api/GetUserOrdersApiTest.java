package diplom_api;

import diplom_api.helper.UserRequestTestHelper;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static diplom_api.helper.TestUtils.*;
import static diplom_api.helper.UserRequestTestHelper.loginUserRequest;
import static org.hamcrest.Matchers.equalTo;

public class GetUserOrdersApiTest {

    private String accessToken;

    @Test
    public void getOrdersForUserWithAuthorization() {
        createAndLoginUser();

        UserRequestTestHelper.getUserOrdersRequest(accessToken)
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    public void getOrdersForUserWithoutAuthorization() {
        UserRequestTestHelper.getUserOrdersRequestWithoutAuthorization()
                .then()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }

    private Response createAndLoginUser() {
        UserRequestTestHelper.createUserRequest(prepareCreateUserRequest(EMAIL, PASSWORD, NAME))
                .then()
                .statusCode(200)
                .body("success", equalTo(true));

        Response response = loginUserRequest(prepareEmailPasswordRequest(EMAIL, PASSWORD));
        accessToken = response.getBody().jsonPath().getString("accessToken");
        response
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
        return response;
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

