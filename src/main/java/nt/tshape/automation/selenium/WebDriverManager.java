package nt.tshape.automation.selenium;

import org.openqa.selenium.WebDriver;

public class WebDriverManager {
    private static WebDriver driver;

    public static void iniDriver(String browser) {
        driver = WebDriverCreator.createLocalDriver(browser);
        driver.manage().window().maximize();
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
