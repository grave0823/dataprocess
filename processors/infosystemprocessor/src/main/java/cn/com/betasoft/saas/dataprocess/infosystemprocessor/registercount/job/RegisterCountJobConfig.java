package cn.com.betasoft.saas.dataprocess.infosystemprocessor.registercount.job;

import cn.com.betasoft.saas.dataprocess.infosystemprocessor.listener.InfoSystemJobListener;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.registercount.model.RegisterCountModel;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.registercount.processor.RegisterCountProcessor;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.registercount.rowmapper.RegisterCountRowMapper;
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
public class RegisterCountJobConfig {

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
    public JdbcCursorItemReader<RegisterCountModel> registerCountReader() {
        JdbcCursorItemReader itemReader = new JdbcCursorItemReader();
        itemReader.setName("registerCountReader");
        itemReader.setDataSource(this.infosystemDataSource);
        itemReader.setSql("SELECT count(*) as count FROM saas_order_user;");
        itemReader.setRowMapper(new RegisterCountRowMapper());
        return itemReader;
    }

    @Bean
    public RegisterCountProcessor registerCountProcessor() {
        return new RegisterCountProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<RegisterCountModel> registerCountWriter() {
        JdbcBatchItemWriter<RegisterCountModel> writer = new JdbcBatchItemWriter<RegisterCountModel>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<RegisterCountModel>());
        writer.setSql("INSERT INTO infosystem_registercount (id, count,date,createTime) VALUES (:id, :count,:date,:createTime);");
        writer.setDataSource(mysqlDataSource);
        return writer;
    }

    @Bean
    public Step registerCountStep() {
        return stepBuilderFactory.get("registerCountStep")
                .<RegisterCountModel, RegisterCountModel>chunk(10)
                .reader(registerCountReader())
                .processor(registerCountProcessor())
                .writer(registerCountWriter())
                .build();
    }

    @Bean
    public Job registerCountJob(InfoSystemJobListener listener) {
        return jobBuilderFactory.get("registerCountJob")
                .listener(listener)
                .start(registerCountStep())
                .build();
    }
}
