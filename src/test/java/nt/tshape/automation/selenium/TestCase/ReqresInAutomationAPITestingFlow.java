package nt.tshape.automation.selenium.TestCase;

import nt.tshape.automation.selenium.Endpoint.Users.UserEndpoint;
import nt.tshape.automation.selenium.Endpoint.Users.UserID.UserIdEndpoint;
import nt.tshape.automation.setup.WebDriverTestNGSetupBase;
import org.testng.annotations.Test;

public class ReqresInAutomationAPITestingFlow extends WebDriverTestNGSetupBase {
    private final UserEndpoint userEndpoint = new UserEndpoint(getTestContext());
    private final UserIdEndpoint userIdEndpoint = new UserIdEndpoint(getTestContext());

    @Test
    public void RegresInUserEndpointAPI() {
        userEndpoint
                .addCustomHeader("Content-Type", "application/json")
                .addQueryParamNameWithValue("page", "2")
                .callGETRequest()
                .verifyUserEndpointResponseCodeEqual(200)
                .addUserRequestBody()
                .callPostToUserEndpointRequestWithBodyAndSaveCreatedUserId()
                .verifyUserEndpointResponseCodeEqual(201);
    }
    @Test
    public void RegresInUserIdEndpointAPI(){
        userIdEndpoint
                .callGETRequestBySavedUserId()
                .verifyUserIDEndpointResponseCodeEqual(200)
                .addUserIdRequestBody()
                .changeUserIdRequestFieldNameToValue("name","morpheus1")
                .changeUserIdRequestFieldNameToValue("job","leader1")
                .callUPDATERequestBySavedUserId()
                .verifyUserIDEndpointResponseCodeEqual(201)
                .callDELETERequestBySavedUserId()
                .verifyUserIDEndpointResponseCodeEqual(204);
    }

}
