### 项目实现
- 构建工具： maven
- 接口调用： rest-assured
- 测试框架： junit5
- 测试用例加工： jacksonYaml,mustache
- 测试报告优化： allure
- github地址：https://github.com/ZhzhIn/tmoney
### 工具准备
- 代码编辑工具：ideaJ
- 开发语言： java
- 代码版本管理工具：git
- 构建工具： maven
### 建议
- idea安装插件：
    - lombok
    - .gitignore
    - rainbow brackets
    - 阿里巴巴代码规范
- idea准备工作：
    - IntelliJ IDEA为类和方法自动添加注释
### 接口框架代码
- module ： horizon-api
### 官方文档
- rest-assured: http://rest-assured.io/
- junit5: https://junit.org/junit5/docs/current/user-guide/
- allure2: http://allure.qatools.ru/
- mustache: http://mustache.github.io/ 
- jackson: https://github.com/FasterXML/jackson

### 环境搭建
1.安装java编译和执行环境
2.安装maven
3.git clone代码到idea
4.命令行进入tmoney/horizon-api,执行mvn clean test allure:report allure:serve
5.点击命令行中的网址，查看报告
6.jenkins设置，略
### 项目代码结构
- 还没画图，以前的不能用了。
### 用例编写方式
- src/test/java/api/framework/xxx 定义业务调用 Example.java
- src/test/resources/apiyaml/xxx 编写对应的调用yaml,json文件。
- 示例：
   yaml格式：
``` yaml 
    name: 登录
    describle: 登录相关测试用例
    contents:
      login:
        url: /caizhi_miniapi/index/auth.do
        method: get
        requestParam:
          userId: YinZhenZhi
          corpId: ww8c83d949a80b562d
```
- 接口中需要传输的json
``` java
{
  "id": "f09a04b775974f98bee9aaed8c492d24",
  "name": xxx
}
```
- .java断言
``` java

public class MorPaperTest {
    ApiModel model = ApiModel.load("src/test/resources/apiyaml/paper.yaml");
    @BeforeAll
    static void beforeAll() {
        LoginHelper.login();
    }

    @BeforeEach
    void setUp(){
    }

    @ParameterizedTest(name ="早报接口：{0}-{index}")
    @CsvSource({
            "getDetail,ret,0",
            "getDetail,ret,0"
    })
    @Story("csv测试detail.do接口")
    void testPaper(String apiName, String responsePath, Integer expectValue) throws InvocationTargetException, IllegalAccessException {
        assertTrue(model.run(apiName)
                .path(responsePath).equals(expectValue));
    }

    @ParameterizedTest(name ="早报接口：{0}-{index}")
    @MethodSource("paperYamlProvider")
    @Story("一大堆早报接口")
    void testPaperYaml(String apiName, HashMap<ManuData, HashMap<String, String>> map, String responsePath, Integer expectValue) {
        assertTrue(model.run(apiName, map)
                .path(responsePath)
                .equals(expectValue));
        /*assertAll(
                ()->assertTrue(ApiPO.parseApi(api).path("ret").equals(0))
        );*/
    }

    static Stream<Arguments> paperYamlProvider() {
        HashMap<ManuData, HashMap<String, String>> map1 = new HashMap<>();
        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("id", "f09a04b775974f98bee9aaed8c492d24");
        map1.put(ManuData.REQUEST_PARAM, requestParam);

        HashMap<ManuData, HashMap<String, String>> map2 = new HashMap<>();
        HashMap<String,String> jsonFileName = new HashMap<>();
        jsonFileName.put("jsonFileName","viewPaper");
        map2.put(ManuData.JSON_FILE_NAME,jsonFileName);
        return Stream.of(
                arguments("getDetail", null, "ret", 0),
                arguments("getDetail2" , map1, "ret", 0),
                arguments("getDetail" , null, "ret", 0),
                arguments("getDetail" , map2, "ret", 0),
                arguments("viewPaper",null, "ret", 0),
                arguments("filter",null, "ret", 0),
                arguments("paperConfigAPI",null, "ret", 0),
                arguments("paperSaveAPI",null, "ret", 0),
                arguments("paperListAPI",null, "ret", 0)
        );
    }
    @ParameterizedTest(name = "接口-{0}-{index}")
    @MethodSource("oneApi")
    @Story("接口的另一种写法")
    void testPaper2(String apiName,HashMap map, String str, BaseMatcher matcher) {
        Object result = model.run(apiName, map).getBody().jsonPath().get(str);
        assertThat(result, matcher);
    }
    static Stream<Arguments> oneApi() {
        HashMap<ManuData, HashMap<String, String>> map1 = new HashMap<>();
        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("id", "f09a04b775974f98bee9aaed8c492d24");
        map1.put(ManuData.REQUEST_PARAM, requestParam);

        HashMap<ManuData, HashMap<String, String>> map2 = new HashMap<>();
        HashMap<String,String> jsonFileName = new HashMap<>();
        jsonFileName.put("jsonFileName","viewPaper");
        map2.put(ManuData.JSON_FILE_NAME,jsonFileName);
        return Stream.of(
                arguments("getDetail",map1, "ret",is(0)),
                arguments("getDetail",map2, "ret",is(0))
        );

    }
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
- 代码优化：异常加工，配置文件路径硬编码，json文件路径配置
