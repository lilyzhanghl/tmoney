package commons;

import java.util.HashMap;


/**
 * @ClassName: ExeConfig
 * @Description: execute config
 * @Author: zhzh.yin
 * @Date: 2020-04-15 17:53
 * @Verion: 1.0
 */
public class ExeConfig {
    private String paperDetailAPI = "/caizhi_miniapi/api/applet/staffCard/detail";
    private String paperViewAPI = "/caizhi_miniapi/api/applet/morning/paper/view.do";
    private String paperFilterListAPI = "/caizhi_miniapi/api/applet/banner/filter_list.do";
    private String paperConfigAPI = "/caizhi_miniapi/api/applet/finance/config.do";
    private String paperSaveAPI = "/caizhi_miniapi/api/applet/snapshot/save.do";
    private String paperListAPI = "/caizhi_miniapi/relate/product/v2/list";

    public String getPaperDetailAPI() {
        return paperDetailAPI;
    }

    public void setPaperDetailAPI(String paperDetailAPI) {
        this.paperDetailAPI = paperDetailAPI;
    }

    public String getPaperViewAPI() {
        return paperViewAPI;
    }

    public void setPaperViewAPI(String paperViewAPI) {
        this.paperViewAPI = paperViewAPI;
    }

    public String getPaperFilterListAPI() {
        return paperFilterListAPI;
    }

    public void setPaperFilterListAPI(String paperFilterListAPI) {
        this.paperFilterListAPI = paperFilterListAPI;
    }

    public String getPaperConfigAPI() {
        return paperConfigAPI;
    }

    public void setPaperConfigAPI(String paperConfigAPI) {
        this.paperConfigAPI = paperConfigAPI;
    }

    public String getPaperSaveAPI() {
        return paperSaveAPI;
    }

    public void setPaperSaveAPI(String paperSaveAPI) {
        this.paperSaveAPI = paperSaveAPI;
    }

    public String getPaperListAPI() {
        return paperListAPI;
    }

    public void setPaperListAPI(String paperListAPI) {
        this.paperListAPI = paperListAPI;
    }

    private static ExeConfig config;
    private ExeConfig(){
    }
    public static ExeConfig getInstance(){
        if(config==null){
            config=new ExeConfig();
        }
        return config;
    }
    private String  oploginapi="https://test.tengmoney.com/caizhi_mkto/index/auth.do";

    public String getOploginapi() {
        return oploginapi;
    }

    public void setOploginapi(String oploginapi) {
        this.oploginapi = oploginapi;
    }

    private String environment="test";
    private String type="ui";
    private String scenes="caizhi_op";
    private String env=
            "test";
    private String host=
            "https://test.tengmoney.com";
    private String staffid=
            "88d41b05dec44f9cbbbdbcd25ea742b5";
    private String selProdListAPI=
            "1";
    private String updateProdListAPI=
            "1";
    private String viewPaperAPI=
            "1";
    private String userId = "mr.joker";
    private String corpId ="ww8c83d949a80b562d";
    String miniURL = "https://test.tengmoney.com/caizhi_miniapi/index/auth.do";


    public static ExeConfig getConfig() {
        return config;
    }

    public static void setConfig(ExeConfig config) {
        ExeConfig.config = config;
    }

    public String getMiniURL() {
        return miniURL;
    }

    public void setMiniURL(String miniURL) {
        this.miniURL = miniURL;
    }

    public String getManageURL() {
        return manageURL;
    }

    public void setManageURL(String manageURL) {
        this.manageURL = manageURL;
    }

    String manageURL = "https://test.tengmoney.com/caizhi_manage/index/auth.do";
    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public void setSelProdListAPI(String selProdListAPI) {
        this.selProdListAPI = selProdListAPI;
    }

    public void setUpdateProdListAPI(String updateProdListAPI) {
        this.updateProdListAPI = updateProdListAPI;
    }

    public void setViewPaperAPI(String viewPaperAPI) {
        this.viewPaperAPI = viewPaperAPI;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCaizhi_op(String caizhi_op) {
        this.caizhi_op = caizhi_op;
    }

    public void setChromedrvierpath(String chromedrvierpath) {
        this.chromedrvierpath = chromedrvierpath;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getEnv() {
        return env;
    }

    public String getHost() {
        return host;
    }

    public String getStaffid() {
        return staffid;
    }

    public String getSelProdListAPI() {
        return selProdListAPI;
    }

    public String getUpdateProdListAPI() {
        return updateProdListAPI;
    }

    public String getViewPaperAPI() {
        return viewPaperAPI;
    }

    public String getCaizhi_op() {
        return caizhi_op;
    }

    public String getChromedrvierpath() {
        return chromedrvierpath;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    private String caizhi_op=
            "https://test.tengmoney.com/caizhi_op";
    private String chromedrvierpath=
            "C:/Users/yzz/webdriver/chromedriver/chromedriver.exe";
    private String newsUrl=
            "https://mp.weixin.qq.com/s/a8LEK2r1EwJBWOriIVLpNQ";

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScenes() {
        return scenes;
    }

    public void setScenes(String scenes) {
        this.scenes = scenes;
    }

    public HashMap loadExeConfig(){
        return new HashMap<String ,String>(){
            {
                put("env",getEnvironment());
                put("scenes",getScenes());
                put("type",getType());
            }
        };
    }
    public String loadExeConfig(String config){
        return (String) loadExeConfig().get(config);
    }



}
