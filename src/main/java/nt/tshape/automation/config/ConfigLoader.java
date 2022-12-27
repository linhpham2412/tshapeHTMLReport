package nt.tshape.automation.config;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;

public class ConfigLoader {
    private static JSONObject environmentConfiguration;
    private static JSONObject frameworkConfiguration;

    public static void initConfig() {
        if (environmentConfiguration == null && frameworkConfiguration == null) {
            readConfiguration();
        }
    }
    private static void readConfiguration() {
        InputStream inputStreamEnvironment = ConfigLoader.class.getResourceAsStream("/environment.json");
        InputStream inputStreamConfig = ConfigLoader.class.getResourceAsStream("/config.json");

        environmentConfiguration = new JSONObject(new JSONTokener(inputStreamEnvironment));
        frameworkConfiguration = new JSONObject(new JSONTokener(inputStreamConfig));

        String configEnv = System.getProperty("environment");
        String environment = "";
        if (configEnv != null) {
            environmentConfiguration = environmentConfiguration.getJSONObject(configEnv);
        } else {
            environment = environmentConfiguration.getString("environment");
            environmentConfiguration = environmentConfiguration.getJSONObject(environment);
        }

        System.out.println("Loaded [" + environment + "] environment with url: [" + environmentConfiguration.getString("url") + "]");
    }

    public static String getEnvironment(String key){
        initConfig();
        String config = System.getProperty(key);
        if (config != null) return config;
        return environmentConfiguration.get(key).toString();
    }

    public static String getConfiguration(String key){
        initConfig();
        String config = System.getProperty(key);
        if (config != null) return config;
        return frameworkConfiguration.get(key).toString();
    }

}
