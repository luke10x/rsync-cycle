package dev.luke10x.rsynccycle.schedule;

import dev.luke10x.rsynccycle.events.ChoreAddedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class QuartzChoreAddedEventListener
    implements ApplicationListener<ChoreAddedEvent>
{
    @Override
    public void onApplicationEvent(ChoreAddedEvent event) {
        System.out.println("💚 Added actually does not matter for quartz");
    }
}
