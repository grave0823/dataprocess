package cn.com.betasoft.saas.dataprocess.infosystemprocessor.industryratio.job;

import cn.com.betasoft.saas.dataprocess.baselib.util.DateUtil;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.industryratio.model.IndustryRatioModel;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.industryratio.processor.AllIndustryRatioProcessor;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.industryratio.processor.PartnerIndustryRatioProcessor;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.industryratio.processor.RegisterIndustryRatioProcessor;
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
import java.util.Date;

@Configuration
//@EnableBatchProcessing
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
    public JdbcCursorItemReader<IndustryRatioModel> partnerIndustryRatioReader() {
        JdbcCursorItemReader itemReader = new JdbcCursorItemReader();
        itemReader.setDataSource(this.infosystemDataSource);
        itemReader.setSql("SELECT business as industry,count(*) as count FROM partner_user group by business order by count desc;");
        itemReader.setName("partnerIndustryRatioReader");
        itemReader.setRowMapper(new IndustryRatioRowMapper());
        return itemReader;
    }

    @Bean
    public JdbcCursorItemReader<IndustryRatioModel> registerIndustryRatioReader() {
        JdbcCursorItemReader itemReader = new JdbcCursorItemReader();
        itemReader.setDataSource(this.infosystemDataSource);
        itemReader.setSql("SELECT customercategory as industry,count(*) as count FROM saas_order_user group by customercategory order by count desc;");
        itemReader.setName("registerIndustryRatioReader");
        itemReader.setRowMapper(new IndustryRatioRowMapper());
        return itemReader;
    }

    @Bean
    public JdbcCursorItemReader<IndustryRatioModel> allIndustryRatioReader() {
        JdbcCursorItemReader itemReader = new JdbcCursorItemReader();
        itemReader.setDataSource(this.mysqlDataSource);
        String dateStr = DateUtil.formatDate(new Date(),"yyyy-MM-dd");
        itemReader.setSql("SELECT industry,SUM(count) as count FROM infosystem_industryratio where date = '"+dateStr+"' group by industry order by count desc;");
        itemReader.setName("allIndustryRatioReader");
        itemReader.setRowMapper(new IndustryRatioRowMapper());
        return itemReader;
    }

    @Bean
    public RegisterIndustryRatioProcessor registerIndustryRatioProcessor() {
        return new RegisterIndustryRatioProcessor();
    }

    @Bean
    public PartnerIndustryRatioProcessor partnerIndustryRatioProcessor() {
        return new PartnerIndustryRatioProcessor();
    }

    @Bean
    public AllIndustryRatioProcessor allIndustryRatioProcessor() {
        return new AllIndustryRatioProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<IndustryRatioModel> industryRatioWriter() {
        JdbcBatchItemWriter<IndustryRatioModel> writer = new JdbcBatchItemWriter<IndustryRatioModel>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<IndustryRatioModel>());
        writer.setSql("INSERT INTO infosystem_industryratio (id, type, industry, count,date,createTime) VALUES (:id, :type, :industry, :count,:date,:createTime);");
        writer.setDataSource(mysqlDataSource);
        return writer;
    }

    @Bean
    public Step partnerIndustryRatioStep() {
        return stepBuilderFactory.get("partnerIndustryRatioStep")
                .<IndustryRatioModel, IndustryRatioModel>chunk(10)
                .reader(partnerIndustryRatioReader())
                .processor(partnerIndustryRatioProcessor())
                .writer(industryRatioWriter())
                .build();
    }

    @Bean
    public Step registerIndustryRatioStep() {
        return stepBuilderFactory.get("registerIndustryRatioStep")
                .<IndustryRatioModel, IndustryRatioModel>chunk(10)
                .reader(registerIndustryRatioReader())
                .processor(registerIndustryRatioProcessor())
                .writer(industryRatioWriter())
                .build();
    }

    @Bean
    public Step allIndustryRatioStep() {
        return stepBuilderFactory.get("allIndustryRatioStep")
                .<IndustryRatioModel, IndustryRatioModel>chunk(10)
                .reader(allIndustryRatioReader())
                .processor(allIndustryRatioProcessor())
                .writer(industryRatioWriter())
                .build();
    }

    @Bean
    public Job industryRatioJob(InfoSystemJobListener listener) {
        return jobBuilderFactory.get("industryRatioJob")
                .listener(listener)
                .start(partnerIndustryRatioStep())
                .next(registerIndustryRatioStep())
                .next(allIndustryRatioStep())
                .build();
    }
}
