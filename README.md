### 项目实现
- 构建工具： maven
- 接口调用： rest-assured
- 测试框架： junit5
- 测试用例加工： jacksonYaml,mustache
- 测试报告优化： allure

### 用例编写方式
- src/test/java/api/framework/xxx 定义业务调用 Example.java
- src/test/resources/apiyaml/xxx 编写对应的调用yaml,json文件。
- 示例：
   yaml格式：
``` yaml 
    name: 登录
    describle: 登录相关测试用例
    contents:
      - name: login
        url: /caizhi_miniapi/index/auth.do
        method: get
        requestParam:
          userId: YinZhenZhi
          corpId: ww8c83d949a80b562d
      - name: testLoginFailure
        url: /caizhi_miniapi/index/auth.do
        method: get
        requestParam:
          userId: zhangsan
          corpId: ww8c83d949a80b562d
```
- 接口中需要传输的json
``` java
{
  "id": "f09a04b775974f98bee9aaed8c492d24",
  "name": 
}
```
- .java断言
``` java
package api.framework;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;
import static org.junit.jupiter.params.provider.Arguments.arguments;
@Execution(CONCURRENT)  //CONCURRENT表示支持多线程
@Slf4j
public class MorPaperTest {
    private static ApiPO basePO=new ApiPO();
    private static List<ApiContent> list = basePO.load("src/test/resources/apiyaml/paper.yaml").getContents();
    @BeforeAll
    static void beforeAll(){
        LoginHelper.login();
    }
    @ParameterizedTest
    @MethodSource("paperYamlProvider")
    @DisplayName("早报")
    void testPaperYaml(ApiContent api) {
        assertTrue(ApiPO.parseApi(api).path("ret").equals(0));
        /*assertAll(
                ()->assertTrue(ApiPO.parseApi(api).path("ret").equals(0))
        );*/
    }

    static Stream<Arguments> paperYamlProvider() {
        return Stream.of(
                arguments(list.get(0)),
                arguments(list.get(1)),
                arguments(list.get(2)),
                arguments(list.get(3)),
                arguments(list.get(4)),
                arguments(list.get(5))
        );
    }
}
```
- src/test/java/com.example.page/xxx 定义测试调用 ExampleTest.java。
``` java
    @Test
    public void testLoginSuccess()   {
        HashMap map = PageObjectModel.parseParam(BaseAPI.class);
        api.login(map)
                .then()
                .body("ret", equalTo(0));
    }
```
### yaml配置编写规范
- 参数名和参数值之间需要用空格隔开
### 执行
``` shell
mvn clean test
allure serve target/allure-results
allure report
```
report地址： target/site/allure-maven-plugin/index.html
### 待完善
- 报告自带restAssured日志
- 报告发送邮件
- 断言结合mybatis
- 代码优化：yaml传参枚举，异常加工，配置文件路径硬编码，json文件路径配置
