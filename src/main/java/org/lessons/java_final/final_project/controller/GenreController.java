package org.lessons.java_final.final_project.controller;

import org.lessons.java_final.final_project.model.Genre;
import org.lessons.java_final.final_project.model.Game;
import org.lessons.java_final.final_project.service.GenreService;
import org.lessons.java_final.final_project.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @Autowired
    private GameService gameService;


    @GetMapping
    public String index(Model model) {
        model.addAttribute("genres", genreService.findAll());
        return "genres/index";
    }


    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("genre", new Genre());
        model.addAttribute("edit", false);

        return "genres/create-or-edit";
    }


    @PostMapping("/create")
    public String store(
            @Valid @ModelAttribute("genre") Genre genre,
            BindingResult bindingResult,
            Model model) {


        if (bindingResult.hasErrors()) {
            model.addAttribute("edit", false);
            return "genres/create-or-edit";
        }


        genreService.create(genre);

        return "redirect:/genres";
    }


    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable Integer id,
            Model model) {


        model.addAttribute("genre", genreService.getById(id));
        model.addAttribute("edit", true);

        return "genres/create-or-edit";
    }


    @PostMapping("/edit/{id}")
    public String update(
            @Valid @ModelAttribute("genre") Genre genre,
            BindingResult bindingResult,
            Model model) {


        if (bindingResult.hasErrors()) {
            model.addAttribute("edit", true);
            return "genres/create-or-edit";
        }


        genreService.update(genre);

        return "redirect:/genres";
    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {

        Genre genreToDelete = genreService.getById(id);

        for (Game linkedGame : genreToDelete.getGames()) {
            linkedGame.getGenres().remove(genreToDelete);
            gameService.update(linkedGame);
        }

        genreService.delete(genreToDelete);

        return "redirect:/genres";
    }
}