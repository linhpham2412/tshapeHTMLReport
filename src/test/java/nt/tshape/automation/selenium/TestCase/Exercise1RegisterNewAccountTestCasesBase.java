package nt.tshape.automation.selenium.TestCase;

import nt.tshape.automation.selenium.Constant;
import nt.tshape.automation.selenium.Customer_Information;
import nt.tshape.automation.selenium.PageModal.AutomationPracticeAccountPage;
import nt.tshape.automation.selenium.PageModal.AutomationPracticeIndexPage;
import nt.tshape.automation.setup.WebDriverTestNGSetupBase;
import org.testng.annotations.Test;

import static nt.tshape.automation.selenium.Utils.*;

public class Exercise1RegisterNewAccountTestCasesBase extends WebDriverTestNGSetupBase {

    @Test
    public void RegisterNewAccount() {
        AutomationPracticeIndexPage automationPracticeIndexPage = new AutomationPracticeIndexPage(getDriver(), getTestContext());
        AutomationPracticeAccountPage automationPracticeAccountPage = new AutomationPracticeAccountPage(getDriver(), getTestContext());

        automationPracticeIndexPage
                .openPage()
                .clickLinkButtonByName("Sign in")
                .inputToEmailAddressWithEmail(generateTestEmail())
                .clickButtonByName("Create an account")
                .selectPersonalTitleAs(generateRandomTrueOrFalse())
                .inputPersonalInformationFieldNameWithValue("First name", generateRandomTestCharacters(5))
                .inputPersonalInformationFieldNameWithValue("Last name", generateRandomTestCharacters(7))
                .inputPersonalInformationFieldNameWithValue("Password", generateRandomTestCharacters(5))
                .selectDropDownFieldByIdWithValue(Constant.DOB_DAY_ID, generateRandomNumberInRange(1, 31))
                .selectDropDownFieldByIdWithValue(Constant.DOB_MONTH_ID, generateRandomNumberInRange(1, 12))
                .selectDropDownFieldByIdWithValue(Constant.DOB_YEAR_ID, generateRandomNumberInRange(1900, 2022))
                .checkOnPersonalInfoCheckBoxByText("Sign up for our newsletter!", generateRandomTrueOrFalse())
                .checkOnPersonalInfoCheckBoxByText("Receive special offers from our partners!", generateRandomTrueOrFalse())
                .inputYourAddressTextFieldNameWithValue("Company", generateRandomTestCharacters(10))
                .inputYourAddressTextFieldNameWithValue("Address", generateRandomTestCharacters(50))
                .inputYourAddressTextFieldNameWithValue("Address (Line 2)", generateRandomTestCharacters(50))
                .inputYourAddressTextFieldNameWithValue("City", generateRandomTestCharacters(20))
                .selectDropDownFieldByIdWithValue(Constant.ADDRESS_STATE_ID, generateRandomNumberInRange(1, 50))
                .inputYourAddressTextFieldNameWithValue("Zip/Postal Code", generateRandomNumberInRange(10000, 99999))
                .selectDropDownFieldByIdWithValue(Constant.ADDRESS_COUNTRY_ID, Constant.COUNTRY_UNITED_STATE)
                .inputAdditionalTextareaWithText(generateRandomTestCharacters(100))
                .inputYourAddressTextFieldNameWithValue("Home phone", generateRandomNumberInRange(1000000000, 1111111111))
                .inputYourAddressTextFieldNameWithValue("Mobile phone", generateRandomNumberInRange(1000000000, 1111111111))
                .inputYourAddressTextFieldNameWithValue("Assign an address alias for future reference.", generateRandomTestCharacters(10))
                .clickButtonByName("Register");

        automationPracticeAccountPage
                .clickLinkButtonByName("My personal information")
                .verifyPersonalInfoDataByFieldName("First name")
                .verifyPersonalInfoDataByFieldName("Last name")
                .verifyAccountLinkButtonWithAccountNameAvailable()
                .verifyPersonalInfoDataByFieldName("E-mail address")
                .verifyTitleCheckBoxDisplayCorrectlyById(Constant.TITLE_MR_FIELD_ID, getTestContext().getCustomerInformation().getMrTitle())
                .verifyTitleCheckBoxDisplayCorrectlyById(Constant.TITLE_MRS_FIELD_ID, !getTestContext().getCustomerInformation().getMrTitle())
                .verifyDropDownFieldByIdDisplayCorrectValue(Constant.DOB_DAY_ID)
                .verifyDropDownFieldByIdDisplayCorrectValue(Constant.DOB_MONTH_ID)
                .verifyDropDownFieldByIdDisplayCorrectValue(Constant.DOB_YEAR_ID)
                .clickLinkButtonByName("Back to your account")
                .clickLinkButtonByName("My addresses")
                .verifyAddressAliasHeaderDisplayCorrectValue(getTestContext().getCustomerInformation().getDataByFieldName("Assign an address alias for future reference."))
                .verifyExistingOfAddressInfoByFieldName("First name")
                .verifyExistingOfAddressInfoByFieldName("Last name")
                .verifyExistingOfAddressInfoByFieldName("Company")
                .verifyExistingOfAddressInfoByFieldName("Address")
                .verifyExistingOfAddressInfoByFieldName("Address (Line 2)")
                .verifyExistingOfAddressInfoByFieldName("City")
                .verifyExistingOfAddressInfoByFieldName(Constant.ADDRESS_STATE_ID)
                .verifyExistingOfAddressInfoByFieldName("Zip/Postal Code")
                .verifyExistingOfAddressInfoByFieldName(Constant.ADDRESS_COUNTRY_ID)
                .verifyExistingOfAddressInfoByFieldName("Home phone")
                .verifyExistingOfAddressInfoByFieldName("Mobile phone");
    }
}
