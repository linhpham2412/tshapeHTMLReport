package nt.tshape.automation.selenium.TestCase;

import nt.tshape.automation.selenium.Endpoint.UserEndpoint;
import org.testng.annotations.Test;

public class ReqresInAutomationAPITestingFlow {
    private UserEndpoint userEndpoint = new UserEndpoint();

    @Test
    public void RegresInAutomationUsersAPIFlow(){
        userEndpoint
                .addCustomHeader("X-Requested-With", "XMLHttpRequest")
                .addQueryParamNameWithValue("page","2")
                .callGETRequest()
                .verifyUserEndpointResponseCodeEqual(200)
                .addPathParamWithValue("2")
                .callGETRequest()
                .verifyUserEndpointResponseCodeEqual(200)
                .addRequestBody()
                .changeUserNameTo("morpheus1")
                .changeUserJobTo("leader1")
                .callPostRequestWithBody()
                .verifyUserEndpointResponseCodeEqual(201)
                .verifyResponseUserFieldWithValue("name","morpheus1")
                .verifyResponseUserFieldWithValue("job","leader1")
                .verifyResponseUserFieldExist("id")
                .verifyResponseUserFieldExist("createdAt");
    }
}
