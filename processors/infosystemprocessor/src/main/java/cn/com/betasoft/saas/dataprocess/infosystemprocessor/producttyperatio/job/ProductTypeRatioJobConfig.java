package cn.com.betasoft.saas.dataprocess.infosystemprocessor.producttyperatio.job;

import cn.com.betasoft.saas.dataprocess.infosystemprocessor.listener.InfoSystemJobListener;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.producttyperatio.model.ProductTypeRatioModel;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.producttyperatio.processor.ProductTypeRatioProcessor;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.producttyperatio.rowmapper.ProductTypeRatioRowMapper;
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
public class ProductTypeRatioJobConfig {

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
    public JdbcCursorItemReader<ProductTypeRatioModel> productTypeRatioReader() {
        JdbcCursorItemReader itemReader = new JdbcCursorItemReader();
        itemReader.setName("productTypeRatioReader");
        itemReader.setDataSource(this.infosystemDataSource);
        itemReader.setSql("SELECT userproduct as producttype,count(*) as count FROM saas_order_user group by producttype order by count desc;");
        itemReader.setRowMapper(new ProductTypeRatioRowMapper());
        return itemReader;
    }

    @Bean
    public ProductTypeRatioProcessor productTypeRatioProcessor() {
        return new ProductTypeRatioProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<ProductTypeRatioModel> productTypeRatioWriter() {
        JdbcBatchItemWriter<ProductTypeRatioModel> writer = new JdbcBatchItemWriter<ProductTypeRatioModel>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<ProductTypeRatioModel>());
        writer.setSql("INSERT INTO infosystem_producttyperatio (id, producttype, count,date,createTime) VALUES (:id, :productType, :count,:date,:createTime);");
        writer.setDataSource(mysqlDataSource);
        return writer;
    }

    @Bean
    public Step productTypeRatioStep() {
        return stepBuilderFactory.get("productTypeRatioStep")
                .<ProductTypeRatioModel, ProductTypeRatioModel>chunk(10)
                .reader(productTypeRatioReader())
                .processor(productTypeRatioProcessor())
                .writer(productTypeRatioWriter())
                .build();
    }

    @Bean
    public Job productTypeRatioJob(InfoSystemJobListener listener) {
        return jobBuilderFactory.get("productTypeRatioJob")
                .listener(listener)
                .start(productTypeRatioStep())
                .build();
    }
}
