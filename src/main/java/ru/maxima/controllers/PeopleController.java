package ru.maxima.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.maxima.dao.PersonDAO;
import ru.maxima.models.Person;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
   @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String showAllPeople(Model model){
        List<Person> allPeople = personDAO.getAllPeople();

        model.addAttribute("allPeople",allPeople);

        return "people/view-with-all-people";
    }
    @GetMapping("/{id}")
    public String showPersonById(@PathVariable("id") Long id, Model model) {
       Person person = personDAO.findById(id);
       model.addAttribute("personById",person);
       return "people/view-with-person-by-id";
    }
    @GetMapping("/create")
    public String getPageToCreateNewPerson(Model model){
       model.addAttribute("newPerson",new Person());
       return"people/view-to-creat-new-person";
    }
    @PostMapping
    public String createNewPerson(@ModelAttribute("newPerson") Person person){
       personDAO.save(person);

       return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String getPageToEditPerson(Model model, @PathVariable("id") Integer id){
        model.addAttribute("editedPerson",personDAO.findById(Long.valueOf(id)));
        return"people/view-to-edit-person";
    }
    @PostMapping("/{id}")
    public String editPerson(@PathVariable("id") Integer id, @ModelAttribute("editedPerson") Person person) {
       personDAO.update(person,id);
       return "redirect:/people";
    }
    @PostMapping("/{id}/delete")
    public String deletePerson(@PathVariable("id") Integer id){
       personDAO.delete(id);
        return "redirect:/people";
    }
}
