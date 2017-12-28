package cn.com.betasoft.saas.dataprocess.scheduled;

import cn.com.betasoft.saas.dataprocess.baselib.parameters.SaaSJobParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledJob {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledJob.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private List<Job> jobs;

    @Autowired
    private List<SaaSJobParameters> saaSJobParametersList;

    @Scheduled(cron="0 5 0 * * ?")
    public void executeDailyJob() {

        initSaaSJobParameters();

        JobParameters jobParameters =
                new JobParametersBuilder()
                        .addLong("time",System.currentTimeMillis()).toJobParameters();
        for(Job job : jobs){
            try {
                jobLauncher.run(job, jobParameters);
            } catch (JobExecutionAlreadyRunningException e) {
                logger.error("JobExecutionAlreadyRunningException",e);
            } catch (JobRestartException e) {
                logger.error("JobRestartException",e);
            } catch (JobInstanceAlreadyCompleteException e) {
                logger.error("JobInstanceAlreadyCompleteException",e);
            } catch (JobParametersInvalidException e) {
                logger.error("JobParametersInvalidException",e);
            }
        }
    }

    private void initSaaSJobParameters(){
        for(SaaSJobParameters saaSJobParameters : saaSJobParametersList){
            saaSJobParameters.init();
        }
    }
}
