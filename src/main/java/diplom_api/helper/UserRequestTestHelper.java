package diplom_api.helper;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserRequestTestHelper {

    public static Response createUserRequest(String json) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/auth/register");
    }

    public static Response loginUserRequest(String json) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/auth/login");
    }

    public static Response createOrderRequest(Object body) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .post("/api/orders");
    }

    public static Response updateUserProfileRequest(String json, String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .and()
                .body(json)
                .when()
                .patch("/api/auth/user");
    }

    public static Response updateUserProfileRequestWithoutAuthorization(String json) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .patch("/api/auth/user");
    }

    public static Response getIngredients() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/ingredients");
    }

    public static Response getUserOrdersRequest(String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .when()
                .get("/api/orders");
    }

    public static Response getUserOrdersRequestWithoutAuthorization() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/orders");
    }


    public static Response deleteUser(String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .when()
                .delete("/api/auth/user");
    }
}

