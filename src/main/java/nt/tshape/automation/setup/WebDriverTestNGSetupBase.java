package nt.tshape.automation.setup;

import nt.tshape.automation.reportmanager.HTMLReporter;
import nt.tshape.automation.selenium.TestContext;
import nt.tshape.automation.selenium.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class WebDriverTestNGSetupBase {
    private TestContext testContext;

    @AfterSuite
    public static void afterSuite() {
        HTMLReporter.getHtmlReporter().getExtentReports().flush();
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
    public void beforeSuiteSetUp(@Optional("chrome") String browser) throws MalformedURLException {
        WebDriverManager.iniDriver(browser);
        testContext = new TestContext();
        HTMLReporter.initHTMLReporter("HTML_TestingReport.html", "HTMLReport_Test_Output_On_");
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        HTMLReporter.getHtmlReporter().createReportNode(method.getName(), method.toGenericString());
    }

    @BeforeTest
    public void beforeTest() {
        HTMLReporter.getHtmlReporter().createReportClass(getClass().getName(), getClass().descriptorString());
    }
}
