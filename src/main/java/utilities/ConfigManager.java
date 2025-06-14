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
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(input);
            logger.info("Configuration loaded successfully from config.properties");
        } catch (IOException e) {
            logger.error("Failed to load configuration file", e);
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }
    // Generic getter
    private static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property key '{}' not found in configuration", key);
        }
        return value;
    }

    public static String getUrl() {
        return getProperty("url");
    }

    public static String getApiVersion() {
        return getProperty("api_version");
    }

    public static String getUsername() {
        return getProperty("username");
    }

    public static String getPassword() {
        return getProperty("password");
    }

    public static String getHomePageUrl() {
        return getProperty("homePageUrl");
    }

    public static String getLoginUrl() {
        return getProperty("loginUrl");
    }

    public static String getClientId() {
        return getProperty("clientId");
    }

    public static String getClientSecret() {
        return getProperty("clientSecret");
    }

    public static String getSecurityToken() {
        return getProperty("securityToken");
    }
}
