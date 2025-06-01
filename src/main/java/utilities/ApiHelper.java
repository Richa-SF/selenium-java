package utilities;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.ConfigManager;
import utilities.Auth;

import java.util.HashMap;
import java.util.Map;

public class ApiHelper {
    private static final String INSTANCE_URL = ConfigManager.getUrl("qa");  // Fetch from config
    private static final String API_VERSION = ConfigManager.getApiv("qa");  //

    /**
     * Creates a case in Salesforce via REST API.
     * @param subject Case subject
     * @param description Case description
     * @param origin Case origin (e.g., Web, Phone, Email)
     * @param status Case status (e.g., New, In Progress, Closed)
     * @param priority Case priority (e.g., High, Medium, Low)
     * @param contactId Salesforce Contact ID associated with the case
     * @return Case ID if successful, otherwise throws an exception
     */
    public static String createCase(String subject, String description, String origin, String status, String priority, String contactId) {
        String accessToken = Auth.getAccessToken();

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("Subject", subject);
        requestBody.put("Description", description);
        requestBody.put("Origin", origin);
        requestBody.put("Status", status);
        requestBody.put("Priority", priority);
     //   requestBody.put("ContactId", contactId);  // Ensure it's a valid ContactId
      /*  if (contactId != null && !contactId.isEmpty()) {
            requestBody.put("ContactId", contactId);
        }*/
        String requestUrl = INSTANCE_URL + "/services/data/" + API_VERSION + "/sobjects/Case";
        System.out.println("DEBUG: Attempting to POST to URL: " + requestUrl);

        Response response = RestAssured
                .given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(INSTANCE_URL + "/services/data/" + API_VERSION + "/sobjects/Case");

        if (response.getStatusCode() == 201) {
            String caseId = response.jsonPath().getString("id");
            System.out.println("✅ Case Created Successfully: " + caseId);
            return caseId;
        } else {
            throw new RuntimeException("❌ Failed to create case: " + response.getBody().asString());
        }
    }
}
