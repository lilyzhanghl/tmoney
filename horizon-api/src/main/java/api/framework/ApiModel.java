package api.framework;

import auto.framework.Model;

import java.util.HashMap;
import java.util.List;

public class ApiModel extends Model {
    public String name ;
    public String describle;
    public List<ApiContent> contents;
    public List<HashMap<String, Object>> steps;
}
