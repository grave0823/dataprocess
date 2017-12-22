package cn.com.betasoft.saas.dataprocess.infosystemprocessor.arearatio.rowmapper;

import cn.com.betasoft.saas.dataprocess.infosystemprocessor.arearatio.model.AreaRatioModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AreaRatioRowMapper implements RowMapper {

    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        AreaRatioModel areaRatioModel = new AreaRatioModel();

        areaRatioModel.setProvince(resultSet.getString("province"));
        areaRatioModel.setCity(resultSet.getString("city"));
        areaRatioModel.setCount(resultSet.getInt("count"));

        return areaRatioModel;
    }
}
