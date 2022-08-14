package diplom_api.helper;

public class TestUtils {

    public static final String PASSWORD = "1999191";
    public static final String NAME = "TED";
    public static String EMAIL = "iveina!!!!!!!!@mail.ru";
    public static String STELLAR_BURGERS_URL = "https://stellarburgers.nomoreparties.site";

    public static String prepareCreateUserRequest(String email, String password, String name) {
        return "{\"email\": \"" + email + "\", \"password\": \"" + password + "\", \"name\": \"" + name + "\"}";
    }

    public static String prepareEmailPasswordRequest(String email, String password) {
        return "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";
    }
}




