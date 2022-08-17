package diplom_api.helper;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UserRequestTestHelper {

    private static final String STELLAR_BURGERS_URL = "https://stellarburgers.nomoreparties.site";

    public static Response createUserRequest(String json) {
        return given()
                .spec(buildSpec())
                .and()
                .body(json)
                .when()
                .post("/api/auth/register");
    }

    public static Response loginUserRequest(String json) {
        return given()
                .spec(buildSpec())
                .and()
                .body(json)
                .when()
                .post("/api/auth/login");
    }

    public static Response createOrderRequest(Object body) {
        return given()
                .spec(buildSpec())
                .and()
                .body(body)
                .when()
                .post("/api/orders");
    }

    public static Response updateUserProfileRequest(String json, String accessToken) {
        return given()
                .spec(buildSpec())
                .header("Authorization", accessToken)
                .and()
                .body(json)
                .when()
                .patch("/api/auth/user");
    }

    public static Response updateUserProfileRequestWithoutAuthorization(String json) {
        return given()
                .spec(buildSpec())
                .and()
                .body(json)
                .when()
                .patch("/api/auth/user");
    }

    public static Response getIngredients() {
        return given()
                .spec(buildSpec())
                .when()
                .get("/api/ingredients");
    }

    public static Response getUserOrdersRequest(String accessToken) {
        return given()
                .spec(buildSpec())
                .header("Authorization", accessToken)
                .when()
                .get("/api/orders");
    }

    public static Response getUserOrdersRequestWithoutAuthorization() {
        return given()
                .spec(buildSpec())
                .when()
                .get("/api/orders");
    }


    public static Response deleteUser(String accessToken) {
        return given()
                .spec(buildSpec())
                .header("Authorization", accessToken)
                .when()
                .delete("/api/auth/user");
    }

    private static RequestSpecification buildSpec() {
        return new RequestSpecBuilder()
                .addHeader("Content-type", "application/json")
                .setBaseUri(STELLAR_BURGERS_URL)
                .build();
    }
}

