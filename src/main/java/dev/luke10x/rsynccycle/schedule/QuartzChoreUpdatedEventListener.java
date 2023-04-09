package dev.luke10x.rsynccycle.schedule;

import dev.luke10x.rsynccycle.events.ChoreUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import static dev.luke10x.rsynccycle.schedule.ProvisionJob.Constants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuartzChoreUpdatedEventListener
    implements ApplicationListener<ChoreUpdatedEvent>
{
    private final Scheduler scheduler;

    @Override
    public void onApplicationEvent(ChoreUpdatedEvent event) {
        System.out.println("💚 Chore updated, so trying to update schedule");

        try {
            updateScheduler(event);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The answer for you people who asking if I want to split this method
     * is NO.
     * I want to see what is happening here in one place.
     * (PRs will be automatically declined)
     */
    private void updateScheduler(ChoreUpdatedEvent event) throws SchedulerException {
        // Desired details are as described in the event
        final Boolean desiredActive = event.getActive();
        final String desiredRsyncCommand = event.getRsyncCommand();
        final int desiredInterval = event.getInterval();

        // Actual details are not final
        // and can be overridden by loaded JobDetail/Trigger
        boolean actualActive = false;
        String actualRsyncCommand = "";
        int actualInterval = 0;

        // Build JobKey/TriggerKey
        final Long choreId = event.getChoreId();
        final String name = String.valueOf(choreId);
        final JobKey jobKey = JobKey.jobKey(name, GRP_EXEC);
        final TriggerKey triggerKey = TriggerKey.triggerKey(name, GRP_EXEC);

        // Load or create JobDetail
        JobDetail jobDetail;
        if (scheduler.checkExists(jobKey)) {
            jobDetail = scheduler.getJobDetail(jobKey);

            JobDataMap jobDataMap = jobDetail.getJobDataMap();
            actualRsyncCommand = jobDataMap.getString(RSYNC_CMD_KEY);
        } else {
            final JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put(RSYNC_CMD_KEY, desiredRsyncCommand);
            jobDataMap.put(CHORE_ID_KEY, choreId);

            jobDetail = JobBuilder.newJob(ProvisionJob.class)
                .withIdentity(name, GRP_EXEC)
                .usingJobData(jobDataMap)
                .build();
        }

        // Load or create Trigger
        Trigger trigger;
        if (scheduler.checkExists(triggerKey)) {
            trigger = scheduler.getTrigger(triggerKey);

            if (trigger instanceof SimpleTrigger) {
                SimpleTrigger simpleTrigger = (SimpleTrigger) trigger;

                long repeatIntervalMillis = simpleTrigger.getRepeatInterval();
                actualInterval = (int) (repeatIntervalMillis / 1000);
            }

            actualActive = true;
        } else {
            trigger = TriggerBuilder.newTrigger()
                .withIdentity(name, GRP_EXEC)
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(desiredInterval)
                    .repeatForever())
                .build();
        }

        // Update the rsync command (It alone does not require reschedule)
        if (!actualRsyncCommand.equals(desiredRsyncCommand)) {
            JobDataMap jobDataMap = jobDetail.getJobDataMap();
            jobDataMap.put(RSYNC_CMD_KEY, desiredRsyncCommand);
        }

        // (Re-)schedule if schedule params (interval) changed
        final boolean needsReschedule = actualInterval != desiredInterval;

        if (actualActive) {
            if (desiredActive) {
                if (needsReschedule) {
                    scheduler.unscheduleJob(triggerKey);
                    scheduler.scheduleJob(jobDetail, trigger);
                    log.info("⏳ Re-schedule previously active job {}", triggerKey);
                }
            } else {
                scheduler.unscheduleJob(triggerKey);
                log.info("⏳ Un-schedule previously active job {}", triggerKey);
            }
        } else {
            if (desiredActive) {
                scheduler.scheduleJob(jobDetail, trigger);
                log.info("⏳ Schedule job which was not active before {}", triggerKey);
            }
        }
    }
}
