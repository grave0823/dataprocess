package cn.com.betasoft.saas.dataprocess.infosystemprocessor.arearatio.job;

import cn.com.betasoft.saas.dataprocess.infosystemprocessor.arearatio.model.AreaRatioModel;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.arearatio.processor.AreaRatioProcessor;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.arearatio.rowmapper.AreaRatioRowMapper;
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
public class AreaRatioJobConfig {

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
    public JdbcCursorItemReader<AreaRatioModel> areaRatioReader() {
        JdbcCursorItemReader itemReader = new JdbcCursorItemReader();
        itemReader.setName("areaRatioReader");
        itemReader.setDataSource(this.infosystemDataSource);
        itemReader.setSql("SELECT province ,city,count(*) as count FROM saas_order_user group by province,city order by count desc;");
        itemReader.setRowMapper(new AreaRatioRowMapper());
        return itemReader;
    }

    @Bean
    public AreaRatioProcessor areaRatioProcessor() {
        return new AreaRatioProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<AreaRatioModel> areaRatioWriter() {
        JdbcBatchItemWriter<AreaRatioModel> writer = new JdbcBatchItemWriter<AreaRatioModel>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<AreaRatioModel>());
        writer.setSql("INSERT INTO infosystem_arearatio (id, province,city, count,date,createTime) VALUES (:id, :province,:city, :count,:date,:createTime);");
        writer.setDataSource(mysqlDataSource);
        return writer;
    }

    @Bean
    public Step areaRatioStep() {
        return stepBuilderFactory.get("areaRatioStep")
                .<AreaRatioModel, AreaRatioModel>chunk(10)
                .reader(areaRatioReader())
                .processor(areaRatioProcessor())
                .writer(areaRatioWriter())
                .build();
    }

    @Bean
    public Job areaRatioJob(InfoSystemJobListener listener) {
        return jobBuilderFactory.get("areaRatioJob")
                .listener(listener)
                .start(areaRatioStep())
                .build();
    }
}
