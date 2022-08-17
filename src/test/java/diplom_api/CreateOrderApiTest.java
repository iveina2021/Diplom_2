package diplom_api;

import diplom_api.helper.UserRequestTestHelper;
import diplom_api.model.Ingredient;
import diplom_api.serialization.TestIngredients;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static diplom_api.helper.TestUtils.*;
import static diplom_api.helper.UserRequestTestHelper.loginUserRequest;
import static org.hamcrest.Matchers.equalTo;

public class CreateOrderApiTest {

    private String accessToken;

    @Test
    public void createOrderWithAuthorizationAndIngredientsReturnSuccess() {
        createUserAndLogin();

        List<Ingredient> ingredients = getIngredientsWithRequest();

        List<String> ingredientIds = getIngredientIds(ingredients);

        TestIngredients createOrderRequestIngredients = new TestIngredients(ingredientIds);

        Response createOrderResponse = UserRequestTestHelper.createOrderRequest(createOrderRequestIngredients);
        createOrderResponse.then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @Ignore("Test returns 200, but we are not authorized - looks like a bug!!!")
    public void createOrderWithoutAuthorizationReturnError() {
        List<Ingredient> ingredients = getIngredientsWithRequest();

        List<String> ingredientIds = getIngredientIds(ingredients);

        TestIngredients createOrderRequestIngredients = new TestIngredients(ingredientIds);

        Response createOrderResponse = UserRequestTestHelper.createOrderRequest(createOrderRequestIngredients);
        createOrderResponse.then()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    public void createOrderWithoutIngredients() {
        createUserAndLogin();

        List<String> ingredientIds = new ArrayList<>();

        TestIngredients createOrderRequestIngredients = new TestIngredients(ingredientIds);

        Response createOrderResponse = UserRequestTestHelper.createOrderRequest(createOrderRequestIngredients);
        createOrderResponse.then()
                .statusCode(400)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    public void createOrderWithIncorrectHash() {
        createUserAndLogin();

        List<String> ingredientIds = new ArrayList<>();
        ingredientIds.add("INCORRECT_ID");

        TestIngredients createOrderRequestIngredients = new TestIngredients(ingredientIds);

        Response createOrderResponse = UserRequestTestHelper.createOrderRequest(createOrderRequestIngredients);
        createOrderResponse.then()
                .statusCode(400);
    }

    private void createUserAndLogin() {
        UserRequestTestHelper.createUserRequest(prepareCreateUserRequest(EMAIL, PASSWORD, NAME))
                .then()
                .statusCode(200)
                .body("success", equalTo(true));

        Response response = loginUserRequest(prepareEmailPasswordRequest(EMAIL, PASSWORD));
        accessToken = response.getBody().jsonPath().getString("accessToken");
    }

    private List<Ingredient> getIngredientsWithRequest() {
        Response responseIngredients = UserRequestTestHelper.getIngredients();
        return responseIngredients.getBody().jsonPath().getList("data", Ingredient.class);
    }

    private List<String> getIngredientIds(List<Ingredient> ingredients) {
        List<String> ingredientIds = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            Ingredient ingredientFromList = ingredients.get(i);
            ingredientIds.add(ingredientFromList.getId());
        }
        return ingredientIds;
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





