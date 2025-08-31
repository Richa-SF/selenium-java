package utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
    private static final Properties properties = new Properties();

    static {
        String configPath = System.getenv("CONFIG"); // Jenkins sets this
        if (configPath == null || configPath.isEmpty()) {
            // Fallback for local run
            configPath = "src/main/resources/config.properties";
            logger.info("No CONFIG env var found, using local file: {}", configPath);
        } else {
            logger.info("Loading configuration from Jenkins credentials file: {}", configPath);
        }

        try (InputStream input = new FileInputStream(configPath)) {
            properties.load(input);
            logger.info("Configuration loaded successfully from {}", configPath);
        } catch (IOException e) {
            logger.error("Failed to load configuration file: {}", configPath, e);
            throw new RuntimeException("Failed to load configuration file: " + configPath, e);
        }
    }

    private static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property key '{}' not found in configuration", key);
        }
        return value;
    }

    // Getter methods
    public static String getUrl() { return getProperty("url"); }
    public static String getApiVersion() { return getProperty("api_version"); }
    public static String getUsername() { return getProperty("username"); }
    public static String getPassword() { return getProperty("password"); }
    public static String getHomePageUrl() { return getProperty("homePageUrl"); }
    public static String getLoginUrl() { return getProperty("loginUrl"); }
    public static String getClientId() { return getProperty("clientId"); }
    public static String getClientSecret() { return getProperty("clientSecret"); }
    public static String getSecurityToken() { return getProperty("securityToken"); }
}
