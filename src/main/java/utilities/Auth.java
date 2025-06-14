package utilities;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
public class Auth {
    private static final Logger logger = LoggerFactory.getLogger(Auth.class);
    private static final String ENVIRONMENT = "qa";

    public static String getAccessToken() {
        String loginUrl = ConfigManager.getLoginUrl();
        String clientId = ConfigManager.getClientId();
        String clientSecret = ConfigManager.getClientSecret();
        String username = ConfigManager.getUsername();
        String password = ConfigManager.getPassword();
        String securityToken = ConfigManager.getSecurityToken();

        Map<String, String> authParams = new HashMap<>();
        authParams.put("grant_type", "password");
        authParams.put("client_id", clientId);
        authParams.put("client_secret", clientSecret);
        authParams.put("username", username);
        authParams.put("password", password + securityToken);

        logger.debug("--- Auth Request Details ---");
        logger.debug("URL: {}", loginUrl);
        logger.debug("Client ID Used: {}", clientId);
        logger.debug("Username Used: {}", username);
        logger.debug("--------------------------");

        Response response = RestAssured
                .given()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParams(authParams)
                .post(loginUrl);

        if (response.getStatusCode() == 200) {
            logger.info("Access Token Retrieved Successfully");
            return response.jsonPath().getString("access_token");
        } else {
            String errorBody = response.getBody().asString();
            logger.error("Failed to get access token. Status: {}, Body: {}", response.getStatusCode(), errorBody);
            throw new RuntimeException("Failed to get access token: " + errorBody);
        }
    }
}
