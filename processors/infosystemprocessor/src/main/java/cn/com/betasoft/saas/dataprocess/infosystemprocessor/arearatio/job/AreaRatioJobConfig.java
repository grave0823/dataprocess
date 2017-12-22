package cn.com.betasoft.saas.dataprocess.infosystemprocessor.arearatio.job;

import cn.com.betasoft.saas.dataprocess.baselib.util.DateUtil;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.arearatio.model.AreaRatioModel;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.arearatio.processor.AllAreaRatioProcessor;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.arearatio.processor.PartnerAreaRatioProcessor;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.arearatio.processor.RegisterAreaRatioProcessor;
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
import java.util.Date;

@Configuration
//@EnableBatchProcessing
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
    public JdbcCursorItemReader<AreaRatioModel> partnerAreaRatioReader() {
        JdbcCursorItemReader itemReader = new JdbcCursorItemReader();
        itemReader.setName("partnerAreaRatioReader");
        itemReader.setDataSource(this.infosystemDataSource);
        itemReader.setSql("SELECT province,city,count(*) as count FROM partner_user group by province,city order by count desc;");
        itemReader.setRowMapper(new AreaRatioRowMapper());
        return itemReader;
    }

    @Bean
    public JdbcCursorItemReader<AreaRatioModel> registerAreaRatioReader() {
        JdbcCursorItemReader itemReader = new JdbcCursorItemReader();
        itemReader.setName("registerAreaRatioReader");
        itemReader.setDataSource(this.infosystemDataSource);
        itemReader.setSql("SELECT province ,city,count(*) as count FROM saas_order_user group by province,city order by count desc;");
        itemReader.setRowMapper(new AreaRatioRowMapper());
        return itemReader;
    }

    @Bean
    public JdbcCursorItemReader<AreaRatioModel> allAreaRatioReader() {
        JdbcCursorItemReader itemReader = new JdbcCursorItemReader();
        itemReader.setName("allAreaRatioReader");
        itemReader.setDataSource(this.mysqlDataSource);
        String dateStr = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
        itemReader.setSql("SELECT province,city,SUM(count) as count FROM infosystem_arearatio where date = '" + dateStr + "' group by province,city order by count desc;");
        itemReader.setRowMapper(new AreaRatioRowMapper());
        return itemReader;
    }

    @Bean
    public RegisterAreaRatioProcessor registerAreaRatioProcessor() {
        return new RegisterAreaRatioProcessor();
    }

    @Bean
    public PartnerAreaRatioProcessor partnerAreaRatioProcessor() {
        return new PartnerAreaRatioProcessor();
    }

    @Bean
    public AllAreaRatioProcessor allAreaRatioProcessor() {
        return new AllAreaRatioProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<AreaRatioModel> areaRatioWriter() {
        JdbcBatchItemWriter<AreaRatioModel> writer = new JdbcBatchItemWriter<AreaRatioModel>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<AreaRatioModel>());
        writer.setSql("INSERT INTO infosystem_arearatio (id, type, province,city, count,date,createTime) VALUES (:id, :type, :province,:city, :count,:date,:createTime);");
        writer.setDataSource(mysqlDataSource);
        return writer;
    }

    @Bean
    public Step partnerAreaRatioStep() {
        return stepBuilderFactory.get("partnerAreaRatioStep")
                .<AreaRatioModel, AreaRatioModel>chunk(10)
                .reader(partnerAreaRatioReader())
                .processor(partnerAreaRatioProcessor())
                .writer(areaRatioWriter())
                .build();
    }

    @Bean
    public Step registerAreaRatioStep() {
        return stepBuilderFactory.get("registerAreaRatioStep")
                .<AreaRatioModel, AreaRatioModel>chunk(10)
                .reader(registerAreaRatioReader())
                .processor(registerAreaRatioProcessor())
                .writer(areaRatioWriter())
                .build();
    }

    @Bean
    public Step allAreaRatioStep() {
        return stepBuilderFactory.get("allAreaRatioStep")
                .<AreaRatioModel, AreaRatioModel>chunk(10)
                .reader(allAreaRatioReader())
                .processor(allAreaRatioProcessor())
                .writer(areaRatioWriter())
                .build();
    }

    @Bean
    public Job areaRatioJob(InfoSystemJobListener listener) {
        return jobBuilderFactory.get("areaRatioJob")
                .listener(listener)
                .start(partnerAreaRatioStep())
                .next(registerAreaRatioStep())
                .next(allAreaRatioStep())
                .build();
    }
}
