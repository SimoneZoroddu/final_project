package org.lessons.java_final.final_project.controller;

import org.lessons.java_final.final_project.model.Game;
import org.lessons.java_final.final_project.service.GameService;
import org.lessons.java_final.final_project.service.GenreService;
import org.lessons.java_final.final_project.service.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private PlatformService platformService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("games", gameService.findAll());
        return "games/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        model.addAttribute("game", gameService.getById(id));
        return "games/show";
    }

    @GetMapping("create")
    public String create(Model model) {
        model.addAttribute("game", new Game());
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("platforms", platformService.findAll());
        return "games/create-or-edit";
    }

    @PostMapping("create")
    public String store(@Valid @ModelAttribute("game") Game formGame, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genres", genreService.findAll());
            model.addAttribute("platforms", platformService.findAll());
            return "games/create-or-edit";
        }
        gameService.create(formGame);
        return "redirect:/games";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("game", gameService.getById(id));
        model.addAttribute("edit", true);
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("platforms", platformService.findAll());
        return "games/create-or-edit";
    }

    @PostMapping("edit/{id}")
    public String update(@Valid @ModelAttribute("game") Game formGame, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genres", genreService.findAll());
            model.addAttribute("platforms", platformService.findAll());
            return "games/create-or-edit";
        }
        gameService.update(formGame);
        return "redirect:/games";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        gameService.delete(gameService.getById(id));
        return "redirect:/games";
    }

}
