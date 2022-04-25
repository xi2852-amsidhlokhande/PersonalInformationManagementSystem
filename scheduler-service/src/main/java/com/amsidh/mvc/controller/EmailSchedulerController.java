package com.amsidh.mvc.controller;

import com.amsidh.mvc.EmailJob.EmailJob;
import com.amsidh.mvc.payload.EmailRequest;
import com.amsidh.mvc.payload.EmailResponse;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
public class EmailSchedulerController {

    @Autowired
    private Scheduler scheduler;

    @PostMapping("/schedule/email")
    public ResponseEntity<EmailResponse> submitJob(@Valid @RequestBody EmailRequest emailRequest) {
        try {
            JobDetail emailJobDetail = buildJobDetail(emailRequest);
            Trigger emailTrigger = buildTrigger(emailJobDetail);
            scheduler.scheduleJob(emailJobDetail, emailTrigger);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new EmailResponse(true, "Email Scheduled Successfully!", emailJobDetail.getKey().getName(), emailJobDetail.getKey().getGroup()));

        } catch (SchedulerException schedulerException) {
            log.error("Error while scheduling email: ", schedulerException);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EmailResponse(false, "Error while scheduling email. Please try again later!"));
        }
    }


    @GetMapping("/health/check")
    public ResponseEntity<String> healthStatus() {
        return ResponseEntity.ok("Email Scheduler Service Working");
    }

    private JobDetail buildJobDetail(EmailRequest emailRequest) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("email", emailRequest.getEmail());
        jobDataMap.put("subject", emailRequest.getSubject());
        jobDataMap.put("body", emailRequest.getBody());

        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs-group")
                .withDescription("Send Email Job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildTrigger(JobDetail jobDetail) {

        return TriggerBuilder.newTrigger()
                .withIdentity(jobDetail.getKey().getName(), "email-triggers-group")
                .withDescription("Send Email Trigger")
                .startNow()
                .forJob(jobDetail)
                .withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(1))
                .build();
    }

}
