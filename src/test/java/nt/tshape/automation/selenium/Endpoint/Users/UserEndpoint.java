package nt.tshape.automation.selenium.Endpoint.Users;

import lombok.SneakyThrows;
import nt.tshape.automation.apimanager.UniversalEndpoint;
import nt.tshape.automation.selenium.DataModel.UsersDataModel;
import nt.tshape.automation.selenium.TestContext;

import java.nio.file.Files;
import java.nio.file.Paths;

public class UserEndpoint extends UniversalEndpoint {
    private final String endpointPath = "api/users";
    private final String requestBodyLocation = "src/main/resources/RequestJSON/createUsersJSON.json";

    public UserEndpoint(TestContext testContext) {
        super(testContext);
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


    public UserEndpoint addCustomHeader(String headerName, String headerValue) {
        addHeader(headerName, headerValue, UserEndpoint.class);
        return this;
    }

    @SneakyThrows
    public UserEndpoint addUserRequestBody() {
        createRequestBody(Files.readString(Paths.get(requestBodyLocation)), UserEndpoint.class);
        return this;
    }

    public UserEndpoint changeRequestFieldNameToValue(String fieldName, String fieldValue) {
        updateRequestFieldWithValue(fieldName, fieldValue, UserEndpoint.class);
        return this;
    }

    @SneakyThrows
    public UserEndpoint callPostToUserEndpointRequestWithBodyAndSaveCreatedUserId() {
        sendPostRequestWithBody(UserEndpoint.class);
        UsersDataModel createdUser = super.convertResponseToObject(UsersDataModel.class);
        getTestContext().setAttribute("UserID", createdUser.id);
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
