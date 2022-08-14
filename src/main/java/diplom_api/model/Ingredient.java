package diplom_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ingredient {

    @JsonProperty("_id")
    private String id;

    public String getId() {
        return id;
    }
}
