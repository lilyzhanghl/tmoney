package horizon.page.paper;

import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;
import po.PageObjectModel;

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

    @Test
    @Description("查看早报view.do")
    public void testViewPaper() {
        paper.viewPaper(PageObjectModel.parseParam(Paper.class)).
                then().
                body("retdata.personInfo.name", equalTo("尹珍枝"));
    }
}
