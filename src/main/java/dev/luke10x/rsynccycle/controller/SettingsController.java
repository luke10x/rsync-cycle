package dev.luke10x.rsynccycle.controller;

import dev.luke10x.rsynccycle.management.Chore;
import dev.luke10x.rsynccycle.management.ChoreService;
import dev.luke10x.rsynccycle.management.ImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/settings")
public class SettingsController {

    private final ChoreService choreService;
    private final ImportService importService;

    @GetMapping
    public String settingsList(Model model) {
        return "settings";
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public String importSettings(@RequestParam("file") MultipartFile file) throws IOException {

        InputStream inputStream = file.getInputStream();

        importService.importData(inputStream);
        return("redirect:/settings");
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportChores() throws IOException {
        // generate YAML content
        String yamlContent = generateYaml();
//
//        // set headers for response
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("chores.yaml").build());
        // add datetime stamp to filename
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm_ss");
        String filename = "chores_" + LocalDateTime.now().format(formatter) + ".yaml";

        // set headers for response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(filename).build());

        // convert YAML content to byte array and return as response
        return new ResponseEntity<>(yamlContent.getBytes(), headers, HttpStatus.OK);
    }

    private String generateYaml() {
        // get all chores from the database
        List<Chore> chores = (List<Chore>) choreService.getAllChores();

        // create a YAML representation of the chores
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        Yaml yaml = new Yaml(options);
        return yaml.dump(chores);
    }
}