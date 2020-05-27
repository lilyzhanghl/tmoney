package horizon.api.base;

import java.util.HashMap;
import java.util.Map;

public class Auth {
    Map<String, String> cookies;

    public Auth(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public Auth() {
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(HashMap<String, String> cookies) {
        this.cookies = cookies;
    }
}
