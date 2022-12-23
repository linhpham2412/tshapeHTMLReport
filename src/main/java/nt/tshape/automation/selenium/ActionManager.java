package nt.tshape.automation.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ActionManager {
    private WebDriver driver;
    private WebDriverWait wait;
    private TestContext testContext;
    StringBuilder printOutInfo = null;

    public ActionManager(WebDriver driver, TestContext testContext) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Constant.SHORT_TIME);
        this.testContext = testContext;
        printOutInfo = new StringBuilder();
    }

    public TestContext getTestContext(){
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
            printOutInfo.append("Sent [").append(keysToSend).append("] into element [").append(elementToSendKey.toString()).append("]");
        } catch (StaleElementReferenceException staleElementReferenceException) {
            sendKeys(elementToSendKey, keysToSend);
        } catch (Exception e) {
            printOutInfo.append("Cannot send [").append(keysToSend).append("] into element [").append(elementToSendKey.toString()).append("]");
            throw e;
        }
        System.out.println(printOutInfo);
    }

    public void clearText(String elementToClearText) {
        try {
            WebElement workingElement = findElement(elementToClearText);
            workingElement.clear();
            printOutInfo.append("Cleared text in element [").append(elementToClearText.toString()).append("]");
        } catch (StaleElementReferenceException staleElementReferenceException) {
            clearText(elementToClearText);
        } catch (Exception e) {
            printOutInfo.append("Cannot clear text of element [").append(elementToClearText.toString()).append("]");
            throw e;
        }
        System.out.println(printOutInfo);
    }

    public String getText(String elementToGetText) {
        try {
            WebElement workingElement = findElement(elementToGetText);
            String resultText = workingElement.getTagName().equalsIgnoreCase("input") ? workingElement.getAttribute("value") : workingElement.getText();
            printOutInfo.append("Got text [").append(resultText).append("] from element [").append(elementToGetText.toString()).append("]");
            System.out.println(printOutInfo);
            return resultText;
        } catch (StaleElementReferenceException staleElementReferenceException) {
            return getText(elementToGetText);
        } catch (Exception e) {
            printOutInfo.append("Cannot get text from element [").append(elementToGetText.toString()).append("]");
            System.out.println(printOutInfo);
            throw e;
        }
    }

    public void click(String elementToClick) {
        try {
            findElement(elementToClick).click();
            printOutInfo.append("Clicked on the element [").append(elementToClick.toString()).append("]");
            System.out.println(printOutInfo);
        } catch (StaleElementReferenceException staleElementReferenceException) {
            click(elementToClick);
        } catch (Exception e) {
            printOutInfo.append("Cannot click on element [").append(elementToClick.toString()).append("]");
            System.out.println(printOutInfo);
            throw e;
        }
    }

    public void openUrl(String urlToBeOpen) {
        try {
            driver.get(urlToBeOpen);
            printOutInfo.append("Browser opened page with url [").append(urlToBeOpen).append("] successfully!");
            System.out.println(printOutInfo);
        } catch (Exception e) {
            printOutInfo.append("Browser cannot opened page with url [").append(urlToBeOpen).append("]");
            System.out.println(printOutInfo);
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
            printOutInfo.append("Moved and clicked on element [").append(elementMovingToAndClick).append("]");
            System.out.println(printOutInfo);
        } catch (StaleElementReferenceException staleElementReferenceException) {
            mouseMoveToElementAndClick(elementMovingToAndClick);
            printOutInfo.append("Moved and clicked on element [").append(elementMovingToAndClick).append("]");
            System.out.println(printOutInfo);
        } catch (Exception e) {
            printOutInfo.append("Cannot move and click on element [").append(elementMovingToAndClick).append("]");
            System.out.println(printOutInfo);
            throw e;
        }
    }

    public void selectDropDownFieldWithValue(String elementDropDownField, String fieldValue) {
        try {
            Select workingDropDownField = new Select(findElement(elementDropDownField));
            workingDropDownField.selectByValue(fieldValue);
            printOutInfo.append("Opened drop down field [").append(elementDropDownField).append("] and select value [").append(fieldValue).append("]");
            System.out.println(printOutInfo);
        } catch (StaleElementReferenceException staleElementReferenceException) {
            selectDropDownFieldWithValue(elementDropDownField, fieldValue);
            printOutInfo.append("Opened drop down field [").append(elementDropDownField).append("] and select value [").append(fieldValue).append("]");
            System.out.println(printOutInfo);
        } catch (Exception e) {
            printOutInfo.append("Can not opene drop down field [").append(elementDropDownField).append("] and select value [").append(fieldValue).append("]");
            System.out.println(printOutInfo);
            throw e;
        }
    }

    public Select getDropDownOptionsList(String elementDropDownField) {
        return new Select(findElement(elementDropDownField));
    }
}
