package filter;

import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingFilter implements WebDriverListener {
    public static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
    private static final ThreadLocal<StringBuilder> logBuffer = ThreadLocal.withInitial(StringBuilder::new);

    @Override
    public void beforeGet(WebDriver driver, String url) {
        logger.info("Navigating to: {}", url);
        logBuffer.get().append("Navigating to: ").append(url).append("\n");
    }

    @Override
    public void beforeClick(WebElement element) {
        logger.info("Clicking element");
        logBuffer.get().append("Clicking element\n");
    }

    public void attachLogsToAllure(String attachmentName) {
        String logs = logBuffer.get().toString();
        if (!logs.isEmpty()) {
            Allure.addAttachment(attachmentName, "text/plain", logs);
        }
        logBuffer.get().setLength(0);
    }
}
