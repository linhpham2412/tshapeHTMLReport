package nt.tshape.automation.selenium.PageModal;

import nt.tshape.automation.selenium.ActionManager;
import nt.tshape.automation.selenium.Constant;
import nt.tshape.automation.selenium.Customer_Information;
import nt.tshape.automation.selenium.TestContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class AutomationPracticeIndexPage extends ActionManager {
    //locator
    private final String linkButtonXPathLocatorByName = "xpath=//a[normalize-space(text()) = '%s']";
    private final String postFixSelect = "//select";
    private final String buttonXPathLocatorByName = "xpath=//span[normalize-space() = '%s']//parent::button";
    private final String dobDropDownFieldLocatorByXPathIdName = "xpath=//div[@id='%s']";
    private final String linkMenuListXPathLocatorByName = "xpath=//a[contains (@class,'sf-with-ul') and normalize-space(text()) = '%s']";
    private final String personalInfoCheckBoxLocatorByText = "xpath=//label[normalize-space()='%s']//preceding-sibling::div";
    private final String emailCreateTextBox = "id=email_create";
    private final String titleCheckBoxLocatorById = "xpath=//input[@id='%s']";
    private final String personalInfoTextFieldLocatorByName = "xpath=//label[normalize-space(text())='%s']//parent::div//child::input";
    private final String yourAddressTextFieldLocatorByName = "xpath=//label[normalize-space(text())='%s']//parent::p//child::input";
    private final String additionalInformationTextareaXPathLocator = "xpath=//textarea[@id='other']";
    private final String linkSubMenuToMergeWithMenuXPathLocatorByName = "//following::ul[contains(@class,'submenu-container')]//a[normalize-space(text()) = '%s']";
    private final String productXPathLocatorByIndex = "xpath=//ul[contains (@class,'product_list')]//li[contains (@class,'ajax_block_product')][%s]";
    private final String productDetailXPathLocatorByClassName = "//div[@class='right-block']//*[@class='%s']";
    private final String searchTextField = "id=search_query_top";



    //constructor
    public AutomationPracticeIndexPage(WebDriver driver, TestContext testContext) {
        super(driver, testContext);
    }

    public AutomationPracticeIndexPage goToPageByURL(String locationURL) {
        openUrl(locationURL);
        return this;
    }

    public AutomationPracticeIndexPage clickLinkButtonByName(String linkName) {
        waitForElementClickable(String.format(linkButtonXPathLocatorByName, linkName));
        click(String.format(linkButtonXPathLocatorByName, linkName));
        return this;
    }

    public AutomationPracticeIndexPage clickButtonByName(String buttonName) {
        waitForElementClickable(String.format(buttonXPathLocatorByName, buttonName));
        click(String.format(buttonXPathLocatorByName, buttonName));
        return this;
    }

    public AutomationPracticeIndexPage inputToEmailAddressWithEmail(String emailAddress) {
        waitForElementVisible(emailCreateTextBox);
        sendKeys(emailCreateTextBox,emailAddress);
        getTestContext().getCustomerInformation().saveDataByFieldName("Email", emailAddress);
        return this;
    }

    public AutomationPracticeIndexPage selectDropDownFieldByIdWithValue(String fieldId, String fieldValue) {
        waitForElementClickable(String.format(dobDropDownFieldLocatorByXPathIdName, fieldId));
        selectDropDownFieldWithValue(String.format(dobDropDownFieldLocatorByXPathIdName, fieldId) + postFixSelect,fieldValue);
        getTestContext().getCustomerInformation().saveDataByFieldName(fieldId, fieldValue);
        List<WebElement> options = getDropDownOptionsList(String.format(dobDropDownFieldLocatorByXPathIdName, fieldId) + postFixSelect).getOptions();
        if (fieldId.equals(Constant.ADDRESS_STATE_ID)) {
            options.forEach(option -> getTestContext().getCustomerInformation().setListOfState(option.getAttribute("value"), option.getText()));
        } else if (fieldId.equals(Constant.ADDRESS_COUNTRY_ID)) {
            options.forEach(option -> getTestContext().getCustomerInformation().setListOfCountry(option.getAttribute("value"), option.getText()));
        }
        return this;
    }


    public AutomationPracticeIndexPage selectPersonalTitleAs(Boolean isMrTitle) {
        String titleId = (isMrTitle) ? Constant.TITLE_MR_FIELD_ID : Constant.TITLE_MRS_FIELD_ID;
        waitForElementVisible(String.format(titleCheckBoxLocatorById, titleId));
        click(String.format(titleCheckBoxLocatorById, titleId));
        getTestContext().getCustomerInformation().saveCustomerTitleByBoolean(isMrTitle);
        return this;
    }

    public AutomationPracticeIndexPage inputPersonalInformationFieldNameWithValue(String fieldName, String fieldValue) {
        sendKeys(String.format(personalInfoTextFieldLocatorByName, fieldName),fieldValue);
        getTestContext().getCustomerInformation().saveDataByFieldName(fieldName, fieldValue);
        return this;
    }

    public AutomationPracticeIndexPage checkOnPersonalInfoCheckBoxByText(String fieldText, Boolean isCheck) {
        if (isCheck) click(String.format(personalInfoCheckBoxLocatorByText, fieldText));
        getTestContext().getCustomerInformation().saveCheckBoxDataByFieldNameWithBooleanValue(fieldText, isCheck);
        return this;
    }

    public AutomationPracticeIndexPage inputYourAddressTextFieldNameWithValue(String fieldName, String fieldValue) {
        clearText(String.format(yourAddressTextFieldLocatorByName, fieldName));
        sendKeys(String.format(yourAddressTextFieldLocatorByName, fieldName),fieldValue);
        getTestContext().getCustomerInformation().saveDataByFieldName(fieldName, fieldValue);
        return this;
    }

    public AutomationPracticeIndexPage inputAdditionalTextareaWithText(String fieldText) {
        sendKeys(additionalInformationTextareaXPathLocator,fieldText);
        getTestContext().getCustomerInformation().saveAdditionalInformationTextAreaByValue(fieldText);
        return this;
    }

    public AutomationPracticeIndexPage moveToMenuWithNameAndClickOnButtonByFieldName(String menuName, String subMenuFieldName) {
        waitForElementVisible(String.format(linkMenuListXPathLocatorByName, menuName));
        mouseMoveToElementAndClick(String.format(linkMenuListXPathLocatorByName, menuName));
        mouseMoveToElementAndClick(String.format(linkMenuListXPathLocatorByName, menuName) + String.format(linkSubMenuToMergeWithMenuXPathLocatorByName, subMenuFieldName));;
        return this;
    }

    public AutomationPracticeIndexPage focusOnProductInListByIndex(String index) {
        getTestContext().setAttribute("focusedProductXPath", String.format(productXPathLocatorByIndex, index));
        return this;
    }

    public AutomationPracticeIndexPage getDetailDataOfFocusedProductByName(String className) {
        waitForElementVisible(getTestContext().getAttributeByName("focusedProductXPath") + String.format(productDetailXPathLocatorByClassName, className));
        String valueToBeExtracted = getText(getTestContext().getAttributeByName("focusedProductXPath") + String.format(productDetailXPathLocatorByClassName, className));
        getTestContext().setAttribute(className, valueToBeExtracted);
        return this;
    }

    public AutomationPracticeIndexPage inputSearchTextIntoSearchField(String searchText) {
        sendKeys(searchTextField,searchText);
        return this;
    }

    public AutomationPracticeIndexPage verifyDetailDataOfSavedProductDisplayOnSearchByName(String className) {
        String actualValue = getText(getTestContext().getAttributeByName("focusedProductXPath") + String.format(productDetailXPathLocatorByClassName, className));
        String expectedValue = getTestContext().getAttributeByName(className);
        Assert.assertEquals(actualValue, expectedValue, "Search Product Field " + className + " does not match with the focused Product field");
        return this;
    }
}
