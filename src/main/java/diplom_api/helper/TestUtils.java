package diplom_api.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TestUtils {

    public static final String PASSWORD = UUID.randomUUID() + "_password";
    public static final String NAME = UUID.randomUUID() + "_name";
    public static String EMAIL = UUID.randomUUID() + "@yandex.ru";

    public static Map<String, String> prepareCreateUserRequest(String email, String password, String name) {
        Map<String, String> map = new HashMap<>();

        map.put("email", email);
        map.put("password", password);
        map.put("name", name);

        return map;
    }

    public static Map<String, String> prepareEmailPasswordRequest(String email, String password) {
        Map<String, String> map = new HashMap<>();

        map.put("email", email);
        map.put("password", password);

        return map;
    }
}




