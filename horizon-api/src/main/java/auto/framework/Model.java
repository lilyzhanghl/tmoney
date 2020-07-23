package auto.framework;

import api.framework.ApiContent;

import java.util.HashMap;
import java.util.List;

public class Model {
    public String name ;
    public String describle;
    public List<ApiContent> contents;
    public List<HashMap<String, Object>> steps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrible() {
        return describle;
    }

    public void setDescrible(String describle) {
        this.describle = describle;
    }

    public List<ApiContent> getContents() {
        return contents;
    }

    public void setContents(List<ApiContent> contents) {
        this.contents = contents;
    }

    public List<HashMap<String, Object>> getSteps() {
        return steps;
    }

    public void setSteps(List<HashMap<String, Object>> steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "Model{" +
                "name='" + name + '\'' +
                ", describle='" + describle + '\'' +
                ", contents=" + contents +
                ", steps=" + steps +
                '}';
    }
}
