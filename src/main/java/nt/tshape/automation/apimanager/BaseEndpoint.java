package nt.tshape.automation.apimanager;

import lombok.SneakyThrows;
import nt.tshape.automation.config.ConfigLoader;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseEndpoint {
    private String baseHost;
    private OkHttpClient apiClient;
    private Response response;
    private HashMap<String, String> parameters;
    List<String> endpointPathSegment;
    List<String> paramPathSegment;


    public BaseEndpoint() {
        baseHost = ConfigLoader.getEnvironment("apiHost");
        apiClient = new OkHttpClient();
        parameters = new HashMap<>();
        endpointPathSegment = new ArrayList<>();
        paramPathSegment = new ArrayList<>();
    }

    public void setEndpointPath(String endpointPath) {
        endpointPathSegment = List.of(endpointPath.split("/"));
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
                    .build();
            response = apiClient.newCall(request).execute();
            clearAllParams();
            System.out.println("Send GET request to endpoint [" + urlBuilder.toString() + "] successfully!");
            System.out.println("Response headers: [" + response.headers() + "]");
            System.out.println("Response body: [" + response.body().string() + "]");
        } catch (IOException e) {
            System.out.println("Failed to send GET request to endpoint[" + urlBuilder.toString() + "]");
        }
        return response;
    }
}
