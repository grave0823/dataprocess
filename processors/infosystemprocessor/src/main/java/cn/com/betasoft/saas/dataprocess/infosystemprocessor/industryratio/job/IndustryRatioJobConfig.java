package cn.com.betasoft.saas.dataprocess.infosystemprocessor.industryratio.job;

import cn.com.betasoft.saas.dataprocess.infosystemprocessor.industryratio.model.IndustryRatioModel;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.industryratio.processor.IndustryRatioProcessor;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.industryratio.rowmapper.IndustryRatioRowMapper;
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
public class IndustryRatioJobConfig {

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
    public JdbcCursorItemReader<IndustryRatioModel> industryRatioReader() {
        JdbcCursorItemReader itemReader = new JdbcCursorItemReader();
        itemReader.setName("industryRatioReader");
        itemReader.setDataSource(this.infosystemDataSource);
        itemReader.setSql("SELECT customercategory as industry,count(*) as count FROM saas_order_user group by customercategory order by count desc;");
        itemReader.setRowMapper(new IndustryRatioRowMapper());
        return itemReader;
    }

    @Bean
    public IndustryRatioProcessor industryRatioProcessor() {
        return new IndustryRatioProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<IndustryRatioModel> industryRatioWriter() {
        JdbcBatchItemWriter<IndustryRatioModel> writer = new JdbcBatchItemWriter<IndustryRatioModel>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<IndustryRatioModel>());
        writer.setSql("INSERT INTO infosystem_industryratio (id, industry, count,date,createTime) VALUES (:id, :industry, :count,:date,:createTime);");
        writer.setDataSource(mysqlDataSource);
        return writer;
    }

    @Bean
    public Step industryRatioStep() {
        return stepBuilderFactory.get("industryRatioStep")
                .<IndustryRatioModel, IndustryRatioModel>chunk(10)
                .reader(industryRatioReader())
                .processor(industryRatioProcessor())
                .writer(industryRatioWriter())
                .build();
    }

    @Bean
    public Job industryRatioJob(InfoSystemJobListener listener) {
        return jobBuilderFactory.get("industryRatioJob")
                .listener(listener)
                .start(industryRatioStep())
                .build();
    }
}
