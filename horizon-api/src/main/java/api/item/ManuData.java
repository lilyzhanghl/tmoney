package api.item;

/**
 * tmoney
 * 2020/7/25 18:57
 * 手动传入的数据类型
 *
 * @author zhzh.yin
 **/
public enum ManuData {
    REQUEST_PARAM("requestParam"),
    JSON_PARAM("jsonParam"),
    JSON_FILE_NAME("jsonFileName");
    private final String type;
    private ManuData(String type)
    {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
