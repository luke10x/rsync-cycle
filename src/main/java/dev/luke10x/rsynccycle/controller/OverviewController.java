package dev.luke10x.rsynccycle.controller;

import dev.luke10x.rsynccycle.management.Chore;
import dev.luke10x.rsynccycle.schedule.Overview;
import dev.luke10x.rsynccycle.management.ChoreService;
import dev.luke10x.rsynccycle.schedule.OverviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.GONE;

@Controller
@RequiredArgsConstructor
public class OverviewController {

    private final ChoreService choreService;
    private final OverviewService overviewService;

    @GetMapping("/overview/{id}")
    public String showOverview(@PathVariable("id") Long choreId, Model model) {
        Chore chore = choreService.getChoreById(choreId).orElseGet(() -> {
            throw new HttpClientErrorException(GONE, "This is gone now");
        });

        Overview overview = overviewService.getOverview(choreId);

        model.addAttribute("chore", chore);
        model.addAttribute("overview", overview);

        return "overview";
    }
}

