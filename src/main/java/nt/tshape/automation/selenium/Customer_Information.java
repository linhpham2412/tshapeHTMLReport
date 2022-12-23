package nt.tshape.automation.selenium;

import java.util.HashMap;
import java.util.Map;

public class Customer_Information {
    private final Map<String, String> stateListFromDropDown = new HashMap<>();
    private final Map<String, String> countryListFromDropDown = new HashMap<>();
    private Boolean isMrTitle;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String dobDayValue;
    private String dobMonthValue;
    private String dobYearValue;
    private Boolean isReceiveNewsletter;
    private Boolean isOptInSpecialOffer;
    private String company;
    private String address;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String additionalInformation;
    private String homePhone;
    private String mobilePhone;
    private String addressAlias;

    public void setListOfState(String key, String value) {
        stateListFromDropDown.put(key, value);
    }

    public void setListOfCountry(String key, String value) {
        countryListFromDropDown.put(key, value);
    }

    public void saveDataByFieldName(String rawFieldName, String fieldValue) {
        switch (rawFieldName) {
            case "Email" -> email = fieldValue;
            case "First name" -> firstName = fieldValue;
            case "Last name" -> lastName = fieldValue;
            case "Password" -> password = fieldValue;
            case Constant.DOB_DAY_ID -> dobDayValue = fieldValue;
            case Constant.DOB_MONTH_ID -> dobMonthValue = fieldValue;
            case Constant.DOB_YEAR_ID -> dobYearValue = fieldValue;
            case "Company" -> company = fieldValue;
            case "Address" -> address = fieldValue;
            case "Address (Line 2)" -> addressLine2 = fieldValue;
            case "City" -> city = fieldValue;
            case Constant.ADDRESS_STATE_ID -> state = fieldValue;
            case "Zip/Postal Code" -> zipCode = fieldValue;
            case Constant.ADDRESS_COUNTRY_ID -> country = fieldValue;
            case "Home phone" -> homePhone = fieldValue;
            case "Mobile phone" -> mobilePhone = fieldValue;
            case "Assign an address alias for future reference." -> addressAlias = fieldValue;
        }
    }

    public void saveCheckBoxDataByFieldNameWithBooleanValue(String fieldName, Boolean fieldValue) {
        switch (fieldName) {
            case "Sign up for our newsletter!" -> isReceiveNewsletter = fieldValue;
            case "Receive special offers from our partners!" -> isOptInSpecialOffer = fieldValue;
        }
    }

    public void saveAdditionalInformationTextAreaByValue(String fieldValue) {
        additionalInformation = fieldValue;
    }

    public void saveCustomerTitleByBoolean(Boolean isMrTitle) {
        this.isMrTitle = isMrTitle;
    }

    public Boolean getMrTitle() {
        return isMrTitle;
    }

    public String getDataByFieldName(String rawFieldName) {
        return switch (rawFieldName) {
            case "E-mail address" -> email;
            case "First name" -> firstName;
            case "Last name" -> lastName;
            case "Password" -> password;
            case Constant.DOB_DAY_ID -> dobDayValue;
            case Constant.DOB_MONTH_ID -> convertMonthValueToTextFormat(dobMonthValue);
            case Constant.DOB_YEAR_ID -> dobYearValue;
            case "Company" -> company;
            case "Address" -> address;
            case "Address (Line 2)" -> addressLine2;
            case "City" -> city;
            case Constant.ADDRESS_STATE_ID -> convertStateValueToTextFormat(state);
            case "Zip/Postal Code" -> zipCode;
            case Constant.ADDRESS_COUNTRY_ID -> convertCountryValueToTextFormat(country);
            case "Home phone" -> homePhone;
            case "Mobile phone" -> mobilePhone;
            case "Assign an address alias for future reference." -> addressAlias;
            default -> "";
        };
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    private String convertMonthValueToTextFormat(String dobMonthValue) {
        return switch (dobMonthValue) {
            case "1" -> "January";
            case "2" -> "February";
            case "3" -> "March";
            case "4" -> "April";
            case "5" -> "May";
            case "6" -> "June";
            case "7" -> "July";
            case "8" -> "August";
            case "9" -> "September";
            case "10" -> "October";
            case "11" -> "November";
            case "12" -> "December";
            default -> "";
        };
    }

    private String convertStateValueToTextFormat(String stateValueAsKey) {
        return this.stateListFromDropDown.get(stateValueAsKey);
    }

    private String convertCountryValueToTextFormat(String countryValueAsKey) {
        return this.countryListFromDropDown.get(countryValueAsKey);
    }
}
