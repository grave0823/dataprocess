package cn.com.betasoft.saas.dataprocess.infosystemprocessor.itdepartmentratio.job;

import cn.com.betasoft.saas.dataprocess.infosystemprocessor.itdepartmentratio.model.ItDepartmentRatioModel;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.itdepartmentratio.processor.ItDepartmentRatioProcessor;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.itdepartmentratio.rowmapper.ItDepartmentRatioRowMapper;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.listener.InfoSystemJobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackageClasses = DefaultBatchConfigurer.class)
public class ItDepartmentRatioJobConfig {

    @Autowired
    @Qualifier("mysqlDataSource")
    public DataSource mysqlDataSource;

    @Autowired
    @Qualifier("infosystemDataSource")
    public DataSource infosystemDataSource;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Bean
    public JdbcCursorItemReader<ItDepartmentRatioModel> itDepartmentRatioReader() {
        JdbcCursorItemReader itemReader = new JdbcCursorItemReader();
        itemReader.setName("itDepartmentRatioReader");
        itemReader.setDataSource(this.infosystemDataSource);
//        StringBuilder sqlStr = new StringBuilder("SELECT itdepartment,sum(count) as count FROM ");
//        sqlStr.append("(SELECT itdepartment,count ");
//        sqlStr.append("FROM (SELECT itmanagedepartment as itdepartment,count(*) as count FROM saas_order_user group by itdepartment order by count desc) as t1 ");
//        sqlStr.append("union all (SELECT itmanagedepartment as itdepartment,count(*) as count FROM partner_user group by itdepartment order by count desc)) as temp ");
//        sqlStr.append("group by itdepartment order by count desc");
        itemReader.setSql("SELECT itmanagedepartment as itdepartment,count(*) as count FROM saas_order_user group by itdepartment order by count desc;");
        itemReader.setRowMapper(new ItDepartmentRatioRowMapper());
        return itemReader;
    }

    @Bean
    public ItDepartmentRatioProcessor itDepartmentRatioProcessor() {
        return new ItDepartmentRatioProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<ItDepartmentRatioModel> itDepartmentRatioWriter() {
        JdbcBatchItemWriter<ItDepartmentRatioModel> writer = new JdbcBatchItemWriter<ItDepartmentRatioModel>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<ItDepartmentRatioModel>());
        writer.setSql("INSERT INTO infosystem_itdepartmentratio (id, itdepartment, count,date,createTime) VALUES (:id,:itDepartment, :count,:date,:createTime);");
        writer.setDataSource(mysqlDataSource);
        return writer;
    }

    @Bean
    public Step itDepartmentRatioStep() {
        return stepBuilderFactory.get("itDepartmentRatioStep")
                .<ItDepartmentRatioModel, ItDepartmentRatioModel>chunk(10)
                .reader(itDepartmentRatioReader())
                .processor(itDepartmentRatioProcessor())
                .writer(itDepartmentRatioWriter())
                .build();
    }

    @Bean
    public Job itDepartmentRatioJob(InfoSystemJobListener listener) {
        return jobBuilderFactory.get("itDepartmentRatioJob")
                .listener(listener)
                .start(itDepartmentRatioStep())
                .build();
    }
}
