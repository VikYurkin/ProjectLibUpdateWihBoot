package ru.VYurkin.UpdateSpringBoot.contollers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.VYurkin.UpdateSpringBoot.models.Person;
import ru.VYurkin.UpdateSpringBoot.services.PeopleService;
import ru.VYurkin.UpdateSpringBoot.util.PersonValidator;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String indexPeople(Model model){
        model.addAttribute("people", peopleService.indexPerson());
        return "people/indexPeople";
    }

    @GetMapping("/{personId}")
    public String showPerson(@PathVariable("personId") int personId, Model model){
        model.addAttribute("person", peopleService.showPerson(personId));
        model.addAttribute("books", peopleService.listBookFromPerson(personId));
        return "people/showPerson";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "people/newPerson";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "people/newPerson";

        peopleService.savePerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{personId}/edit")
    public String editPerson(Model model,@PathVariable("personId") int personId){
        model.addAttribute("person", peopleService.showPerson(personId));
        return "people/editPerson";
    }

    @PatchMapping("/{personId}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("personId") int personId){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "people/editPerson";

        peopleService.updatePerson(personId,person);
        return "redirect:/people";
    }

    @DeleteMapping("/{personId}")
    public String deletePerson(@PathVariable("personId") int personId){
        peopleService.deletePerson(personId);
        return "redirect:/people";
    }

}
