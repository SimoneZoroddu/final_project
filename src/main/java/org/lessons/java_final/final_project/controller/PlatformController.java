package org.lessons.java_final.final_project.controller;

import org.lessons.java_final.final_project.model.Platform;
import org.lessons.java_final.final_project.model.Game;
import org.lessons.java_final.final_project.service.PlatformService;
import org.lessons.java_final.final_project.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/platforms")
public class PlatformController {


    @Autowired
    private PlatformService platformService;

    @Autowired
    private GameService gameService;



    @GetMapping
    public String index(Model model){

        model.addAttribute("platforms", platformService.findAll());

        return "platforms/index";
    }



    @GetMapping("/create")
    public String create(Model model){

        model.addAttribute("platform", new Platform());
        model.addAttribute("edit", false);

        return "platforms/create-or-edit";
    }



    @PostMapping("/create")
    public String store(
            @Valid @ModelAttribute("platform") Platform platform,
            BindingResult bindingResult,
            Model model){


        if(bindingResult.hasErrors()){
            model.addAttribute("edit", false);
            return "platforms/create-or-edit";
        }


        platformService.create(platform);

        return "redirect:/platforms";
    }




    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable Integer id,
            Model model){


        model.addAttribute("platform", platformService.getById(id));
        model.addAttribute("edit", true);

        return "platforms/create-or-edit";
    }




    @PostMapping("/edit/{id}")
    public String update(
            @Valid @ModelAttribute("platform") Platform platform,
            BindingResult bindingResult,
            Model model){


        if(bindingResult.hasErrors()){
            model.addAttribute("edit", true);
            return "platforms/create-or-edit";
        }


        platformService.update(platform);

        return "redirect:/platforms";
    }




    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){

        // Platform e' il lato inverso (mappedBy) della @ManyToMany.
        // La tabella game_platform viene gestita dal lato proprietario Game.platforms.
        // Prima togliamo la Platform da tutti i Game collegati e poi la eliminiamo.
        Platform platformToDelete = platformService.getById(id);

        for (Game linkedGame : platformToDelete.getGames()) {
            linkedGame.getPlatforms().remove(platformToDelete);
            gameService.update(linkedGame);
        }

        platformService.delete(platformToDelete);

        return "redirect:/platforms";
    }
}