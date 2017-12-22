package cn.com.betasoft.saas.dataprocess.infosystemprocessor.itdepartmentratio.model;

import java.util.Date;

/**
 * IT隶属部门分布
 */
public class ItDepartmentRatioModel {

    /**
     *  主键
     */
    private String id;

    /**
     * IT隶属部门
     */
    private String itDepartment;

    /**
     * 各代IT隶属部门对应数量
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

    public String getItDepartment() {
        return itDepartment;
    }

    public void setItDepartment(String itDepartment) {
        this.itDepartment = itDepartment;
    }

    @Override
    public String toString() {
        return "ItDepartmentRatioModel{" +
                "id='" + id + '\'' +
                ", itDepartment='" + itDepartment + '\'' +
                ", count=" + count +
                ", date='" + date + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
