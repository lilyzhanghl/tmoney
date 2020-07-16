package auto.framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class BasePO {
    public void click(HashMap<String, Object> map) {
        System.out.println(map);
//        driver.findElement(by).click
    }

    public void find() {

    }

    public void sendKeys(HashMap<String, Object> map) {
        System.out.println("sendKeys");
        System.out.println(map);
    }

    public void action(HashMap<String, Object> map) {
        System.out.println("action");
        System.out.println(map);
    }


    public void getText() {

    }

    public void run(Model model) {
        model.steps.stream().forEach(m -> {
            if (m.keySet().contains("click")) {
                HashMap<String, Object> by = (HashMap<String, Object>) m.get("click");
                click(by);
            }
            if (m.containsKey("sendKeys")) {
                sendKeys(m);
            }

            if (m.containsKey("action")) {
                action(m);
            }
        });

    }
    public List<Content> getContent(Model model){
        return model.contents;
    }
    public Model load(String path) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Model model = null;
        try {
            model = mapper.readValue(
//                    BasePage.class.getResourceAsStream(path),
                    new File(path),
                    Model.class
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }
}
