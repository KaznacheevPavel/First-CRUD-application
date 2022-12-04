package ru.kaznacheev.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kaznacheev.library.dao.person.PeopleDAO;
import ru.kaznacheev.library.model.Person;
import ru.kaznacheev.library.util.PersonValidator;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private PeopleDAO peopleDAO;
    private PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleDAO peopleDAO, PersonValidator personValidator) {
        this.peopleDAO = peopleDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String getPeople(Model model) {
        model.addAttribute("people", peopleDAO.getAll());
        return "person/people";
    }

    @PostMapping()
    public String postPeople(@ModelAttribute("person") Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "person/new";
        }

        peopleDAO.add(person);
        return "redirect:/people";
    }

    @GetMapping("/new")
    public String getPeopleNew(@ModelAttribute("person") Person person) {
        return "person/new";
    }

    @GetMapping("/{id}")
    public String getPeopleId(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleDAO.get(id));
        model.addAttribute("books", peopleDAO.getBooksInfo(id));
        return "person/person";
    }

    @GetMapping("/{id}/edit")
    public String getPeopleIdEdit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleDAO.get(id));
        return "person/edit";
    }

    @PatchMapping("/{id}")
    public String patchPeopleId(@ModelAttribute("person") Person person) {
        peopleDAO.update(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePeopleId(@PathVariable("id") int id) {
        peopleDAO.delete(id);
        return "redirect:/people";
    }


}
