package dev.luke10x.rsynccycle.schedule;

import lombok.Builder;
import lombok.Value;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Value
@Builder
public class Overview {
    private final ZonedDateTime previousFireDateTime;
    private final ZonedDateTime nextFireDateTime;
    private final ZonedDateTime schedulerDateTime;
    private final boolean active;
    private final boolean executing;
    private final long executionTime;

    public Long getElapsed() {
        return Duration.between(previousFireDateTime, schedulerDateTime).toMillis();
    }

    public Duration getRemaining() {
        return Duration.between(schedulerDateTime, nextFireDateTime);
    }

    public float getDone() {
        Long durationMilis = getDuration().toMillis();
        return (getElapsed().floatValue() / durationMilis.floatValue()) * 100;
    }

    public Duration getDuration() {
        return Duration.between(previousFireDateTime, nextFireDateTime);
    }

    public String getNextFireNiceDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy 'at' h:mm a z");
        return nextFireDateTime.format(formatter);
    }

    public double getWork() {
        return executing
            ? getDone()
            : 100 * executionTime / getDuration().toMillis();
    }

    public double getIdle() {
        return Math.max(0, (getDone() - getWork()));
    }
}
