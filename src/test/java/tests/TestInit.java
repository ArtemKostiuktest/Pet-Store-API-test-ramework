package tests;

import io.qameta.allure.Step;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import utils.SuiteConfiguration;

import java.io.IOException;

@Listeners(listeners.TestListener.class)
public class TestInit {
    protected String baseUrl;

    @Step("Preparing a configuration for the test")
    @BeforeClass
    public void setUpTestConfig() throws IOException {
        SuiteConfiguration conf = new SuiteConfiguration();
        baseUrl = conf.getPropsUrl();

        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new IllegalArgumentException("The base URL is missing.");
        }
    }
}