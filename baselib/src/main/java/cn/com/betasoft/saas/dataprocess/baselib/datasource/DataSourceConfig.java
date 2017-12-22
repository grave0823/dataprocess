package cn.com.betasoft.saas.dataprocess.baselib.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "mysqlDataSource")
    @Qualifier("mysqlDataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.mysql")
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "infosystemDataSource")
    @Qualifier("infosystemDataSource")
    @ConfigurationProperties(prefix="spring.datasource.infosystem")
    public DataSource infosystemDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mainwebsetDataSource")
    @Qualifier("mainwebsetDataSource")
    @ConfigurationProperties(prefix="spring.datasource.mainwebset")
    public DataSource mainwebsetDataSource() {
        return DataSourceBuilder.create().build();
    }


//    @Bean(name = "mysqlJdbcTemplate")
//    public JdbcTemplate mysqlJdbcTemplate(
//            @Qualifier("mysqlDataSource") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//
//    @Bean(name = "infosystemJdbcTemplate")
//    public JdbcTemplate infosystemJdbcTemplate(
//            @Qualifier("infosystemDataSource") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//
//    @Bean(name = "mainwebsetJdbcTemplate")
//    public JdbcTemplate mainwebsetJdbcTemplate(
//            @Qualifier("mainwebsetDataSource") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
}
