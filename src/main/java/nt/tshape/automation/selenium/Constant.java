package nt.tshape.automation.selenium;

import okhttp3.MediaType;

public class Constant {
    public static final long SHORT_TIME = 3;
    public static final long MEDIUM_TIME = 10;
    public static final long LONG_TIME = 20;
    public static final long SUPER_LONG_TIME = 40;
    public static final String DOB_DAY_ID = "uniform-days";
    public static final String DOB_MONTH_ID = "uniform-months";
    public static final String DOB_YEAR_ID = "uniform-years";
    public static final String ADDRESS_STATE_ID = "uniform-id_state";
    public static final String ADDRESS_COUNTRY_ID = "uniform-id_country";
    public static final String COUNTRY_UNITED_STATE = "21";

    public static final String TITLE_MR_FIELD_ID = "id_gender1";
    public static final String TITLE_MRS_FIELD_ID = "id_gender2";
    public static final String PRODUCT_NAME = "product-name";
    public static final String PRODUCT_CURRENT_PRICE = "price product-price";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

}