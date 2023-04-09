package dev.luke10x.rsynccycle.management;

import dev.luke10x.rsynccycle.events.ChoreAddedEvent;
import dev.luke10x.rsynccycle.events.ChoreRemovedEvent;
import dev.luke10x.rsynccycle.events.ChoreUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

@Component
@RequiredArgsConstructor
public class ChoreListener {

    private final ApplicationEventPublisher publisher;

    @PostPersist
    public void afterPersist(Chore chore) {
        publisher.publishEvent(new ChoreAddedEvent(chore));
    }

    @PostUpdate
    public void afterUpdate(Chore chore) {
        publisher.publishEvent(new ChoreUpdatedEvent(chore));
    }

    @PostRemove
    public void afterRemove(Chore chore) {
        publisher.publishEvent(new ChoreRemovedEvent(chore));
    }
}