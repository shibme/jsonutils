package me.shib.java.lib.jsonconfig;

import me.shib.java.lib.utils.JsonUtil;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public final class JsonConfig {

    private static Map<String, JsonConfig> configMap = new HashMap<>();
    private static JsonUtil jsonUtil = new JsonUtil();

    private File file;

    private JsonConfig(File file) {
        this.file = file;
    }

    public static synchronized JsonConfig getJsonConfig(File file) {
        JsonConfig jsonConfig = configMap.get(file.getAbsolutePath());
        if (jsonConfig == null) {
            jsonConfig = new JsonConfig(file);
            configMap.put(file.getAbsolutePath(), jsonConfig);
        }
        return jsonConfig;
    }

    public static synchronized JsonConfig getNewJsonConfig(File file) {
        JsonConfig jsonConfig = new JsonConfig(file);
        configMap.put(file.getAbsolutePath(), jsonConfig);
        return jsonConfig;
    }

    private String readFromFile() throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            contentBuilder.append(line).append("\n");
        }
        br.close();
        return contentBuilder.toString();
    }

    public <T> T get(Class<T> classOfT) throws IOException {
        return jsonUtil.fromJson(readFromFile(), classOfT);
    }

    public <T> T get(Type typeOfT) throws IOException {
        return jsonUtil.fromJson(readFromFile(), typeOfT);
    }

    private void writeToFile(String json) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(file);
        pw.append(json);
        pw.close();
    }

    public void put(Object object) throws FileNotFoundException {
        writeToFile(jsonUtil.toPrettyJson(object));
    }

}
