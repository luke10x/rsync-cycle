package dev.luke10x.rsynccycle.events;

import dev.luke10x.rsynccycle.management.Chore;
import lombok.Value;
import org.springframework.context.ApplicationEvent;


@Value
public class ChoreAddedEvent extends ApplicationEvent {

    private final Long choreId;

    public ChoreAddedEvent(Chore chore) {
        super(chore);
        choreId = chore.getId();
    }
}
