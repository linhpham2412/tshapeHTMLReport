package nt.tshape.automation.apimanager;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import nt.tshape.automation.config.ConfigLoader;
import nt.tshape.automation.selenium.Constant;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static nt.tshape.automation.reportmanager.HTMLReporter.getCurrentReportNode;
import static nt.tshape.automation.reportmanager.HTMLReporter.getHtmlReporter;

public class BaseEndpoint {
    private final String baseHost;
    private final OkHttpClient apiClient;
    private final HashMap<String, String> parameters;
    List<String> endpointPathSegment;
    List<String> paramPathSegment;
    ObjectMapper objectMapper;
    private Response response;
    private Headers headers;
    private String responseBody;
    private JSONObject requestJSON;


    public BaseEndpoint() {
        baseHost = ConfigLoader.getEnvironment("apiHost");
        OkHttpClient client = new OkHttpClient();
        objectMapper = new ObjectMapper();
        apiClient = client.newBuilder()
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();
        parameters = new HashMap<>();
        endpointPathSegment = new ArrayList<>();
        paramPathSegment = new ArrayList<>();
        headers = new Headers.Builder()
                .add("Content-Type", "application/json")
                .add("Accept", "*/*")
                .add("Connection", "keep-alive")
                .build();
        System.out.println("Set up Base endpoint [" + baseHost + "] successfully!");
    }

    protected void setEndpointPath(String endpointPath) {
        endpointPathSegment = List.of(endpointPath.split("/"));
        System.out.println("Set up endpoint [" + endpointPath + "] successfully!");
    }

    protected <T> void addHeader(String headerName, String headerValue, Class<T> objectClass) {
        Headers currentHeader = headers;
        headers = new Headers.Builder()
                .add(headerName, headerValue)
                .addAll(currentHeader)
                .build();
        System.out.println("Added new header [" + headerName + "] with value [" + headerValue + "] to [" + objectClass.getSimpleName() + "]");
        getCurrentReportNode()
                .pass("Added new header [" + headerName + "] with value [" + headerValue + "] to [" + objectClass.getSimpleName() + "]");
    }

    protected Response getResponse() {
        return response;
    }

    private Boolean isResponseCodeEquals(int responseCode) {
        return response.code() == responseCode;
    }

    protected <T> void addQueryParametersNameWithValue(String paramName, String paramValue, Class<T> objectClass) {
        parameters.put(paramName, paramValue);
        System.out.println("Added new Query Parameter [" + paramName + "] with value [" + paramValue + "] to [" + objectClass.getSimpleName() + "]");
        getCurrentReportNode()
                .pass("Added new Query Parameter [" + paramName + "] with value [" + paramValue + "] to [" + objectClass.getSimpleName() + "]");
    }

    protected <T> void addPathParameterWithValue(String paramValue, Class<T> objectClass) {
        paramPathSegment.add(paramValue);
        System.out.println("Added new Path Parameter with value [" + paramValue + "] to [" + objectClass.getSimpleName() + "]");
        getCurrentReportNode()
                .pass("Added new Path Parameter with value [" + paramValue + "] to [" + objectClass.getSimpleName() + "]");
    }

    @SneakyThrows
    protected <T> void createRequestBody(String jsonBody, Class<T> objectClass) {
        requestJSON = convertStringToJSONObject(jsonBody);
        System.out.println("Added new body [" + jsonBody + "] to [" + objectClass.getSimpleName() + "]");
        getCurrentReportNode()
                .pass("Added new body [" + jsonBody + "] to [" + objectClass.getSimpleName() + "]");
    }

    protected <T> void updateRequestFieldWithValue(String fieldName, String fieldValue, Class<T> objectClass) {
        requestJSON.put(fieldName, fieldValue);
        System.out.println("Changed endpoint [" + objectClass.getSimpleName() + "] request body field [" + fieldName + "] value to [" + fieldValue + "]");
        getCurrentReportNode()
                .pass("Changed endpoint [" + objectClass.getSimpleName() + "] request body field [" + fieldName + "] value to [" + fieldValue + "]");
    }

