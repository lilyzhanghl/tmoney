package horizon.api.paper;
import horizon.api.base.BaseTest;
import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import apiobject.APIObjectModel;
import static org.hamcrest.Matchers.equalTo;

/**
 * @ClassName: PaperTest
 * @Description: 早报测试
 * @Author: zhzh.yin
 * @Date: 2020-05-13 13:59
 * @Verion: 1.0
 */
public class PaperTest {
    private Paper paper = new Paper();
/*    @BeforeAll
    public static void setUp(){
        BaseTest.testLoginSuccess();
    }*/

    @Test
    @Description("查看早报view.do")
    public void testViewPaper() {
        paper.viewPaper(APIObjectModel.parseParam(Paper.class)).
                then().
                body("retdata.personInfo.name", equalTo("尹珍枝"));
    }
    @Test
    @Description("早报员工详情staff-detail.do")
    public void testGetDetail() {
        paper.getDetail(APIObjectModel.parseParam(Paper.class)).
                then().
                body("retdata.name", equalTo("尹珍枝"));
    }
}
