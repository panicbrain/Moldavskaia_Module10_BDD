package Stepdefs;

import DriverManager.ChromeWebDriverSingleton;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import java.util.UUID;

public class ScenarioHooks {
    @Before
    public void beforeScenario() {
        ChromeWebDriverSingleton.getWebDriverInstance();
    }

    @After
    public void afterScenario() {
        ChromeWebDriverSingleton.kill();
    }
}
