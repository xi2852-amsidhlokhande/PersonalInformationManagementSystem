package com.amsidh.mvc.curd.service.impl;

import com.amsidh.mvc.curd.entity.SchedulerJobInfo;
import com.amsidh.mvc.curd.job.CustomerBalanceJob;
import com.amsidh.mvc.curd.job.SimpleJob;
import com.amsidh.mvc.curd.repository.SchedulerRepository;
import com.amsidh.mvc.curd.service.SchedulerJobService;
import com.amsidh.mvc.scheduler.component.JobScheduleCreator;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Slf4j
@Transactional
@Service
public class SchedulerJobServiceImpl implements SchedulerJobService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private SchedulerRepository schedulerRepository;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private JobScheduleCreator scheduleCreator;

    @Override
    public SchedulerMetaData getMetaData() throws SchedulerException {
        return this.scheduler.getMetaData();
    }

    @Override
    public List<SchedulerJobInfo> getAllJobList() {
        return this.schedulerRepository.findAll();
    }

    @Override
    public boolean deleteJob(SchedulerJobInfo jobInfo) {
        try {
            SchedulerJobInfo getJobInfo = this.schedulerRepository.findByJobName(jobInfo.getJobName());
            this.schedulerRepository.delete(getJobInfo);
            log.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " deleted.");
            return this.schedulerFactoryBean.getScheduler().deleteJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
        } catch (SchedulerException e) {
            log.error("Failed to delete job - {}", jobInfo.getJobName(), e);
            return false;
        }
    }

    @Override
    public boolean pauseJob(SchedulerJobInfo jobInfo) {
        try {
            SchedulerJobInfo getJobInfo = this.schedulerRepository.findByJobName(jobInfo.getJobName());
            getJobInfo.setJobStatus("PAUSED");
            this.schedulerRepository.save(getJobInfo);
            this.schedulerFactoryBean.getScheduler().pauseJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
            log.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " paused.");
            return true;
        } catch (SchedulerException e) {
            log.error("Failed to pause job - {}", jobInfo.getJobName(), e);
            return false;
        }
    }

    @Override
    public boolean resumeJob(SchedulerJobInfo jobInfo) {
        try {
            SchedulerJobInfo getJobInfo = this.schedulerRepository.findByJobName(jobInfo.getJobName());
            getJobInfo.setJobStatus("RESUMED");
            this.schedulerRepository.save(getJobInfo);
            this.schedulerFactoryBean.getScheduler().resumeJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
            log.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " resumed.");
            return true;
        } catch (SchedulerException e) {
            log.error("Failed to resume job - {}", jobInfo.getJobName(), e);
            return false;
        }
    }

    @Override
    public boolean startJobNow(SchedulerJobInfo jobInfo) {
        try {
            SchedulerJobInfo getJobInfo = this.schedulerRepository.findByJobName(jobInfo.getJobName());
            getJobInfo.setJobStatus("SCHEDULED & STARTED");
            this.schedulerRepository.save(getJobInfo);
            this.schedulerFactoryBean.getScheduler().triggerJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
            log.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " scheduled and started now.");
            return true;
        } catch (SchedulerException e) {
            log.error("Failed to start new job - {}", jobInfo.getJobName(), e);
            return false;
        }
    }

    @Override
    public void saveOrUpdate(SchedulerJobInfo scheduleJob) throws Exception {
        if (scheduleJob.getCronExpression().length() > 0) {
            scheduleJob.setJobClass(CustomerBalanceJob.class.getName());
            scheduleJob.setCronJob(true);
        } else {
            scheduleJob.setJobClass(SimpleJob.class.getName());
            scheduleJob.setCronJob(false);
            scheduleJob.setRepeatTime((long) 1);
        }
        if (StringUtils.isEmpty(scheduleJob.getJobId())) {
            log.info("Job Info: {}", scheduleJob);
            scheduleNewJob(scheduleJob);
        } else {
            updateScheduleJob(scheduleJob);
        }
        scheduleJob.setDesc("i am job number " + scheduleJob.getJobId());
        scheduleJob.setInterfaceName("interface_" + scheduleJob.getJobId());
        log.info(">>>>> jobName = [" + scheduleJob.getJobName() + "]" + " created.");
    }

    private void scheduleNewJob(SchedulerJobInfo scheduleJob) {
        try {
            Scheduler scheduler = this.schedulerFactoryBean.getScheduler();

            JobDetail jobDetail = JobBuilder
                    .newJob((Class<? extends QuartzJobBean>) Class.forName(scheduleJob.getJobClass()))
                    .withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup()).build();
            if (!scheduler.checkExists(jobDetail.getKey())) {

                jobDetail = this.scheduleCreator.createJobDetail(
                        (Class<? extends QuartzJobBean>) Class.forName(scheduleJob.getJobClass()), false, context,
                        scheduleJob.getJobName(), scheduleJob.getJobGroup());

                Trigger trigger;
                if (scheduleJob.getCronJob()) {
                    trigger = this.scheduleCreator.createCronTrigger(scheduleJob.getJobName(), new Date(),
                            scheduleJob.getCronExpression(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
                } else {
                    trigger = this.scheduleCreator.createSimpleTrigger(scheduleJob.getJobName(), new Date(),
                            scheduleJob.getRepeatTime(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
                }
                scheduler.scheduleJob(jobDetail, trigger);
                scheduleJob.setJobStatus("SCHEDULED");
                this.schedulerRepository.save(scheduleJob);
                log.info(">>>>> jobName = [" + scheduleJob.getJobName() + "]" + " scheduled.");
            } else {
                log.error("scheduleNewJobRequest.jobAlreadyExist");
            }
        } catch (ClassNotFoundException e) {
            log.error("Class Not Found - {}", scheduleJob.getJobClass(), e);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void updateScheduleJob(SchedulerJobInfo scheduleJob) {
        Trigger newTrigger;
        if (scheduleJob.getCronJob()) {
            newTrigger = this.scheduleCreator.createCronTrigger(scheduleJob.getJobName(), new Date(),
                    scheduleJob.getCronExpression(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        } else {
            newTrigger = this.scheduleCreator.createSimpleTrigger(scheduleJob.getJobName(), new Date(), scheduleJob.getRepeatTime(),
                    SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        }
        try {
            this.schedulerFactoryBean.getScheduler().rescheduleJob(TriggerKey.triggerKey(scheduleJob.getJobName()), newTrigger);
            scheduleJob.setJobStatus("EDITED & SCHEDULED");
            this.schedulerRepository.save(scheduleJob);
            log.info(">>>>> jobName = [" + scheduleJob.getJobName() + "]" + " updated and scheduled.");
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }

}
