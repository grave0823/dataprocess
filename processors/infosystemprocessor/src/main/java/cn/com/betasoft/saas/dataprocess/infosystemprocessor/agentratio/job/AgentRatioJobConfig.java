package cn.com.betasoft.saas.dataprocess.infosystemprocessor.agentratio.job;

import cn.com.betasoft.saas.dataprocess.infosystemprocessor.agentratio.model.AgentRatioModel;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.agentratio.processor.PartnerAgentRatioProcessor;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.agentratio.processor.RegisterAgentRatioProcessor;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.agentratio.rowmapper.AgentRatioRowMapper;
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
//@EnableBatchProcessing
@ComponentScan(basePackageClasses = DefaultBatchConfigurer.class)
public class AgentRatioJobConfig {

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
    public JdbcCursorItemReader<AgentRatioModel> partnerAgentRatioReader() {
        JdbcCursorItemReader itemReader = new JdbcCursorItemReader();
        itemReader.setName("partnerAgentRatioReader");
        itemReader.setDataSource(this.infosystemDataSource);
        itemReader.setSql("SELECT agentname as agentname,count(*) as count FROM partner_user group by agentname order by count desc;");
        itemReader.setRowMapper(new AgentRatioRowMapper());
        return itemReader;
    }

    @Bean
    public JdbcCursorItemReader<AgentRatioModel> registerAgentRatioReader() {
        JdbcCursorItemReader itemReader = new JdbcCursorItemReader();
        itemReader.setName("registerAgentRatioReader");
        itemReader.setDataSource(this.infosystemDataSource);
        itemReader.setSql("SELECT agentname as agentname,count(*) as count FROM saas_order_user group by agentname order by count desc;");
        itemReader.setRowMapper(new AgentRatioRowMapper());
        return itemReader;
    }

    @Bean
    public RegisterAgentRatioProcessor registerAgentRatioProcessor() {
        return new RegisterAgentRatioProcessor();
    }

    @Bean
    public PartnerAgentRatioProcessor partnerAgentRatioProcessor() {
        return new PartnerAgentRatioProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<AgentRatioModel> agentRatioWriter() {
        JdbcBatchItemWriter<AgentRatioModel> writer = new JdbcBatchItemWriter<AgentRatioModel>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<AgentRatioModel>());
        writer.setSql("INSERT INTO infosystem_agentratio (id, type, agentname, count,date,createTime) VALUES (:id, :type, :agentName, :count,:date,:createTime);");
        writer.setDataSource(mysqlDataSource);
        return writer;
    }

    @Bean
    public Step partnerAgentRatioStep() {
        return stepBuilderFactory.get("partnerAgentRatioStep")
                .<AgentRatioModel, AgentRatioModel>chunk(10)
                .reader(partnerAgentRatioReader())
                .processor(partnerAgentRatioProcessor())
                .writer(agentRatioWriter())
                .build();
    }

    @Bean
    public Step registerAgentRatioStep() {
        return stepBuilderFactory.get("registerAgentRatioStep")
                .<AgentRatioModel, AgentRatioModel>chunk(10)
                .reader(registerAgentRatioReader())
                .processor(registerAgentRatioProcessor())
                .writer(agentRatioWriter())
                .build();
    }

    @Bean
    public Job agentRatioJob(InfoSystemJobListener listener) {
        return jobBuilderFactory.get("agentRatioJob")
                .listener(listener)
                .start(partnerAgentRatioStep())
                .next(registerAgentRatioStep())
                .build();
    }
}
