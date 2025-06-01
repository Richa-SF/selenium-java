package utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

public class Auth {
    private static final String ENVIRONMENT = "qa"; // Define environment once for consistency

    public static String getAccessToken() {
        // Fetch ALL credentials from ConfigManager for consistency
        String loginUrl = ConfigManager.getLoginUrl(ENVIRONMENT); // NOW FROM CONFIG
        String clientId = ConfigManager.getClientId(ENVIRONMENT);   // NOW FROM CONFIG
        String clientSecret = ConfigManager.getClientSecret(ENVIRONMENT); // NOW FROM CONFIG
        String username = ConfigManager.getUsername(ENVIRONMENT);
        String password = ConfigManager.getPassword(ENVIRONMENT);
        String securityToken = ConfigManager.getSecurityToken(ENVIRONMENT); // NOW FROM CONFIG

        Map<String, String> authParams = new HashMap<>();
        authParams.put("grant_type", "password");
        authParams.put("client_id", clientId);
        authParams.put("client_secret", clientSecret);
        authParams.put("username", username);
        authParams.put("password", password + securityToken); // Always combine!

        System.out.println("--- Auth Request Details ---");
        System.out.println("URL: " + loginUrl);
        System.out.println("Client ID Used: " + clientId);
        System.out.println("Username Used: " + username);
        System.out.println("DEBUG: Combined Password String being sent: " + clientSecret);
        System.out.println("Secret: " + (password + securityToken));

        // Do NOT print full password/token in console for security!
        System.out.println("--------------------------");

        Response response = RestAssured
                .given()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParams(authParams)
                .post(loginUrl);

        if (response.getStatusCode() == 200) {
            System.out.println("--- Auth: Access Token Obtained Successfully! ---");
            System.out.println("✅ Access Token Retrieved Successfully.");
            return response.jsonPath().getString("access_token");
        } else {
            String errorBody = response.getBody().asString();
            System.err.println("--- Auth: Failed to get access token. Status: " + response.getStatusCode() + ", Body: " + errorBody + " ---");
            throw new RuntimeException("Failed to get access token: " + errorBody);
        }
    }
}