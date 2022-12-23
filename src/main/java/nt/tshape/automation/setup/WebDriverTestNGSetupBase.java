package nt.tshape.automation.setup;

import nt.tshape.automation.selenium.TestContext;
import nt.tshape.automation.selenium.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class WebDriverTestNGSetupBase {
    private TestContext testContext;
    public WebDriver getDriver() {
        return WebDriverManager.getDriver();
    }

    public TestContext getTestContext(){
        return testContext;
    }

    @AfterSuite
    public static void afterSuite() {
        WebDriverManager.getDriver().quit();
    }

    @Parameters({"browser"})
    @BeforeSuite
    public void beforeSuiteSetUp(@Optional("chrome") String browser) {
        WebDriverManager.iniDriver(browser);
        testContext = new TestContext();
    }
}
