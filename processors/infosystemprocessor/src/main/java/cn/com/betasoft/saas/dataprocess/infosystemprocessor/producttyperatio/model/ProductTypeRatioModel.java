package cn.com.betasoft.saas.dataprocess.infosystemprocessor.producttyperatio.model;

import java.util.Date;

/**
 * 产品类型分布
 */
public class ProductTypeRatioModel {

    /**
     *  主键
     */
    private String id;

    /**
     * 产品类型
     */
    private String productType;

    /**
     * 各代产品类型对应数量
     */
    private Integer count;

    /**
     * 数据对应日期  yyyy-MM-dd形式
     */
    private String date;

    /**
     * 数据生成时间  日期格式
     */
    private Date createTime = new Date();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    @Override
    public String toString() {
        return "ProductTypeRatioModel{" +
                "id='" + id + '\'' +
                ", productType='" + productType + '\'' +
                ", count=" + count +
                ", date='" + date + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
