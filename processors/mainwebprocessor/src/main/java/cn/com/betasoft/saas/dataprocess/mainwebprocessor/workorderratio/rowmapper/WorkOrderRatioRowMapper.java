package cn.com.betasoft.saas.dataprocess.mainwebprocessor.workorderratio.rowmapper;

import cn.com.betasoft.saas.dataprocess.mainwebprocessor.workorderratio.model.WorkOrderRatioModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkOrderRatioRowMapper implements RowMapper {

    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        WorkOrderRatioModel workOrderRatioModel = new WorkOrderRatioModel();

        workOrderRatioModel.setModuleName(resultSet.getString("modulename"));
        workOrderRatioModel.setCount(resultSet.getInt("count"));

        return workOrderRatioModel;
    }
}
