package cn.com.betasoft.saas.dataprocess.infosystemprocessor.agentratio.rowmapper;

import cn.com.betasoft.saas.dataprocess.infosystemprocessor.agentratio.model.AgentRatioModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AgentRatioRowMapper implements RowMapper {

    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        AgentRatioModel agentRatioModel = new AgentRatioModel();

        agentRatioModel.setAgentName(resultSet.getString("agentname"));
        agentRatioModel.setCount(resultSet.getInt("count"));

        return agentRatioModel;
    }
}
