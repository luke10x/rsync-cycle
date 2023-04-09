package dev.luke10x.rsynccycle.management;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImportService {

    private final ChoreService choreService;

    public void importData(InputStream inputStream) {
        Yaml yaml = new Yaml();
        List<Chore> data = yaml.load(inputStream);

        data.stream().forEach(chore -> {
            log.info("👽 Chore imported: {}", chore);
            choreService.saveChore(chore);
        });
    }
}
