package dev.luke10x.rsynccycle.provision;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Slf4j
@Service
public class ProcessExecutor {

    public void execute(String[] commandArgs) {
        final ProcessBuilder processBuilder = new ProcessBuilder(commandArgs);
        Process process = null;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CompletableFuture<Void> errFut = readLinesAsync(
            process.getErrorStream(),
            line -> {
                log.info("STDERR: {}", line);
            }
        );
        CompletableFuture<Void> outFut = readLinesAsync(
            process.getInputStream(),
            line -> {
                log.info("STDOUT: {}", line);
            }
        );

        try {
            process.waitFor(20, TimeUnit.SECONDS);
            int code = process.exitValue();
            CompletableFuture.allOf(outFut, errFut).join();
            log.info("Exit code {}", code);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static CompletableFuture<Void> readLinesAsync(
        InputStream is,
        Consumer<String> lineHandler
    ) {
        return CompletableFuture.runAsync(() -> {
            try (
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
            ) {
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    lineHandler.accept(inputLine);
                }
            } catch (Throwable e) {
                throw new RuntimeException("problem with executing program", e);
            }
        });
    }
}
