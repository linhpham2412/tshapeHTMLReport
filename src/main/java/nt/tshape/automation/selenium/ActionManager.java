package nt.tshape.automation.selenium;

import nt.tshape.automation.reportmanager.HTMLReporter;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

public class ActionManager {
    private final WebDriver driver;
    private final Wait<WebDriver> wait;
    private final TestContext testContext;

    public ActionManager(WebDriver driver, TestContext testContext) {
        this.driver = driver;
        wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Constant.SHORT_TIME, TimeUnit.SECONDS)
                .pollingEvery(Constant.SHORT_TIME, TimeUnit.SECONDS)
                .ignoring(AssertionError.class);
        this.testContext = testContext;
    }

    public TestContext getTestContext() {
        return testContext;
    }

    public WebElement highlightElement(WebElement elementToHighlight) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].style.borders='2px solid red'", elementToHighlight);
        return elementToHighlight;
    }

    public List<WebElement> highlightElement(List<WebElement> elementsToHighlight) {
        for (WebElement webElement : elementsToHighlight) {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            javascriptExecutor.executeScript("arguments[0].style.borders='2px solid red'", webElement);
        }
        return elementsToHighlight;
    }

    public WebElement findElement(String elementByTypeAndPath) {
        WebElement workingElement = null;
        String[] extractedString = elementByTypeAndPath.split("=", 2);
        String byType = extractedString[0];
        String path = extractedString[1];
        try {
            switch (byType) {
                case "id" -> workingElement = driver.findElement(By.id(path));
                case "xpath" -> workingElement = driver.findElement(By.xpath(path));
                case "class" -> workingElement = driver.findElement(By.className(path));
                case "css" -> workingElement = driver.findElement(By.cssSelector(path));
                case "linkText" -> workingElement = driver.findElement(By.linkText(path));
                case "name" -> workingElement = driver.findElement(By.name(path));
                case "partialLinkText" -> workingElement = driver.findElement(By.partialLinkText(path));
                case "tag" -> workingElement = driver.findElement(By.tagName(path));
            }
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("The element located by : [" + elementByTypeAndPath + "] cannot be found!");
            throw new NoSuchElementException("The element located by : [" + elementByTypeAndPath + "] cannot be found!");
        } catch (StaleElementReferenceException staleElementReferenceException) {
            return findElement(elementByTypeAndPath);
        } catch (Exception e) {
            System.out.println("There is an error when finding the element : [" + elementByTypeAndPath + "]");
            throw e;
        }
        return workingElement;
    }

    public void sendKeys(String elementToSendKey, CharSequence keysToSend) {
        try {
            WebElement workingElement = findElement(elementToSendKey);
            workingElement.sendKeys(keysToSend);
            System.out.println("Sent [" + keysToSend + "] into element [" + elementToSendKey + "]");
        } catch (StaleElementReferenceException staleElementReferenceException) {
            sendKeys(elementToSendKey, keysToSend);
        } catch (Exception e) {
            System.out.println("Cannot send [" + keysToSend + "] into element [" + elementToSendKey + "]");
            throw e;
        }
    }

    public void clearText(String elementToClearText) {
        try {
            WebElement workingElement = findElement(elementToClearText);
            workingElement.clear();
            System.out.println("Cleared text in element [" + elementToClearText + "]");
        } catch (StaleElementReferenceException staleElementReferenceException) {
            clearText(elementToClearText);
        } catch (Exception e) {
            System.out.println("Cannot clear text of element [" + elementToClearText + "]");
            throw e;
        }
    }

    public String getText(String elementToGetText) {
        try {
            WebElement workingElement = findElement(elementToGetText);
            String resultText = workingElement.getTagName().equalsIgnoreCase("input") ? workingElement.getAttribute("value") : workingElement.getText();
            System.out.println("Got text [" + resultText + "] from element [" + elementToGetText + "]");
            return resultText;
        } catch (StaleElementReferenceException staleElementReferenceException) {
            return getText(elementToGetText);
        } catch (Exception e) {
            System.out.println("Cannot get text from element [" + elementToGetText + "]");
            throw e;
        }
    }

    public void click(String elementToClick) {
        try {
            findElement(elementToClick).click();
            System.out.println("Clicked on the element [" + elementToClick + "]");
        } catch (StaleElementReferenceException staleElementReferenceException) {
            click(elementToClick);
        } catch (Exception e) {
            System.out.println("Cannot click on element [" + elementToClick + "]");
            throw e;
        }
    }

    public void openUrl(String urlToBeOpen) {
        try {
            driver.get(urlToBeOpen);
            System.out.println("Browser opened page with url [" + urlToBeOpen + "] successfully!");
        } catch (Exception e) {
            System.out.println("Browser cannot opened page with url [" + urlToBeOpen + "]");
            throw e;
        }
    }

    public void waitForElementVisible(String elementToBeWait) {
        wait.until(ExpectedConditions.visibilityOf(findElement(elementToBeWait)));
    }

    public void waitForElementClickable(String elementToBeWait) {
        wait.until(ExpectedConditions.elementToBeClickable(findElement(elementToBeWait)));
    }

    public void mouseMoveToElementAndClick(String elementMovingToAndClick) {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(findElement(elementMovingToAndClick));
            actions.click().build().perform();
            System.out.println("Moved and clicked on element [" + elementMovingToAndClick + "]");
        } catch (StaleElementReferenceException staleElementReferenceException) {
            mouseMoveToElementAndClick(elementMovingToAndClick);
            System.out.println("Moved and clicked on element [" + elementMovingToAndClick + "]");
        } catch (Exception e) {
            System.out.println("Cannot move and click on element [" + elementMovingToAndClick + "]");
            throw e;
        }
    }

    public void selectDropDownFieldWithValue(String elementDropDownField, String fieldValue) {
        try {
            Select workingDropDownField = new Select(findElement(elementDropDownField));
            workingDropDownField.selectByValue(fieldValue);
            System.out.println("Opened drop down field [" + elementDropDownField + "] and select value [" + fieldValue + "]");
        } catch (StaleElementReferenceException staleElementReferenceException) {
            selectDropDownFieldWithValue(elementDropDownField, fieldValue);
            System.out.println("Opened drop down field [" + elementDropDownField + "] and select value [" + fieldValue + "]");
        } catch (Exception e) {
            System.out.println("Can not opene drop down field [" + elementDropDownField + "] and select value [" + fieldValue + "]");
            throw e;
        }
    }

    public Select getDropDownOptionsList(String elementDropDownField) {
        return new Select(findElement(elementDropDownField));
    }

    public void assertEqual(String objectName, String expected, String actual) throws IOException {
        System.out.println("Compare value [" + expected + "] is equal with: [" + actual + "]");
        try {
            assertEquals(expected, actual);
            HTMLReporter.getCurrentReportNode().pass("Assert object [" + objectName + "] passed because expected value: [" + expected + "] is equal with actual value: [" + actual + "]");
            System.out.println("Assert object [" + objectName + "] passed because expected value: [" + expected + "] is equal with actual value: [" + actual + "]");
        } catch (AssertionError e) {
            HTMLReporter.getCurrentReportNode().fail("Assertion failed because object [" + objectName + "] has expected value: [" + expected + "] is not equal with actual value: [" + actual + "]");
            System.out.println("Assertion failed because object [" + objectName + "] has expected value: [" + expected + "] is not equal with actual value: [" + actual + "]");
            HTMLReporter.getHtmlReporter().takesScreenshot(driver, "CaptureImageOnFailedCase_");
        }
    }
}
