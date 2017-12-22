package cn.com.betasoft.saas.dataprocess.infosystemprocessor.industryratio.rowmapper;

import cn.com.betasoft.saas.dataprocess.infosystemprocessor.industryratio.model.IndustryRatioModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IndustryRatioRowMapper implements RowMapper {

    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        IndustryRatioModel industryRatioModel = new IndustryRatioModel();

        industryRatioModel.setIndustry(resultSet.getString("industry"));
        industryRatioModel.setCount(resultSet.getInt("count"));

        return industryRatioModel;
    }
}
