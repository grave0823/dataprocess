package cn.com.betasoft.saas.dataprocess.infosystemprocessor.agentratio.model;

import java.util.Date;

/**
 * 代理商、运营商分布
 */
public class AgentRatioModel {

    /**
     *  主键
     */
    private String id;

    /**
     * 行业
     */
    private String agentName;

    /**
     * 各代理商、运营商对应数量
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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    @Override
    public String toString() {
        return "AgentRatioModel{" +
                "id='" + id + '\'' +
                ", agentName='" + agentName + '\'' +
                ", count=" + count +
                ", date='" + date + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
