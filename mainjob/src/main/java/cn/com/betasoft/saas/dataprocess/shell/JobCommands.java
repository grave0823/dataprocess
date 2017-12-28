package cn.com.betasoft.saas.dataprocess.shell;

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
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ShellComponent
public class JobCommands {

    private static final Logger logger = LoggerFactory.getLogger(JobCommands.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private List<Job> jobs;

    @Autowired
    private List<SaaSJobParameters> saaSJobParametersList;

    @ShellMethod("获取所有任务名称")
    public String showJobs() {
        StringBuilder jobNameStr = new StringBuilder();
        for(Job job : jobs){
            jobNameStr.append(job.getName()).append("\n");
        }
        return jobNameStr.toString();
    }

    @ShellMethod("用默认参数，立即执行所有任务")
    public String executeAllJob(){
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
        return "execute all job successfully";
    }

    @ShellMethod("根据指定日期，立即执行所有任务。参数为yyyy-MM-dd形式，如2017-12-25")
    public String executeAllJobByDate(String dateStr){
        if(!validDateStr(dateStr)){
            throw new RuntimeException(dateStr+" is not a data format string，please make sure input parameter format date string is yyyy-MM-dd.");
        }
        Map<String,Object> parameters = new HashMap<>();
        parameters.put(SaaSJobParameters.KEY_PROCESS_DATE_STR,dateStr);
        for(SaaSJobParameters saaSJobParameters : saaSJobParametersList){
            saaSJobParameters.init(parameters);
        }
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
        return "execute all job with parameter "+dateStr+" successfully";
    }

    private void initSaaSJobParameters(){
        for(SaaSJobParameters saaSJobParameters : saaSJobParametersList){
            saaSJobParameters.init();
        }
    }

    /**
     * 校验输入的日期格式是否正确
     * @param dateStr
     * @return
     */
    private boolean validDateStr(String dateStr){
        if(StringUtils.isEmpty(dateStr)){
            return false;
        }
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date date = (Date)formatter.parse(dateStr);
            return dateStr.equals(formatter.format(date));
        }catch(Exception e){
            return false;
        }
    }
}
