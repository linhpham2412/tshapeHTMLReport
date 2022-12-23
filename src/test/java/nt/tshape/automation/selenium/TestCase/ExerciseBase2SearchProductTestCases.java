package nt.tshape.automation.selenium.TestCase;

import nt.tshape.automation.selenium.Constant;
import nt.tshape.automation.selenium.Customer_Information;
import nt.tshape.automation.selenium.PageModal.AutomationPracticeIndexPage;
import nt.tshape.automation.selenium.TestContext;
import nt.tshape.automation.setup.WebDriverTestNGSetupBase;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class ExerciseBase2SearchProductTestCases extends WebDriverTestNGSetupBase {

    @Test
    public void CollectProductNameAndSearch() {
        AutomationPracticeIndexPage automationPracticeIndexPage = new AutomationPracticeIndexPage(getDriver(), getTestContext());

        automationPracticeIndexPage
                .goToPageByURL("http://automationpractice.com/index.php")
                .moveToMenuWithNameAndClickOnButtonByFieldName("Women", "T-shirts")
                .focusOnProductInListByIndex("1")
                .getDetailDataOfFocusedProductByName(Constant.PRODUCT_NAME)
                .getDetailDataOfFocusedProductByName(Constant.PRODUCT_CURRENT_PRICE)
                .inputSearchTextIntoSearchField(getTestContext().getAttributeByName(Constant.PRODUCT_NAME))
                .clickButtonByName("Search")
                .verifyDetailDataOfSavedProductDisplayOnSearchByName(Constant.PRODUCT_NAME)
                .verifyDetailDataOfSavedProductDisplayOnSearchByName(Constant.PRODUCT_CURRENT_PRICE);
    }
}
