package nt.tshape.automation.selenium.Endpoint;

import lombok.SneakyThrows;
import nt.tshape.automation.apimanager.BaseEndpoint;

import java.nio.file.Files;
import java.nio.file.Paths;

public class UserEndpoint extends BaseEndpoint {
    private final String endpointPath = "api/users";
    private final String requestBodyLocation = "src/main/resources/RequestJSON/createUsersJSON.json";

    public UserEndpoint() {
        setEndpointPath(endpointPath);
    }

    @SneakyThrows
    public UserEndpoint callGETRequest() {
        sendGETRequest(UserEndpoint.class);
        return this;
    }

    public UserEndpoint addQueryParamNameWithValue(String paramName, String paramValue) {
        addQueryParametersNameWithValue(paramName, paramValue, UserEndpoint.class);
        return this;
    }

    public UserEndpoint addPathParamWithValue(String paramValue) {
        addPathParameterWithValue(paramValue, UserEndpoint.class);
        return this;
    }

    public UserEndpoint addCustomHeader(String headerName, String headerValue) {
        addHeader(headerName, headerValue, UserEndpoint.class);
        return this;
    }

    @SneakyThrows
    public UserEndpoint addRequestBody() {
        createRequestBody(Files.readString(Paths.get(requestBodyLocation)), UserEndpoint.class);
        return this;
    }

    public UserEndpoint changeRequestFieldNameToValue(String fieldName, String fieldValue) {
        updateRequestFieldWithValue(fieldName, fieldValue, UserEndpoint.class);
        return this;
    }

    @SneakyThrows
    public UserEndpoint callPostRequestWithBody() {
        sendPostRequestWithBody(UserEndpoint.class);
        return this;
    }

    @SneakyThrows
    public UserEndpoint callPutRequestWithBody() {
        sendPutRequestWithBody(UserEndpoint.class);
        return this;
    }

    @SneakyThrows
    public UserEndpoint callDeleteRequestWithBody() {
        sendDeleteRequest(UserEndpoint.class);
        return this;
    }

    //Verify
    public UserEndpoint verifyUserEndpointResponseCodeEqual(int expectedCode) {
        verifyEndpointResponseCodeEqual(expectedCode, UserEndpoint.class);
        return this;
    }

    public UserEndpoint verifyResponseUserFieldWithValue(String fieldName, String expectedValue) {
        verifyResponseFieldNameWithValue(fieldName, expectedValue, UserEndpoint.class);
        return this;
    }

    public UserEndpoint verifyResponseUserFieldExist(String fieldName) {
        verifyResponseFieldExist(fieldName, UserEndpoint.class);
        return this;
    }
}
