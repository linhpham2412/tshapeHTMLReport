package nt.tshape.automation.selenium.TestCase;

import nt.tshape.automation.selenium.PageModal.SaucedemoIndex;
import nt.tshape.automation.setup.WebDriverTestNGSetupBase;
import org.testng.annotations.Test;

import static nt.tshape.automation.selenium.Utils.generateRandomNumberInRange;
import static nt.tshape.automation.selenium.Utils.generateRandomTestCharacters;

public class Buy1ProductFlowSwagLabs extends WebDriverTestNGSetupBase {

    @Test
    public void BuyProductE2EFlow() {
        SaucedemoIndex saucedemoIndex = new SaucedemoIndex(getDriver(), getTestContext());
        saucedemoIndex
                .openPage()
                .inputUserNameWithValue("standard_user")
                .inputPasswordWithValue("secret_sauce")
                .clickOnLoginButton()
                .verifyPageTitleWithValue("PRODUCTS")
                .clickOnItemInListByName("Sauce Labs Backpack")
                .clickOnAddToCartButtonInProductPage()
                .verifyBadgeDisplayWithNumber("1")
                .clickOnShoppingCartButton()
                .verifyPageTitleWithValue("YOUR CART")
                .verifyProductNameIsCorrect()
                .verifyProductPriceIsCorrect()
                .verifyProductQuantityIsCorrectWithValue("1")
                .clickOnCheckOutButton()
                .verifyPageTitleWithValue("CHECKOUT: YOUR INFORMATION")
                .inputFirstNameWithValue(generateRandomTestCharacters(8))
                .inputLastNameWithValue(generateRandomTestCharacters(8))
                .inputPostalCodeWithValue(generateRandomNumberInRange(1000000, 9999999))
                .clickOnContinueButton()
                .verifyPageTitleWithValue("CHECKOUT: OVERVIEW")
                .verifyProductNameIsCorrect()
                .verifyProductPriceIsCorrect()
                .verifyProductQuantityIsCorrectWithValue("1")
                .clickOnFinishButton()
                .verifyPageTitleWithValue("CHECKOUT: COMPLETE!")
                .verifyCompleteMessageWithValue("THANK YOU FOR YOUR ORDER")
                .clickOnBackToProductButton()
                .verifyPageTitleWithValue("PRODUCTS");
    }
}
