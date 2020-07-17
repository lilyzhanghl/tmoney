package api.framework;

import auto.framework.BasePO;
import auto.framework.Model;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ApiPOTest {
    private static BasePO basePO;
    @BeforeAll
    static void beforeAll(){
        basePO = new ApiPO();
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getContent() {
        List<ApiContent> apiList = basePO.load("src/test/resources/apiyaml/base.yaml")
                .getContents();
        System.out.println(apiList.get(0).toString());

    }

//    @Test
    void load() throws JsonProcessingException {
        Model model=basePO.load("src/test/resources/apiyaml/base.yaml");
        System.out.println(model.toString());
    }
}
