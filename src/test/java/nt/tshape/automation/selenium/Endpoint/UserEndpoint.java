package nt.tshape.automation.selenium.Endpoint;

import lombok.SneakyThrows;
import nt.tshape.automation.apimanager.BaseEndpoint;
import okhttp3.Request;
import org.testng.Assert;

import java.io.IOException;

public class UserEndpoint extends BaseEndpoint {
    private Request request;
    private String endpointPath = "api/users";

    public UserEndpoint() {
        setEndpointPath(endpointPath);
    }

    @SneakyThrows
    public UserEndpoint callGETRequest() {
        sendGETRequest();
        return this;
    }

    public UserEndpoint addQueryParamNameWithValue(String paramName, String paramValue) {
        addQueryParametersNameWithValue(paramName,paramValue);
        System.out.println("Added new query parameter name [" + paramName + "] with value [" + paramValue + "] to users endpoint");
        return this;
    }

    public UserEndpoint addPathParamWithValue(String paramValue){
        addPathParameterWithValue(paramValue);
        System.out.println("Added new path parameter value [" + paramValue + "] to users endpoint");
        return this;
    }

    public UserEndpoint verifyUserEndpointResponseCodeEqual(int expectedCode) {
        //Verify
        try {
            Assert.assertTrue(isResponseCodeEquals(expectedCode));
            System.out.println("Expected response code [" + expectedCode + "] is equal with actual [" + getResponse().code() + "]");
        } catch (AssertionError e) {
            System.out.println("Expected response code [" + expectedCode + "] is NOT equal with actual [" + getResponse().code() + "]");
        }
        return this;
    }
}
