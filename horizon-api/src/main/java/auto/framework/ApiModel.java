package auto.framework;

import lombok.Data;

import java.util.HashMap;
@Data
public class ApiModel {
    String url;
    String method;
    HashMap<String, Object> headers;
    String connection;
    String host;
    String params;
    String json;
    String jsonFile;}