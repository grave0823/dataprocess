package cn.com.betasoft.saas.dataprocess.infosystemprocessor.arearatio.model;

import java.util.Date;

/**
 * 地域分布
 */
public class AreaRatioModel {

    public static final String TYPE_PARTNER = "partner";
    public static final String TYPE_REGISTER = "register";
    public static final String TYPE_ALL = "all";

    /**
     *  主键
     */
    private String id;

    /**
     * 类型 值包含 partner-运营商  agent-注册用户  all-所有类型用户
     */
    private String type;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 各省-市对应数量
     */
    private Integer count;

    /**
     * 数据对应日期  yyyy-MM-dd形式
     */
    private String date;

    /**
     * 数据生成时间  日期格式
     */
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "ProvinceRatioModel{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", count=" + count +
                ", date='" + date + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
