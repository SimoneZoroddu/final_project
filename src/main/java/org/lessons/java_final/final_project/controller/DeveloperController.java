package org.lessons.java_final.final_project.controller;

import org.lessons.java_final.final_project.model.Developer;
import org.lessons.java_final.final_project.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/developers")
public class DeveloperController {

    @Autowired
    private DeveloperService developerService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("developers", developerService.findAll());
        return "developers/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("developer", new Developer());
        model.addAttribute("edit", false);
        return "developers/create-or-edit";
    }

    @PostMapping("/create")
    public String store(
            @Valid @ModelAttribute("developer") Developer developer,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("edit", false);
            return "developers/create-or-edit";
        }

        developerService.create(developer);
        return "redirect:/developers";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("developer", developerService.getById(id));
        model.addAttribute("edit", true);
        return "developers/create-or-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(
            @PathVariable Integer id,
            @Valid @ModelAttribute("developer") Developer formDeveloper,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            formDeveloper.setId(id);
            model.addAttribute("edit", true);
            return "developers/create-or-edit";
        }

        Developer existingDeveloper = developerService.getById(id);
        existingDeveloper.setName(formDeveloper.getName());
        developerService.update(existingDeveloper);

        return "redirect:/developers";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        developerService.delete(developerService.getById(id));
        return "redirect:/developers";
    }
}
