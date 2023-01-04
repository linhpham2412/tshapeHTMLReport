package nt.tshape.automation.selenium.Endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import nt.tshape.automation.apimanager.BaseEndpoint;
import nt.tshape.automation.selenium.DataModel.UsersDataModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UserEndpoint extends BaseEndpoint {
    private String endpointPath = "api/users";
    private String requestBodyLocation = "src/main/resources/RequestJSON/createUsersJSON.json";
    private String requestBody;
    UsersDataModel usersDataModel;
    ObjectMapper objectMapper;
    private JSONObject responseObject;

    public UserEndpoint() {
        usersDataModel = new UsersDataModel();
        objectMapper = new ObjectMapper();
        setEndpointPath(endpointPath);
    }

    @SneakyThrows
    public UserEndpoint callGETRequest() {
        sendGETRequest();
        return this;
    }

    public UserEndpoint addQueryParamNameWithValue(String paramName, String paramValue) {
        addQueryParametersNameWithValue(paramName, paramValue);
        System.out.println("Added new query parameter name [" + paramName + "] with value [" + paramValue + "] to users endpoint");
        return this;
    }

    public UserEndpoint addPathParamWithValue(String paramValue) {
        addPathParameterWithValue(paramValue);
        System.out.println("Added new path parameter value [" + paramValue + "] to users endpoint");
        return this;
    }

    public UserEndpoint addCustomHeader(String headerName, String headerValue) {
        addHeader(headerName, headerValue);
        return this;
    }

    @SneakyThrows
    public UserEndpoint addRequestBody() {
        String jsonBody = Files.readString(Paths.get(requestBodyLocation));
        usersDataModel = objectMapper.readValue(jsonBody, UsersDataModel.class);
        requestBody = createRequestBody(jsonBody);
        return this;
    }

    public UserEndpoint changeUserNameTo(String nameValue){
        usersDataModel.setName(nameValue);
        return this;
    }

    public UserEndpoint changeUserJobTo(String jobValue){
        usersDataModel.setJob(jobValue);
        return this;
    }

    @SneakyThrows
    public UserEndpoint callPostRequestWithBody() {
        requestBody = new ObjectMapper().writeValueAsString(usersDataModel);
        sendPostRequestWithBody(requestBody);
        responseObject = convertResponseToObject();
        return this;
    }

    //Verify
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
    public UserEndpoint verifyResponseUserFieldWithValue(String fieldName, String expectedValue){
        //Act
        String actualValue = String.valueOf(responseObject.get(fieldName));

        //Verify
        try {
            Assert.assertEquals(actualValue,expectedValue);
            System.out.println("Expected response field [" + fieldName + "] with value: ["+expectedValue+"] is equal with actual [" + actualValue + "]");
        } catch (Exception e) {
            System.out.println("Expected response field [" + fieldName + "] with value: ["+expectedValue+"] is NOT equal with actual [" + actualValue + "]");
        }
        return this;
    }
    public UserEndpoint verifyResponseUserFieldExist(String fieldName){
        //Verify
        try {
            Assert.assertNotEquals(responseObject.get(fieldName), null);
            System.out.println("Expected response field [" + fieldName + "] is existed.");
        } catch (JSONException e) {
            System.out.println("Expected response field [" + fieldName + "] is NOT existed.");
        }
        return this;
    }
}
