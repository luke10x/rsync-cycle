package dev.luke10x.rsynccycle.controller;

import dev.luke10x.rsynccycle.management.ChoreType;
import dev.luke10x.rsynccycle.management.ChoreService;
import dev.luke10x.rsynccycle.management.Chore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Controller
@RequestMapping("/chores")
@RequiredArgsConstructor
public class ChoreController {

    private final ChoreService choreService;

    @GetMapping
    public String list(Model model) {
        List<Chore> chores = (List<Chore>) choreService.getAllChores();

        model.addAttribute("chores", chores);
        return "list";
    }

    @GetMapping("/init")
    public String selectType(Model model) {
        Chore chore = new Chore();
        model.addAttribute("chore", chore);
        return "init";
    }

    @GetMapping("/new")
    public String createNewForm(
        Model model,
        @Param("type") ChoreType type
    ) {
        Chore chore = new Chore();
        chore.setType(type);
        model.addAttribute("chore", chore);
        model.addAttribute("hasErrors", false);
        return "form";
    }

    @PostMapping("/new")
    public String create(
        @ModelAttribute("chore") @Valid Chore chore,
        BindingResult result,
        Model model // Has to be last
    ) {
        if (result.hasErrors()) {
            model.addAttribute("hasErrors", true);
            return "form";
        }
        choreService.saveChore(chore);
        return "redirect:/chores";
    }

    @PostMapping("/{id}")
    public String update(
        @PathVariable("id") Long id,
        @ModelAttribute("chore") @Valid Chore chore,
        BindingResult result,
        Model model // Has to be last
    ) {
        if (result.hasErrors()) {
            model.addAttribute("hasErrors", true);
            return "form";
        }

        chore.setId(id);
        choreService.saveChore(chore);
        return "redirect:/chores";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model) {
        Chore chore = choreService.getChoreById(id).orElseGet(() -> {
            throw new HttpClientErrorException(GONE, "This is gone now");
        });
        model.addAttribute("chore", chore);
        return "form";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        choreService.deleteChoreById(id);
        return "redirect:/chores";
    }

    @PostMapping("/{id}/start")
    public String startChore(@PathVariable Long id) {
        Chore chore = choreService.getChoreById(id).orElseGet(() -> {
            throw new HttpClientErrorException(GONE, "This is gone now");
        });
        chore.setActive(true);
        choreService.saveChore(chore);
        return "redirect:/chores";
    }

    @PostMapping("/{id}/pause")
    public String pauseChore(@PathVariable Long id) {
        Chore chore = choreService.getChoreById(id).orElseGet(() -> {
            throw new HttpClientErrorException(GONE, "This is gone now");
        });
        chore.setActive(false);
        choreService.saveChore(chore);
        return "redirect:/chores";
    }
}