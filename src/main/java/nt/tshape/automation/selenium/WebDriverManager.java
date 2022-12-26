package nt.tshape.automation.selenium;

import nt.tshape.automation.config.ConfigLoader;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

public class WebDriverManager {
    private static WebDriver driver;

    public static void iniDriver(String browser) throws MalformedURLException {
        if (ConfigLoader.getConfiguration("testLocation").equalsIgnoreCase("local"))
            driver = WebDriverCreator.createLocalDriver(browser);
        else if (ConfigLoader.getConfiguration("testLocation").equalsIgnoreCase("remote")) {
            driver = WebDriverCreator.createRemoteHubDriver(browser);
        }else if (ConfigLoader.getConfiguration("testLocation").equalsIgnoreCase("browserstack")){
            driver = WebDriverCreator.createBrowserStackWebDriver(browser);
        }
        driver.manage().window().maximize();
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
