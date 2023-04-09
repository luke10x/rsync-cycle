package dev.luke10x.rsynccycle.events;

import dev.luke10x.rsynccycle.management.Chore;
import lombok.Value;
import org.springframework.context.ApplicationEvent;

@Value
public class ChoreUpdatedEvent extends ApplicationEvent {

    private final Long choreId;
    private final Boolean active;
    private final String rsyncCommand;
    private final int interval;

    public ChoreUpdatedEvent(Chore chore) {
        super(chore);
        choreId = chore.getId();
        active = chore.getActive();
        rsyncCommand = chore.buildRsyncCommand();
        interval = chore.getInterval();
    }

}
