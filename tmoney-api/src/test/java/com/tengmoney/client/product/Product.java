package com.tengmoney.client.product;
/**
 * @ClassName: Product
 *
 * @Description: product实现类
 *
 * @Author: zhzh.yin
 *
 * @Date: 2020-03-31 11:31
 * @Verion: 1.0
 */
public class Product {
    private String objId;
    private String objType;
    private String productId;
    private String actType ;
    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getActType() {
        return actType;
    }

    public void setActType(String actType) {
        this.actType = actType;
    }

    public Product(String objId, String objType, String productId, String actType) {
        this.objId = objId;
        this.objType = objType;
        this.productId = productId;
        this.actType = actType;
    }
}
