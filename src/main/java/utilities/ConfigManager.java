package utilities;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
    private static JsonNode config;

    static {
        try {
            File configFile = new File("src/main/resources/config.json");
            ObjectMapper objectMapper = new ObjectMapper();
            config = objectMapper.readTree(configFile);
            logger.info("Configuration loaded successfully");
        } catch (IOException e) {
            logger.error("Failed to load configuration file", e);
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
