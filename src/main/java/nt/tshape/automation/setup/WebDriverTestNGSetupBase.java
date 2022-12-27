package nt.tshape.automation.setup;

import nt.tshape.automation.reportmanager.HTMLReporter;
import nt.tshape.automation.selenium.TestContext;
import nt.tshape.automation.selenium.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public class WebDriverTestNGSetupBase {
    private static HTMLReporter htmlReporter;
    private TestContext testContext;

    @AfterSuite
    public static void afterSuite() {
        htmlReporter.getExtentReports().flush();
        WebDriverManager.getDriver().quit();
    }

    public WebDriver getDriver() {
        return WebDriverManager.getDriver();
    }

    public TestContext getTestContext() {
        return testContext;
    }

    @Parameters({"browser"})
    @BeforeSuite
    public void beforeSuiteSetUp(@Optional("chrome") String browser) {
        WebDriverManager.iniDriver(browser);
        testContext = new TestContext();
        htmlReporter = new HTMLReporter("newHTMLReport.html");
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        htmlReporter.createReportNode(method.getName(), method.toGenericString());
    }

    @BeforeTest
    public void beforeTest() {
        htmlReporter.createReportClass(getClass().getName(), getClass().descriptorString());
    }
}
