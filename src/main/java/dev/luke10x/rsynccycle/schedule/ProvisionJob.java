package dev.luke10x.rsynccycle.schedule;

import dev.luke10x.rsynccycle.provision.ProvisionService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static dev.luke10x.rsynccycle.schedule.ProvisionJob.Constants.RSYNC_CMD_KEY;

@Slf4j
public class ProvisionJob implements Job {

    class Constants {
        static final String RSYNC_CMD_KEY = "rsync_command";
        static final String CHORE_ID_KEY = "chore_id";
        static final String GRP_EXEC = "exec_group_process";
    }

    @Autowired
    LastProvisionDurationTimeRepository lastProvisionDurationTimeRepository;

    @Autowired
    ProvisionService provisionService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();

        String rsyncCommand = jobDataMap.getString(RSYNC_CMD_KEY);
        Long choreId = jobDataMap.getLong(Constants.CHORE_ID_KEY);

        System.out.println("👑 Job started at " + new Date() + " CMD: " + rsyncCommand);

        Instant start = new Date().toInstant();
        lastProvisionDurationTimeRepository.start(choreId);

        try {
            provisionService.execute(rsyncCommand);
        } catch (Exception ex) {
            log.error("Execution error, logging and forgiving: {}", ex);
        }

        Instant end = new Date().toInstant();
        lastProvisionDurationTimeRepository.end(choreId, Duration.between(start, end));
        System.out.println("👑 Job executed at " + lastProvisionDurationTimeRepository.get(choreId) +  " CMD: " + rsyncCommand);
    }
}
