package nt.tshape.automation.selenium.PageModal;

import nt.tshape.automation.config.ConfigLoader;
import nt.tshape.automation.selenium.ActionManager;
import nt.tshape.automation.selenium.TestContext;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class SaucedemoIndex extends ActionManager {
    //Locator
    private final String selectItemInListByName = "xpath=//div[@class='inventory_item_name' and text()='%s']";
    private final String addToCartButtonInProductDetail = "css=.btn_primary.btn_inventory";
    private final String shoppingCartBadgeLocator = "css=.shopping_cart_badge";
    private final String shoppingCartLinkLocator = "css=.shopping_cart_link";
    private final String userNameTextBoxLocator = "id=user-name";
    private final String passwordTextBoxLocator = "id=password";
    private final String loginButtonLocator = "id=login-button";
    private final String productPriceInProductDetail = "css=.inventory_details_price";
    private final String pageTitle = "css=.title";
    private final String cartProductName = "css=.inventory_item_name";
    private final String cartProductPrice = "css=.inventory_item_price";
    private final String cartQuantity = "css=.cart_quantity";
    private final String checkOutButton = "id=checkout";
    private final String checkoutFirtName = "id=first-name";
    private final String checkoutLastName = "id=last-name";
    private final String checkoutPostalCode = "id=postal-code";
    private final String checkoutContinueButton = "id=continue";
    private final String checkoutFinishButton = "id=finish";
    private final String backToProductButton = "id=back-to-products";
    private final String completeMessageHeader = "css=.complete-header";

    public SaucedemoIndex(WebDriver driver, TestContext testContext) {
        super(driver, testContext);
    }

    //Function
    public SaucedemoIndex openPage() {
        openUrl(ConfigLoader.getEnvironment("url"));
        return this;
    }

    public SaucedemoIndex clickOnItemInListByName(String itemName) {
        waitForElementClickable(String.format(selectItemInListByName, itemName));
        click(String.format(selectItemInListByName, itemName));
        getTestContext().setAttribute("Product_Name", itemName);
        return this;
    }

    public SaucedemoIndex clickOnAddToCartButtonInProductPage() {
        waitForElementClickable(addToCartButtonInProductDetail);
        click(addToCartButtonInProductDetail);
        getTestContext().setAttribute("Product_Price", getText(productPriceInProductDetail));
        return this;
    }

    public SaucedemoIndex clickOnShoppingCartButton() {
        waitForElementClickable(shoppingCartLinkLocator);
        click(shoppingCartLinkLocator);
        return this;
    }

    public SaucedemoIndex inputUserNameWithValue(String usernameValue) {
        waitForElementVisible(userNameTextBoxLocator);
        sendKeys(userNameTextBoxLocator, usernameValue);
        return this;
    }

    public SaucedemoIndex inputPasswordWithValue(String passwordValue) {
        waitForElementVisible(passwordTextBoxLocator);
        sendKeys(passwordTextBoxLocator, passwordValue);
        return this;
    }

    public SaucedemoIndex clickOnLoginButton() {
        waitForElementClickable(loginButtonLocator);
        click(loginButtonLocator);
        return this;
    }

    public SaucedemoIndex clickOnCheckOutButton() {
        waitForElementClickable(checkOutButton);
        click(checkOutButton);
        return this;
    }

    public SaucedemoIndex inputFirstNameWithValue(String firstName) {
        waitForElementVisible(checkoutFirtName);
        sendKeys(checkoutFirtName, firstName);
        return this;
    }

    public SaucedemoIndex inputLastNameWithValue(String lastName) {
        waitForElementVisible(checkoutLastName);
        sendKeys(checkoutLastName, lastName);
        return this;
    }

    public SaucedemoIndex inputPostalCodeWithValue(String postalCode) {
        waitForElementVisible(checkoutPostalCode);
        sendKeys(checkoutPostalCode, postalCode);
        return this;
    }

    public SaucedemoIndex clickOnContinueButton() {
        waitForElementClickable(checkoutContinueButton);
        click(checkoutContinueButton);
        return this;
    }

    public SaucedemoIndex clickOnFinishButton() {
        waitForElementClickable(checkoutFinishButton);
        click(checkoutFinishButton);
        return this;
    }

    public SaucedemoIndex clickOnBackToProductButton() {
        waitForElementClickable(backToProductButton);
        click(backToProductButton);
        return this;
    }

    //Verify
    public SaucedemoIndex verifyBadgeDisplayWithNumber(String expectedNumberValue) throws IOException {
        //Act
        String actualNumberValue = getText(shoppingCartBadgeLocator);
        elementHighlight(findElement(shoppingCartLinkLocator));

        //Verify
        assertEqual("Badge Number", expectedNumberValue, actualNumberValue);
        elementRemoveHighLight(findElement(shoppingCartLinkLocator));
        return this;
    }

    public SaucedemoIndex verifyPageTitleWithValue(String expectedTitle) throws IOException {
        //Act
        String actualPageTitle = getText(pageTitle);
        elementHighlight(findElement(pageTitle));

        //Verify
        assertEqual("Page Title", expectedTitle, actualPageTitle);
        elementRemoveHighLight(findElement(pageTitle));
        return this;
    }

    public SaucedemoIndex verifyProductNameIsCorrect() throws IOException {
        //Act
        String expectedProductName = getTestContext().getAttributeByName("Product_Name");
        String actualProductName = getText(cartProductName);
        elementHighlight(findElement(cartProductName));

        //Verify
        assertEqual("Product Name", expectedProductName, actualProductName);
        elementRemoveHighLight(findElement(cartProductName));
        return this;
    }

    public SaucedemoIndex verifyProductPriceIsCorrect() throws IOException {
        //Act
        String expectedProductPrice = getTestContext().getAttributeByName("Product_Price");
        String actualProductPrice = getText(cartProductPrice);
        elementHighlight(findElement(cartProductPrice));

        //Verify
        assertEqual("Product Price", expectedProductPrice, actualProductPrice);
        elementRemoveHighLight(findElement(cartProductPrice));
        return this;
    }

    public SaucedemoIndex verifyProductQuantityIsCorrectWithValue(String expectedProductQuantity) throws IOException {
        //Act
        String actualProductQuanity = getText(cartQuantity);
        elementHighlight(findElement(cartQuantity));

        //Verify
        assertEqual("Product Quantity", expectedProductQuantity, actualProductQuanity);
        elementRemoveHighLight(findElement(cartQuantity));
        return this;
    }

    public SaucedemoIndex verifyCompleteMessageWithValue(String expectedCompleteMessage) throws IOException {
        //Act
        String actualCompleteMessage = getText(completeMessageHeader);
        elementHighlight(findElement(completeMessageHeader));

        //Verify
        assertEqual("Complete Message", expectedCompleteMessage, actualCompleteMessage);
        elementRemoveHighLight(findElement(completeMessageHeader));
        return this;
    }
}
