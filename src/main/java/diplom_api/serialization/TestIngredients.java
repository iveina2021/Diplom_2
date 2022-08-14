package diplom_api.serialization;

import java.util.List;

public class TestIngredients {

    private final List<String> ingredients;

    public TestIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
}
