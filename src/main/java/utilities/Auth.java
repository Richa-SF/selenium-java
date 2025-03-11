package utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

    public class Auth {
        private static final String LOGIN_URL = "https://login.salesforce.com/services/oauth2/token";
        private static final String CLIENT_ID = "3MVG9GCMQoQ6rpzT4uxx0cSKPwqeBRA56ZKoGQ63HElzFnUfnxQAt54Qn1gXuk1b8O22CtMMSYbUrz2GGw9Wf";
        private static final String CLIENT_SECRET = "C0B18AEB14A029E2DF27A6FAFC38A32D3AC46168409BED9FF5DA07EC49E9B246";
        private static final String USERNAME = ConfigManager.getUsername("qa");
        private static final String PASSWORD = ConfigManager.getPassword("qa");;
        private static final String SECURITY_TOKEN = "QPMcWHVzk3am3P7UWVE9KaARG";

        public static String getAccessToken() {
            Map<String, String> authParams = new HashMap<>();
            authParams.put("grant_type", "password");
            authParams.put("client_id", CLIENT_ID);
            authParams.put("client_secret", CLIENT_SECRET);
            authParams.put("username", USERNAME);
            authParams.put("password", PASSWORD);

            Response response = RestAssured
                    .given()
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .formParams(authParams)
                    .post(LOGIN_URL);

            if (response.getStatusCode() == 200) {
                return response.jsonPath().getString("access_token");
            } else {
                throw new RuntimeException("Failed to get access token: " + response.getBody().asString());
            }
        }
    }




