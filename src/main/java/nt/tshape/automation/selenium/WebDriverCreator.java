package nt.tshape.automation.selenium;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class WebDriverCreator {

    private static final String webDriverLocation = "C:\\Users\\Admin\\OneDrive\\Documents\\LinhPham\\TShapeTraining\\Webdriver\\";

    public static WebDriver createLocalDriver(String browser) {
        WebDriver driver = null;
        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", webDriverLocation + "chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized"); // open Browser in maximized mode
            options.addArguments("disable-infobars"); // disabling infobars
            options.addArguments("--disable-extensions"); // disabling extensions
            options.addArguments("--disable-gpu"); // applicable to Windows os only
            options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
            options.addArguments("--no-sandbox"); // Bypass OS security model
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", webDriverLocation + "geckodriver.exe");
            FirefoxOptions options = new FirefoxOptions().setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe").setAcceptInsecureCerts(true);
            driver = new FirefoxDriver(options);
        }
        System.out.println("Local driver and run on browser [" + browser + "]");
        return driver;
    }

    public static WebDriver createRemoteHubDriver(String browser) throws MalformedURLException {
        String hubUrl = "http://localhost:4444/wd/hub";
        WebDriver driver = null;
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized"); // open Browser in maximized mode
            options.addArguments("disable-infobars"); // disabling infobars
            options.addArguments("--disable-extensions"); // disabling extensions
            options.addArguments("--disable-gpu"); // applicable to Windows os only
            options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
            options.addArguments("--no-sandbox"); // Bypass OS security model
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setBrowserName(browser);
            capabilities.setPlatform(Platform.WINDOWS);
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            driver = new RemoteWebDriver(capabilities);
            System.setProperty("webdriver.chrome.driver", webDriverLocation + "chromedriver.exe");
            driver = new RemoteWebDriver(options);
        }
        if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setBrowserName(browser);
            capabilities.setPlatform(Platform.WINDOWS);
            capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
            driver = new RemoteWebDriver(capabilities);
            System.setProperty("webdriver.gecko.driver", webDriverLocation + "geckodriver.exe");
            driver = new RemoteWebDriver(options);
        }
        System.out.println("Remote driver on [" + hubUrl + "] and run on browser [" + browser + "]");
        return driver;
    }

    public static WebDriver createBrowserStackWebDriver(String browser) throws MalformedURLException {
        String username = "phamlinh15";
        String accessKey = "3r8Un6KQDkGhyqsZqxPq";
        WebDriver driver = null;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", browser);
        HashMap<String, Object> browserStackOptions = new HashMap<String, Object>();
        browserStackOptions.put("os", "windows");
        browserStackOptions.put("osVersion", "10");
        browserStackOptions.put("sessionName", "BrowserStack Check");
        browserStackOptions.put("seleniumVersion", "3.141.59");
        capabilities.setCapability("bstack:options", browserStackOptions);
        driver = new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + "@hub.browserstack.com/wd/hub"), capabilities);
        System.out.println("BrowserStack driver and run on browser [" + browser + "]");
        return driver;
    }
}
