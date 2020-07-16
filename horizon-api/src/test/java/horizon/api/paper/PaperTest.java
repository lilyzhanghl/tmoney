package horizon.api.paper;

import horizon.api.base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import api.framework.ApiPO;

import static org.hamcrest.Matchers.equalTo;

/**
 * @ClassName: PaperTest
 * @Description: 早报测试
 * @Author: zhzh.yin
 * @Date: 2020-05-13 13:59
 * @Verion: 1.0
 */
@Epic("早报接口")
//@Feature("早报")
public class PaperTest {
    private Paper paper = new Paper();

    @BeforeAll
    public static void setUp() {
        if (BaseTest.notLogin) {
            BaseTest.setUp();
        }
    }

    //todo 改写ExtendWith方法来简化代码，而不是通过每个类编写BeforeAll
    @Test
    @Order(1)
    @Story("查看早报view.do")
    public void testViewPaper() {
        paper.viewPaper(ApiPO.parseParam(Paper.class)).
                then().
                body("retdata.personInfo.name", equalTo("尹珍枝"));
    }

    @Test
    @Story("早报员工详情staff-detail.do")
    public void testGetDetail() {
        paper.getDetail(ApiPO.parseParam(Paper.class)).
                then().
                body("retdata.name", equalTo("尹珍枝"));
    }
}