    private void clearAllParams() {
        paramPathSegment.clear();
        parameters.clear();
    }

    @SneakyThrows
    private String buildEndpointURL(HttpUrl.Builder urlBuilder) {
        endpointPathSegment.stream().forEach(urlBuilder::addPathSegment);
        paramPathSegment.stream().forEach(urlBuilder::addPathSegment);
        parameters.forEach(urlBuilder::addQueryParameter);
        return urlBuilder.toString();
    }

    @SneakyThrows
    protected <T> void sendGETRequest(Class<T> objectClass) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseHost).newBuilder();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        try {
            Request request = new Request.Builder()
                    .url(buildEndpointURL(urlBuilder))
                    .headers(headers)
                    .build();
            LocalDateTime startRequestTime = LocalDateTime.now();
            response = apiClient.newCall(request).execute();
            responseBody = response.body().string();
            LocalDateTime endRequestTime = LocalDateTime.now();
            clearAllParams();
            System.out.println("Send GET request to endpoint [" + objectClass.getSimpleName() + "] with URL: [" + urlBuilder + "] successfully!");
            System.out.println("Request headers: [" + request.headers() + "]");
            System.out.println("Response headers: [" + response.headers() + "]");
            System.out.println("Response body: [" + responseBody + "]");
            int startTime = startRequestTime.getSecond()*1000+startRequestTime.getNano()/1000000;
            int endTime = endRequestTime.getSecond()*1000+endRequestTime.getNano()/1000000;
            System.out.println("Request time: [" + (endTime-startTime) + "ms]");
            if (requestJSON == null) requestJSON = new JSONObject();
            getCurrentReportNode()
                    .pass(getHtmlReporter().markupTextWithColor("Send GET request to endpoint [" + objectClass.getSimpleName() + "] with URL: [" + urlBuilder + "] successfully!", ExtentColor.GREEN));
            getCurrentReportNode().info(getHtmlReporter().markupRequestInfoTable("GET", String.valueOf(urlBuilder), requestJSON.toString(), String.valueOf((endTime-startTime)), responseBody, String.valueOf(response.code())));
        } catch (IOException e) {
            System.out.println("Failed to send GET request to endpoint[" + urlBuilder + "]");
            getCurrentReportNode()
                    .fail("Failed to send GET request to endpoint[" + urlBuilder + "]");
        }
    }

    protected <T> void sendPostRequestWithBody(Class<T> objectClass) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseHost).newBuilder();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        try {
            Request request = new Request.Builder()
                    .url(buildEndpointURL(urlBuilder))
                    .headers(headers)
                    .post(RequestBody.create(Constant.JSON, requestJSON.toString()))
                    .build();
            LocalDateTime startRequestTime = LocalDateTime.now();
            response = apiClient.newCall(request).execute();
            responseBody = response.body().string();
            LocalDateTime endRequestTime = LocalDateTime.now();
            clearAllParams();
            System.out.println("Send POST request to endpoint [" + objectClass.getSimpleName() + "] with URL: [" + urlBuilder + "] successfully!");
            System.out.println("Request headers: [" + request.headers() + "]");
            System.out.println("Request body: [" + requestJSON.toString() + "]");
            int startTime = startRequestTime.getSecond()*1000+startRequestTime.getNano()/1000000;
            int endTime = endRequestTime.getSecond()*1000+endRequestTime.getNano()/1000000;
            System.out.println("Request time: [" + (endTime-startTime) + "ms]");
            getCurrentReportNode()
                    .pass(getHtmlReporter().markupTextWithColor("Send POST request to endpoint [" + objectClass.getSimpleName() + "] with URL: [" + urlBuilder + "] successfully!", ExtentColor.BLUE));
            getCurrentReportNode().info(getHtmlReporter().markupRequestInfoTable("POST", String.valueOf(urlBuilder), requestJSON.toString(), String.valueOf((endTime-startTime)), responseBody, String.valueOf(response.code())));
        } catch (IOException e) {
            System.out.println("Failed to send POST request to endpoint[" + urlBuilder + "]");
            getCurrentReportNode()
                    .fail("Failed to send POST request to endpoint[" + urlBuilder + "]");
        }
    }

    @SneakyThrows
    protected <T> T convertResponseToObject(Class<T> objectClass) {
        Gson gson = new Gson();
        return objectClass.cast(gson.fromJson(responseBody, objectClass));
    }

    protected JSONObject convertResponseToJSONObject() {
        return new JSONObject(responseBody);
    }

    protected JSONObject convertStringToJSONObject(String valueToConvert) {
        return new JSONObject(valueToConvert);
    }

    @SneakyThrows
    protected <T> T convertStringToObject(String stringToConvert, Class<T> objectClass) {
        return objectClass.cast(objectMapper.readValue(stringToConvert, objectClass));
    }

    @SneakyThrows
    protected String convertObjectToString(Object object) {
        return new ObjectMapper().writeValueAsString(object);
    }

    //Verify
    protected <T> void verifyEndpointResponseCodeEqual(int expectedCode, Class<T> objectClass) {
        //Verify
        try {
            Assert.assertTrue(isResponseCodeEquals(expectedCode));
            System.out.println("Expected endpoint [" + objectClass.getSimpleName() + "] has response code [" + expectedCode + "] is equal with actual [" + getResponse().code() + "]");
            getCurrentReportNode()
                    .pass("Expected endpoint [" + objectClass.getSimpleName() + "] has response code [" + expectedCode + "] is equal with actual [" + getResponse().code() + "]");
        } catch (AssertionError e) {
            System.out.println("Expected endpoint [" + objectClass.getSimpleName() + "] has response code [" + expectedCode + "] is NOT equal with actual [" + getResponse().code() + "]");
            getCurrentReportNode()
                    .fail("Expected endpoint [" + objectClass.getSimpleName() + "] has response code [" + expectedCode + "] is NOT equal with actual [" + getResponse().code() + "]");
        }
    }

    protected <T> void verifyResponseFieldNameWithValue(String fieldName, String expectedValue, Class<T> objectClass) {
        //Act
        String actualValue = String.valueOf(convertResponseToJSONObject().get(fieldName));

        //Verify
        try {
            Assert.assertEquals(actualValue, expectedValue);
            System.out.println("Expected response of endpoint [" + objectClass.getSimpleName() + "] field [" + fieldName + "] with value: [" + expectedValue + "] is equal with actual [" + actualValue + "]");
            getCurrentReportNode()
                    .pass("Expected response of endpoint [" + objectClass.getSimpleName() + "] field [" + fieldName + "] with value: [" + expectedValue + "] is equal with actual [" + actualValue + "]");
        } catch (Exception e) {
            System.out.println("Expected response of endpoint [" + objectClass.getSimpleName() + "] field [" + fieldName + "] with value: [" + expectedValue + "] is NOT equal with actual [" + actualValue + "]");
            getCurrentReportNode()
                    .fail("Expected response of endpoint [" + objectClass.getSimpleName() + "] field [" + fieldName + "] with value: [" + expectedValue + "] is NOT equal with actual [" + actualValue + "]");
        }
    }

    protected <T> void verifyResponseFieldExist(String fieldName, Class<T> objectClass) {
        //Verify
        try {
            Assert.assertNotEquals(convertResponseToJSONObject().get(fieldName), null);
            System.out.println("Expected response of endpoint [" + objectClass.getSimpleName() + "] field [" + fieldName + "] is existed.");
            getCurrentReportNode()
                    .pass("Expected response of endpoint [" + objectClass.getSimpleName() + "] field [" + fieldName + "] is existed.");
        } catch (JSONException e) {
            System.out.println("Expected response of endpoint [" + objectClass.getSimpleName() + "] field [" + fieldName + "] is NOT existed.");
            getCurrentReportNode()
                    .fail("Expected response of endpoint [" + objectClass.getSimpleName() + "] field [" + fieldName + "] is NOT existed.");
        }
    }
}
