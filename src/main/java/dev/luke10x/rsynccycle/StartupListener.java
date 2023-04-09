package dev.luke10x.rsynccycle;

import lombok.RequiredArgsConstructor;
import org.quartz.Scheduler;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartupListener implements ApplicationListener<ApplicationReadyEvent> {

    private final Scheduler scheduler;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException("Failed to start scheduler", e);
        }
    }
}
