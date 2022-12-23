package nt.tshape.automation.selenium.PageModal;

import nt.tshape.automation.selenium.ActionManager;
import nt.tshape.automation.selenium.Customer_Information;
import nt.tshape.automation.selenium.TestContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class AutomationPracticeAccountPage extends ActionManager {
    private final Customer_Information customerInformation;
    //locator
    private final String linkButtonXPathLocatorByName = "xpath=//span[normalize-space() = '%s']//parent::a";
    private final String titleCheckBoxLocatorById = "xpath=//input[@id='%s']";
    private final String personalInfoTextFieldLocatorByName = "xpath=//label[normalize-space(text())='%s']//parent::div//child::input";
    private final String dobDropDownFieldLocatorByXPathIdName = "xpath=//div[@id='%s']";
    private final String addressAliasLocatorByValue = "xpath=//h3[contains (@class,'page-subheading') and normalize-space(text())='%s']";
    private final String addressInfoLocatorByFieldName = "xpath=//span[normalize-space() = '%s']";
    public WebDriver driver;

    public AutomationPracticeAccountPage(WebDriver driver, TestContext testContext) {
        super(driver, testContext);
        this.customerInformation = testContext.getCustomerInformation();
    }

    public AutomationPracticeAccountPage clickLinkButtonByName(String buttonName) {
        waitForElementClickable(String.format(linkButtonXPathLocatorByName, buttonName));
        click(String.format(linkButtonXPathLocatorByName, buttonName));
        return this;
    }

    public AutomationPracticeAccountPage verifyPersonalInfoDataByFieldName(String fieldName) {
        waitForElementVisible(String.format(personalInfoTextFieldLocatorByName, fieldName));
        WebElement fieldToBeVerify = findElement(String.format(personalInfoTextFieldLocatorByName, fieldName));
        String actualValue = fieldToBeVerify.getAttribute("value");
        String expectedValue = customerInformation.getDataByFieldName(fieldName);
        Assert.assertEquals(actualValue, expectedValue, "Field: " + fieldName + " does not display correct value");
        return this;
    }

    public AutomationPracticeAccountPage verifyAccountLinkButtonWithAccountNameAvailable() {
        String accountName = customerInformation.getFirstName() + " " + customerInformation.getLastName();
        WebElement headerLinkWithAccountName = findElement(String.format(linkButtonXPathLocatorByName, accountName));
        Assert.assertTrue(headerLinkWithAccountName.isDisplayed(), "Link button with account name on menu does not displace as expected");
        return this;
    }

    public AutomationPracticeAccountPage verifyTitleCheckBoxDisplayCorrectlyById(String titleFieldId, Boolean expectedResult) {
        WebElement titleCheckBox = findElement(String.format(titleCheckBoxLocatorById, titleFieldId));
        Boolean actualValue = titleCheckBox.isSelected();
        Assert.assertEquals(actualValue, expectedResult, "Field: " + titleFieldId + " does not have same value with saved data");
        return this;
    }

    public AutomationPracticeAccountPage verifyDropDownFieldByIdDisplayCorrectValue(String fieldName) {
        WebElement dropDownFieldToBeVerify = findElement(String.format(dobDropDownFieldLocatorByXPathIdName, fieldName));
        String actualValue = dropDownFieldToBeVerify.getText().substring(0, dropDownFieldToBeVerify.getText().indexOf("-")).trim();
        String expectedValue = customerInformation.getDataByFieldName(fieldName);
        Assert.assertEquals(actualValue, expectedValue, "Field: " + fieldName + " does not have date same with saved one");
        return this;
    }

    public AutomationPracticeAccountPage verifyAddressAliasHeaderDisplayCorrectValue(String expectedValue) {
        waitForElementVisible(String.format(addressAliasLocatorByValue, expectedValue));
        String actualValue = getText(String.format(addressAliasLocatorByValue, expectedValue));
        Assert.assertEquals(actualValue.compareToIgnoreCase(expectedValue), 0, "Address " + expectedValue + " does not exist in address panel");
        return this;
    }

    public AutomationPracticeAccountPage verifyExistingOfAddressInfoByFieldName(String fieldName) {
        String expectedValue = customerInformation.getDataByFieldName(fieldName);
        expectedValue = (fieldName.equals("City")) ? expectedValue + "," : expectedValue;
        WebElement addressInfoField = findElement(String.format(addressInfoLocatorByFieldName, expectedValue));
        Assert.assertTrue(addressInfoField.isDisplayed(), "Field :" + fieldName + " not displayed on the address panel");
        return this;
    }
}
