package cn.com.betasoft.saas.dataprocess.mainwebprocessor.workorderratio.model;

import java.util.Date;

/**
 * 网站提交问题分布
 */
public class WorkOrderRatioModel {

    /**
     *  主键
     */
    private String id;

    /**
     * 模块类型
     */
    private String moduleName;

    /**
     * 各模块类型对应数量
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

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
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

    @Override
    public String toString() {
        return "WorkOrderRatioModel{" +
                "id='" + id + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", count=" + count +
                ", date='" + date + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
