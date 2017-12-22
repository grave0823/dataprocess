package cn.com.betasoft.saas.dataprocess.mainwebprocessor.workorderratio.job;

import cn.com.betasoft.saas.dataprocess.mainwebprocessor.listener.MainWebJobListener;
import cn.com.betasoft.saas.dataprocess.mainwebprocessor.workorderratio.model.WorkOrderRatioModel;
import cn.com.betasoft.saas.dataprocess.mainwebprocessor.workorderratio.processor.WorkOrderRatioProcessor;
import cn.com.betasoft.saas.dataprocess.mainwebprocessor.workorderratio.rowmapper.WorkOrderRatioRowMapper;
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
public class WorkOrderRatioJobConfig {

    @Autowired
    @Qualifier("mysqlDataSource")
    public DataSource mysqlDataSource;

    @Autowired
    @Qualifier("mainwebsetDataSource")
    public DataSource mainwebsetDataSource;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Bean
    public JdbcCursorItemReader<WorkOrderRatioModel> workOrderRatioReader() {
        JdbcCursorItemReader itemReader = new JdbcCursorItemReader();
        itemReader.setName("workOrderRatioReader");
        itemReader.setDataSource(this.mainwebsetDataSource);
        itemReader.setSql("SELECT workordertype as modulename,count(*) as count FROM saas_community_work_order group by moduleName order by count desc;");
        itemReader.setRowMapper(new WorkOrderRatioRowMapper());
        return itemReader;
    }

    @Bean
    public WorkOrderRatioProcessor workOrderRatioProcessor() {
        return new WorkOrderRatioProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<WorkOrderRatioModel> workOrderRatioWriter() {
        JdbcBatchItemWriter<WorkOrderRatioModel> writer = new JdbcBatchItemWriter<WorkOrderRatioModel>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<WorkOrderRatioModel>());
        writer.setSql("INSERT INTO mainweb_workorderratio (id, modulename, count,date,createTime) VALUES (:id, :moduleName, :count,:date,:createTime);");
        writer.setDataSource(mysqlDataSource);
        return writer;
    }

    @Bean
    public Step workOrderRatioStep() {
        return stepBuilderFactory.get("workOrderRatioStep")
                .<WorkOrderRatioModel, WorkOrderRatioModel>chunk(10)
                .reader(workOrderRatioReader())
                .processor(workOrderRatioProcessor())
                .writer(workOrderRatioWriter())
                .build();
    }

    @Bean
    public Job workOrderRatioJob(MainWebJobListener listener) {
        return jobBuilderFactory.get("workOrderRatioStep")
                .listener(listener)
                .start(workOrderRatioStep())
                .build();
    }
}
