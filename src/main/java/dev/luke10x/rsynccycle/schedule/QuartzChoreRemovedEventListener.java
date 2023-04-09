package dev.luke10x.rsynccycle.schedule;

import dev.luke10x.rsynccycle.events.ChoreRemovedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class QuartzChoreRemovedEventListener implements
    ApplicationListener<ChoreRemovedEvent>
{
    @Override
    public void onApplicationEvent(ChoreRemovedEvent event) {
        System.out.println("💚 Buy buy");
    }
}
