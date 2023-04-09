package dev.luke10x.rsynccycle.management;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

import static dev.luke10x.rsynccycle.management.ChoreType.*;

/**
 * Sequence provider which conditionally adds constraints whose
 * annotations have groups params
 * And lastly it adds those without groups defined
 */
public class ChoreConstraintGroupSequenceProvider
    implements DefaultGroupSequenceProvider<Chore> {

    @Override
    public List<Class<?>> getValidationGroups(Chore chore) {

        List<Class<?>> sequence = new ArrayList<>();

        if (chore != null) {
            // Apply all validation rules from groups
            // only if *type* has given value
            if (LOCAL_CONTAINER.equals(chore.getType())) {
                sequence.add(LocalContainerConstraints.class);
            }
            if (REMOTE_CONTAINER.equals(chore.getType())) {
                sequence.add(RemoteContainerConstraints.class);
            }
            if (REMOTE_SSH_CONTAINER.equals(chore.getType())) {
                sequence.add(RemoteSshContainerConstraints.class);
            }
            if (REMOTE_SSH.equals(chore.getType())) {
                sequence.add(RemoteSshConstraints.class);
            }
        }
        // Apply all validation rules from default group
        sequence.add(Chore.class);

        return sequence;
    }
}