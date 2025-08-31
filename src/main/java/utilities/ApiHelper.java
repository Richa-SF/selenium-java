package utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.ConfigManager;
import utilities.Auth;

import java.util.HashMap;
import java.util.Map;

public class ApiHelper {
    private static final Logger logger = LoggerFactory.getLogger(ApiHelper.class);
    private static final String INSTANCE_URL = ConfigManager.getUrl();
    private static final String API_VERSION = ConfigManager.getApiVersion();

    public static String createCase(String subject, String description, String origin, String status, String priority, String contactId) {
        String accessToken = Auth.getAccessToken();
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("Subject", subject);
        requestBody.put("Description", description);
        requestBody.put("Origin", origin);
        requestBody.put("Status", status);
        requestBody.put("Priority", priority);

        String requestUrl = INSTANCE_URL + "/services/data/" + API_VERSION + "/sobjects/Case";
        logger.debug("Attempting to POST to URL: {}", requestUrl);

        Response response = RestAssured
                .given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(requestUrl);

        if (response.getStatusCode() == 201) {
            logger.info("Case Created Successfully");
            String caseId = response.jsonPath().getString("id");

            String caseDetailsUrl = INSTANCE_URL + "/services/data/" + API_VERSION + "/sobjects/Case/" + caseId;
            Response getResponse = RestAssured
                    .given()
                    .header("Authorization", "Bearer " + accessToken)
                    .get(caseDetailsUrl);
            String caseNumber = getResponse.jsonPath().getString("CaseNumber");
            logger.info("Fetched Case Number");

            return caseNumber;
        } else {
            logger.error("Failed to create case: {}", response.getBody().asString());
            throw new RuntimeException("Failed to create case: " + response.getBody().asString());
        }
    }
}
