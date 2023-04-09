package dev.luke10x.rsynccycle.management;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

import static dev.luke10x.rsynccycle.management.ChoreType.*;
import static java.lang.String.format;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(ChoreListener.class)
@GroupSequenceProvider(ChoreConstraintGroupSequenceProvider.class)
public class Chore {
    // Enclose interval & user columns in double quotes
    // (Otherwise, it is a problem as INTERVAL & USER is are keywords in H2)

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;
    @NotEmpty(message = "Source path cannot be empty")
    private String source;
    @NotEmpty(message = "Destination path cannot be empty")
    private String destination;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Type has to be selected")
    private ChoreType type;

    @Column(name = "\"USER\"")
    @NotBlank(groups = {RemoteSshConstraints.class, RemoteSshContainerConstraints.class,}, message = "User is required for this")
    private String user;
    private Integer port;
    @NotBlank(groups = {RemoteSshConstraints.class, RemoteSshContainerConstraints.class,}, message = "Public key is required for this")
    private String publicKey;
    @NotBlank(groups = {RemoteSshConstraints.class, RemoteSshContainerConstraints.class,}, message = "Host is required for this")
    private String host;

    @NotBlank(groups = {LocalContainerConstraints.class, RemoteContainerConstraints.class, RemoteSshContainerConstraints.class,}, message = "Container is required for this")
    private String container;

    @Column(name = "\"INTERVAL\"")
    @Min(30)
    @Max(60*60*24)
    private int interval;
    private Boolean active = false;

    /**
     * I know you church of clean-code now want me change this for
     * polymorphism, and without any reasons given let me just say NO
     * and don't even try asking about it again.
     * (PRs trying to "fix" it will be automatically rejected)
     */
    public String buildRsyncCommand() {
        if (type.equals(LOCAL)) {
            return format("rsync -av %s %s", source, destination);
        }
        if (type.equals(LOCAL_CONTAINER)) {
            return format("rsync -av  -e 'docker exec -i' %s:%s %s", container, source, destination);
        }
        if (type.equals(REMOTE_CONTAINER)) {
            return format(
                """
                bash -c "DOCKER_HOST=%s:%d rsync -avz -e 'docker exec -i' %s:%s %s"
                """, "docker-host", 2375, container, source, destination).trim();
        }

        if (type.equals(REMOTE_SSH_CONTAINER)) {
            return format(
                """
                rsync -avz -e "ssh -q %s@%s:%d docker exec -i" %s:%s %s
                """, user, host, port, container, source, destination).trim();
        }
        if (type.equals(REMOTE_SSH)) {
            return format(
                "rsync -e 'ssh -p %d' -av %s@%s:%s",
                port, user, host, source, destination);
        }

        throw new NotYetImplementedException("I don't know how to generate rsync for type: " + String.valueOf(type));
    }
}