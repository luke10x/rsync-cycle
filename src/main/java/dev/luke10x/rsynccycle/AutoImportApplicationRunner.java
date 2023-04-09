package dev.luke10x.rsynccycle;
import dev.luke10x.rsynccycle.management.ImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class AutoImportApplicationRunner implements ApplicationRunner {

    private final ImportService importService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (args.containsOption("importFile")) {
            String fileName = args.getOptionValues("importFile").get(0);
            log.info("📥 Importing from file {}", fileName);

            File importFile = new File(fileName);
            InputStream inputStream = new FileInputStream(importFile);
            importService.importData(inputStream);
        } else {
            log.info("📥 Import not required");
        }
    }
}
