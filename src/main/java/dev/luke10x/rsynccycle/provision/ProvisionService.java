package dev.luke10x.rsynccycle.provision;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ProvisionService {

    private final ProcessExecutor processExecutor;

    public void execute(String rsyncCommand) {
        final String[] commandArgs = parseArgs(rsyncCommand);

        processExecutor.execute(commandArgs);
    }

    private String[] parseArgs(String command) {
        final List<String> tokens = new ArrayList<>();
        final Pattern pattern = Pattern.compile("\"([^\"]*)\"|'([^']*)'|(\\S+)");
        final Matcher m = pattern.matcher(command);
        while (m.find()) {
            if (m.group(1) != null) {
                // Doubly-Quoted string
                tokens.add(m.group(1));
            }
            if (m.group(2) != null) {
                // Quoted string
                tokens.add(m.group(2));
            }
            if (m.group(3) != null) {
                // Non-quoted string
                tokens.add(m.group(3));
            }
        }
        return tokens.toArray(new String[0]);
    }
}
