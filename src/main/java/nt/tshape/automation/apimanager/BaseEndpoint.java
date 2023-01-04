package nt.tshape.automation.apimanager;

import lombok.SneakyThrows;
import nt.tshape.automation.config.ConfigLoader;
import nt.tshape.automation.selenium.Constant;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BaseEndpoint {
    private String baseHost;
    private OkHttpClient apiClient;
    private Response response;
    private HashMap<String, String> parameters;
    List<String> endpointPathSegment;
    List<String> paramPathSegment;
    private Headers headers;
    private String requestBody;
    private String responseBody;


    public BaseEndpoint() {
        baseHost = ConfigLoader.getEnvironment("apiHost");
        OkHttpClient client = new OkHttpClient();
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

    public void setEndpointPath(String endpointPath) {
        endpointPathSegment = List.of(endpointPath.split("/"));
        System.out.println("Set up endpoint [" + endpointPath + "] successfully!");
    }

    public void addHeader(String headerName, String headerValue) {
        Headers currentHeader = headers;
        headers = new Headers.Builder()
                .add(headerName, headerValue)
                .addAll(currentHeader)
                .build();
    }

    public Response getResponse() {
        return response;
    }

    public Boolean isResponseCodeEquals(int responseCode) {
        return (response.code() == responseCode) ? true : false;
    }

    public void addQueryParametersNameWithValue(String paramName, String paramValue) {
        parameters.put(paramName, paramValue);
    }

    public void addPathParameterWithValue(String paramValue) {
        paramPathSegment.add(paramValue);
    }

    @SneakyThrows
    public String createRequestBody(String jsonBody) {
        requestBody = jsonBody;
        return requestBody;
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
    public Response sendGETRequest() {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseHost).newBuilder();
        try {
            Request request = new Request.Builder()
                    .url(buildEndpointURL(urlBuilder))
                    .headers(headers)
                    .build();
            response = apiClient.newCall(request).execute();
            responseBody = response.body().string();
            clearAllParams();
            System.out.println("Send GET request to endpoint [" + urlBuilder.toString() + "] successfully!");
            System.out.println("Request headers: [" + request.headers() + "]");
            System.out.println("Response headers: [" + response.headers() + "]");
            System.out.println("Response body: [" + responseBody + "]");
        } catch (IOException e) {
            System.out.println("Failed to send GET request to endpoint[" + urlBuilder.toString() + "]");
        }
        return response;
    }

    public Response sendPostRequestWithBody(String requestBody){
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseHost).newBuilder();
        try {
            Request request = new Request.Builder()
                    .url(buildEndpointURL(urlBuilder))
                    .headers(headers)
                    .post(RequestBody.create(Constant.JSON, requestBody))
                    .build();
            response = apiClient.newCall(request).execute();
            responseBody = response.body().string();
            clearAllParams();
            System.out.println("Send POST request to endpoint [" + urlBuilder.toString() + "] successfully!");
            System.out.println("Request headers: [" + request.headers() + "]");
            System.out.println("Request body: [" + requestBody + "]");
            System.out.println("Response headers: [" + response.headers() + "]");
            System.out.println("Response body: [" + responseBody + "]");
        } catch (IOException e) {
            System.out.println("Failed to send POST request to endpoint[" + urlBuilder.toString() + "]");
        }
        return response;
    }

    @SneakyThrows
    public JSONObject convertResponseToObject(){
        return new JSONObject(responseBody);
    }
}
