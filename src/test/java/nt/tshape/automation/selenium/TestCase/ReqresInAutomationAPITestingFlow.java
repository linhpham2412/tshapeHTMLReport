package nt.tshape.automation.selenium.TestCase;

import nt.tshape.automation.selenium.Endpoint.UserEndpoint;
import org.testng.annotations.Test;

public class ReqresInAutomationAPITestingFlow {
    private UserEndpoint userEndpoint = new UserEndpoint();

    @Test
    public void RegresInAutomationUsersAPIFlow(){
        userEndpoint
                .addQueryParamNameWithValue("page","2")
                .callGETRequest()
                .verifyUserEndpointResponseCodeEqual(200)
                .addPathParamWithValue("2")
                .callGETRequest()
                .verifyUserEndpointResponseCodeEqual(200);

    }
}
