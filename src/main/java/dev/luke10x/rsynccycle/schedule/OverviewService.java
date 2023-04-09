package dev.luke10x.rsynccycle.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import static dev.luke10x.rsynccycle.schedule.ProvisionJob.Constants.GRP_EXEC;

@Slf4j
@Service
@RequiredArgsConstructor
public class
OverviewService {

    private final Scheduler scheduler;

    private final LastProvisionDurationTimeRepository lastProvisionDurationTimeRepository;

    public Overview getOverview(Long choreId) {
        final String name = String.valueOf(choreId);
        final TriggerKey triggerKey = TriggerKey.triggerKey(name, GRP_EXEC);

        Trigger trigger;
        try {
            trigger = scheduler.getTrigger(triggerKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

        if (trigger instanceof SimpleTrigger) {

            Date previousFireTime = trigger.getPreviousFireTime();
            Date nextFireTime = trigger.getNextFireTime();
            Date schedulerTime = new Date();

            boolean executing;
            try {
                executing = scheduler.getCurrentlyExecutingJobs().stream()
                    .anyMatch(ctx -> ctx.getTrigger().getKey().equals(triggerKey));
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }

            ZonedDateTime previousFireDateTime = previousFireTime.toInstant().atZone(ZoneId.systemDefault());
            ZonedDateTime nextFireDateTime = nextFireTime.toInstant().atZone(ZoneId.systemDefault());
            ZonedDateTime schedulerDateTime = schedulerTime.toInstant().atZone(ZoneId.systemDefault());

             Duration executionTime = executing
                ? Duration.between(previousFireDateTime, schedulerDateTime)
                : lastProvisionDurationTimeRepository.get(choreId);

            return Overview.builder()
                .previousFireDateTime(previousFireDateTime)
                .nextFireDateTime(nextFireDateTime)
                .schedulerDateTime(schedulerDateTime)
                .executionTime(executionTime.toMillis())
                .executing(executing)
                .active(true)
                .build();
        }

        // Null object - not active
        return Overview.builder().active(false).build();
    }
}
