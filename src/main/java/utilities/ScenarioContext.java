
// Purpose: Manages scenario-specific state across Cucumber steps.
// - Stores data in ThreadLocal for thread-safety in parallel execution (if enabled).
// - Tracks scenario failure and resets state after each scenario.
// - Used in LoginSteps to store the Scenario object, log scenario names, and track failures.
        package utilities;

import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {
    private static final Logger logger = LoggerFactory.getLogger(ScenarioContext.class);
    // ThreadLocal to track scenario failure status per thread
    private static final ThreadLocal<Boolean> scenarioFailed = ThreadLocal.withInitial(() -> false);
    // ThreadLocal to store the current Cucumber Scenario object per thread
    private static final ThreadLocal<Scenario> currentScenario = new ThreadLocal<>();
    // ThreadLocal to store scenario-specific attributes (e.g., caseId) per thread
    private static final ThreadLocal<Map<String, Object>> attributes = ThreadLocal.withInitial(HashMap::new);

    // Sets the current Scenario object for the thread
    public static void setScenario(Scenario scenario) {
        logger.debug("Setting scenario: {}", scenario.getName());
        currentScenario.set(scenario);
    }

    // Marks the scenario as failed
    public static void markScenarioFailed() {
        logger.warn("Marking scenario as failed");
        scenarioFailed.set(true);
    }

    // Checks if the scenario has failed
    public static boolean isScenarioFailed() {
        return scenarioFailed.get();
    }

    // Retrieves the current Scenario object
    public static Scenario getScenario() {
        return currentScenario.get();
    }

    // Stores an attribute (e.g., caseId) in the scenario context
    public static void setAttribute(String key, Object value) {
        logger.debug("Setting attribute: {} = {}", key, value);
        attributes.get().put(key, value);
    }

    // Retrieves an attribute (e.g., caseId) from the scenario context
    public static Object getAttribute(String key) {
        Object value = attributes.get().get(key);
        logger.debug("Retrieving attribute: {} = {}", key, value);
        return value;
    }

    // Resets the scenario context after each scenario
    public static void reset() {
        logger.debug("Resetting scenario context");
        scenarioFailed.set(false);
        currentScenario.remove();
        attributes.get().clear();
    }
}
