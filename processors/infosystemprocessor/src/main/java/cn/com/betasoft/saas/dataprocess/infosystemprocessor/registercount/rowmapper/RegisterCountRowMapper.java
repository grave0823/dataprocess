package cn.com.betasoft.saas.dataprocess.infosystemprocessor.registercount.rowmapper;

import cn.com.betasoft.saas.dataprocess.infosystemprocessor.registercount.model.RegisterCountModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterCountRowMapper implements RowMapper {

    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        RegisterCountModel registerCountModel = new RegisterCountModel();
        registerCountModel.setCount(resultSet.getInt("count"));
        return registerCountModel;
    }
}
