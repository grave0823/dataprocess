package cn.com.betasoft.saas.dataprocess.infosystemprocessor.itdepartmentratio.rowmapper;

import cn.com.betasoft.saas.dataprocess.infosystemprocessor.itdepartmentratio.model.ItDepartmentRatioModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItDepartmentRatioRowMapper implements RowMapper {

    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        ItDepartmentRatioModel itDepartmentRatioModel = new ItDepartmentRatioModel();

        itDepartmentRatioModel.setItDepartment(resultSet.getString("itdepartment"));
        itDepartmentRatioModel.setCount(resultSet.getInt("count"));

        return itDepartmentRatioModel;
    }
}
