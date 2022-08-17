package diplom_api.helper;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserRequestTestHelper {

    private static final String STELLAR_BURGERS_URL = "https://stellarburgers.nomoreparties.site";
    private static final String REGISTER_URL = "/api/auth/register";
    private static final String LOGIN_URL = "/api/auth/login";
    private static final String ORDERS_URL = "/api/orders";
    private static final String AUTH_USER_URL = "/api/auth/user";
    private static final String INGREDIENTS_URL = "/api/ingredients";

    public static Response createUserRequest(Map<String, String> body) {
        return given()
                .spec(buildSpec())
                .and()
                .body(body)
                .when()
                .post(REGISTER_URL);
    }

    public static Response loginUserRequest(Map<String, String> body) {
        return given()
                .spec(buildSpec())
                .and()
                .body(body)
                .when()
                .post(LOGIN_URL);
    }

    public static Response createOrderRequest(Object body) {
        return given()
                .spec(buildSpec())
                .and()
                .body(body)
                .when()
                .post(ORDERS_URL);
    }

    public static Response updateUserProfileRequest(Map<String, String> body, String accessToken) {
        return given()
                .spec(buildSpec())
                .header("Authorization", accessToken)
                .and()
                .body(body)
                .when()
                .patch(AUTH_USER_URL);
    }

    public static Response updateUserProfileRequestWithoutAuthorization(Map<String, String> body) {
        return given()
                .spec(buildSpec())
                .and()
                .body(body)
                .when()
                .patch(AUTH_USER_URL);
    }

    public static Response getIngredients() {
        return given()
                .spec(buildSpec())
                .when()
                .get(INGREDIENTS_URL);
    }

    public static Response getUserOrdersRequest(String accessToken) {
        return given()
                .spec(buildSpec())
                .header("Authorization", accessToken)
                .when()
                .get(ORDERS_URL);
    }

    public static Response getUserOrdersRequestWithoutAuthorization() {
        return given()
                .spec(buildSpec())
                .when()
                .get(ORDERS_URL);
    }


    public static Response deleteUser(String accessToken) {
        return given()
                .spec(buildSpec())
                .header("Authorization", accessToken)
                .when()
                .delete(AUTH_USER_URL);
    }

    private static RequestSpecification buildSpec() {
        return new RequestSpecBuilder()
                .addHeader("Content-type", "application/json")
                .setBaseUri(STELLAR_BURGERS_URL)
                .build();
    }
}

