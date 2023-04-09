package dev.luke10x.rsynccycle.events;

import dev.luke10x.rsynccycle.management.Chore;
import org.springframework.context.ApplicationEvent;

public class ChoreRemovedEvent extends ApplicationEvent {

    private final Long choreId;

    public ChoreRemovedEvent(Chore chore) {
        super(chore);
        choreId = chore.getId();
    }
}
