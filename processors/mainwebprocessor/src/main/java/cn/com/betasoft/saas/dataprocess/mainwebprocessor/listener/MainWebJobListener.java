package cn.com.betasoft.saas.dataprocess.mainwebprocessor.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class MainWebJobListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(MainWebJobListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("!!! MainWeb JOB START! Start time is :"+jobExecution.getStartTime());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("!!! MainWeb JOB FINISHED with status "+jobExecution.getStatus().name());
        if(jobExecution.getStatus() != BatchStatus.COMPLETED) {
            log.info("!!! MainWeb JOB may have Exceprion.",jobExecution.getAllFailureExceptions());
        }
        log.info("!!! MainWeb JOB Finish time is :"+jobExecution.getEndTime());
    }
}
