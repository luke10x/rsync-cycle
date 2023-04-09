package dev.luke10x.rsynccycle.schedule;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class LastProvisionDurationTimeRepository {
    private final Map<Long, Duration> map = new ConcurrentHashMap<>();

    public void start(Long choreId) {
        map.remove(choreId);
    }

    public void end(Long choreId, Duration duration) {
        map.put(choreId, duration);
    }

    public Duration get(Long choreId) {
        Duration duration = map.get(choreId);
        if (duration == null) {
            return Duration.of(1, ChronoUnit.MILLIS);
        }
        return duration;
    }
}
