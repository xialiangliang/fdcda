package com.keyou.fdcda.home.job;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keyou.fdcda.api.utils.DateUtil;

@Service
public class BatchJobLauncher {
    private static final Logger LOG = LoggerFactory.getLogger(BatchJobLauncher.class);
    @Autowired
    private JobLauncher         jobLauncher;
    /*@Autowired
    private JobLocator          jobLocator;*/
    @Autowired
    private JobExplorer         jobExplorer;

    private Map<String, String> jobParameters;

    private Job job;

    private String dateFormat;

    public synchronized void execute() throws Exception {
//        if (jobParameters == null)
//            jobParameters = new HashMap<String, String>();
//        jobParameters.put("now", new Date().getTime() + "");
//        JobParameters allParams = translateParams(job, jobParameters);
//        JobExecution je = jobLauncher.run(job, allParams);
//        Long jobExecutionId = je.getId();
//        LOG.info("开始执行 execute :name=[{}],id=[{}],status=[{}]", job.getName(), jobExecutionId, je.getStatus());
        executeJob();
    }

    /**
     * 串行化执行job 如果该job已经有实例正在执行，则不开启新job
     * @throws Exception
     * @author:     xll
     */
    public synchronized void executeSerial() throws Exception {
        Set<JobExecution> set = jobExplorer.findRunningJobExecutions(job.getName());
        if (set.size() > 0) {
            LOG.info("已经有{}正在执行。", job.getName());
            return;
        }
        executeJob();
    }

    /**
     * 执行job
     * @throws Exception
     * @author:     xll
     */
    private void executeJob() throws Exception {
        if (jobParameters == null) jobParameters = new HashMap<>();
        jobParameters.put("now", new Date().getTime() + "");
        JobParameters allParams = translateParams(job, jobParameters);
        JobExecution je = jobLauncher.run(job, allParams);
        Long jobExecutionId = je.getId();
        LOG.info("开始执行 execute :name=[{}],id=[{}],status=[{}]", job.getName(), jobExecutionId, je.getStatus());
    }

    public void executeDay() {
        if (jobParameters == null)
            jobParameters = new HashMap<String, String>();
        jobParameters.put("now", DateUtil.getDate(dateFormat));
        JobParameters allParams = null;
        try {
            allParams = translateParams(job, jobParameters);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        JobExecution je = null;
        try {
            je = jobLauncher.run(job, allParams);
        } catch (JobExecutionAlreadyRunningException e) {
            LOG.error(e.getMessage());
        } catch (JobRestartException e) {
            LOG.error(e.getMessage());
        } catch (JobInstanceAlreadyCompleteException e) {
            LOG.error(e.getMessage());
        } catch (JobParametersInvalidException e) {
            LOG.error(e.getMessage());
        }
        if (je == null) {
            LOG.info("已经执行成功 executeDay:[{}]---batch_job_execution_params [now={}]", job.getName(), DateUtil.getDate(dateFormat));
        } else {
            Long jobExecutionId = je.getId();
            LOG.info("开始执行 executeDay :[{}]---[{}]", job.getName(), jobExecutionId);
        }

    }

    public synchronized void executeSettlement() throws Exception {
        if (jobParameters == null)
            jobParameters = new HashMap<String, String>();
        jobParameters.put("now", new Date() + "000000");
        JobParameters allParams = translateParams(job, jobParameters);

        JobExecution je = jobLauncher.run(job, allParams);
        Long jobExecutionId = je.getId();
        LOG.info("开始执行 executeSettlement :[{}] ---[{}]", job.getName(), jobExecutionId);
    }

    public void executeMonth() {
        if (jobParameters == null)
            jobParameters = new HashMap<String, String>();
        jobParameters.put("now", DateUtil.getDate(dateFormat));
        JobParameters allParams = null;
        try {
            allParams = translateParams(job, jobParameters);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        JobExecution je = null;
        try {
            je = jobLauncher.run(job, allParams);
        } catch (JobExecutionAlreadyRunningException e) {
            LOG.error(e.getMessage());
        } catch (JobRestartException e) {
            LOG.error(e.getMessage());
        } catch (JobInstanceAlreadyCompleteException e) {
            LOG.error(e.getMessage());
        } catch (JobParametersInvalidException e) {
            LOG.error(e.getMessage());
        }
        if (je == null) {
            LOG.info("已经执行成功 executeMonth:[{}]---batch_job_execution_params [now={}]", job.getName(), DateUtil.getDate(dateFormat));
        } else {
            Long jobExecutionId = je.getId();
            LOG.info("开始执行 executeMonth :[{}]---[{}]", job.getName(), jobExecutionId);
        }

    }

    public synchronized void executePayOrderQueryJob() throws Exception {
        if (jobParameters == null)
            jobParameters = new HashMap<String, String>();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -1);
        jobParameters.put("dtCreateEnd", DateUtil.getDate(cal.getTime(), "yyyy-MM-dd HH:mm"));
        jobParameters.put("now", DateUtil.getCurrentTimeStamp());
        JobParameters allParams = translateParams(job, jobParameters);

        JobExecution je = jobLauncher.run(job, allParams);
        Long jobExecutionId = je.getId();
        LOG.info("开始执行 executePayOrderQueryJob :[{}] ---[{}]", job.getName(), jobExecutionId);
    }

    private JobParameters translateParams(Job job, Map<String, String> params) throws Exception {
        JobParametersBuilder builder = new JobParametersBuilder();

        if (params != null) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                builder.addString(param.getKey(), param.getValue());
            }
        }
        // 保证任务可以重复启动
        // Calendar now = Calendar.getInstance();
        // builder.addString("uid", String.valueOf(now.getTimeInMillis()) +
        // (Math.random() * 10000));

        if (job.getName().equals("currentsettlementJob")) {
            return builder.toJobParameters();
        } else {
            return builder.addLong("time", System.currentTimeMillis()).toJobParameters();
        }

    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
}
