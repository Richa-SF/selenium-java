package utilities;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private static JsonNode config;

    static {
        try {
            // Load the config file
            File configFile = new File("src/main/resources/config.json");
            ObjectMapper objectMapper = new ObjectMapper();
            config = objectMapper.readTree(configFile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }

    public static String getUrl(String environment) {
        return config.get("environments").get(environment).get("url").asText();
    }

    public static String getApiv(String environment) {
        return config.get("environments").get(environment).get("api_version").asText();
    }

    public static String getUsername(String environment) {
        return config.get("environments").get(environment).get("username").asText();
    }

    public static String getPassword(String environment) {
        return config.get("environments").get(environment).get("password").asText();
    }
    public static String gethomePageUrl(String environment) {
        return config.get("environments").get(environment).get("homePageUrl").asText();
    }

    // --- NEW METHODS ADDED ---
    public static String getLoginUrl(String environment) {
        return config.get("environments").get(environment).get("loginUrl").asText();
    }

    public static String getClientId(String environment) {
        return config.get("environments").get(environment).get("clientId").asText();
    }

    public static String getClientSecret(String environment) {
        return config.get("environments").get(environment).get("clientSecret").asText();
    }

    public static String getSecurityToken(String environment) {
        return config.get("environments").get(environment).get("securityToken").asText();
    }
}