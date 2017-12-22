package cn.com.betasoft.saas.dataprocess.infosystemprocessor.producttyperatio.rowmapper;

import cn.com.betasoft.saas.dataprocess.infosystemprocessor.producttyperatio.model.ProductTypeRatioModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductTypeRatioRowMapper implements RowMapper {

    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        ProductTypeRatioModel productTypeRatioModel = new ProductTypeRatioModel();

        productTypeRatioModel.setProductType(resultSet.getString("producttype"));
        productTypeRatioModel.setCount(resultSet.getInt("count"));

        return productTypeRatioModel;
    }
}
