package ru.VYurkin.UpdateSpringBoot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.VYurkin.UpdateSpringBoot.contollers.BooksController;
import ru.VYurkin.UpdateSpringBoot.contollers.PeopleController;
import ru.VYurkin.UpdateSpringBoot.models.Book;
import ru.VYurkin.UpdateSpringBoot.models.Person;
import ru.VYurkin.UpdateSpringBoot.services.BooksService;
import ru.VYurkin.UpdateSpringBoot.services.PeopleService;
import ru.VYurkin.UpdateSpringBoot.util.BookValidator;
import ru.VYurkin.UpdateSpringBoot.util.PersonValidator;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class PeopleControllerTest {
    @MockBean
    private PeopleService peopleService;

    @MockBean
    private PersonValidator personValidator;

    @MockBean
    private BooksController booksController;
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void shouldGetPeople() throws Exception {
        //given
        Person person = new Person("name", 1999);
        Mockito.when(peopleService.indexPerson()).thenReturn(Collections.singletonList(person));

        //when
        mockMvc.perform(get("/people"))
                .andExpect(status().isOk())
                .andExpect(view().name( "people/indexPeople"))
                .andExpect(model().attribute("people", Collections.singletonList(person)));
        //then
        Mockito.verify(peopleService).indexPerson();
    }

    @Test
    public void shouldShowPerson() throws Exception {
        //given
        List<Book> books = new ArrayList<>();
        books.add(new Book("name", "author", 1999, new Date()));
        books.add(new Book("name", "author", 1999, new Date()));
        books.add(new Book("name", "author", 1999, new Date()));

        Person person = new Person("name", 1999);
        person.setPersonId(1);

        Mockito.when(peopleService.showPerson(1)).thenReturn(person);
        Mockito.when(peopleService.listBookFromPerson(1)).thenReturn(books);

        //when
        mockMvc.perform(get("/people/1"))
                .andExpect(status().isOk())
                .andExpect(view().name( "people/showPerson"))
                .andExpect(model().attribute("person", person))
                .andExpect(model().attribute("books", books));
        //then
        Mockito.verify(peopleService).showPerson(1);
        Mockito.verify(peopleService).listBookFromPerson(1);
    }

    @Test
    public void shouldNewPerson() throws Exception {
        //given
        Person person = new Person("name", 1999);
        //when
        mockMvc.perform(get("/people/new")
                .flashAttr("person", person))
                .andExpect(status().isOk())
                .andExpect(view().name( "people/newPerson"));
    }

    @Test
    public void shouldCreatePerson() throws Exception {
        //given
        Person person = new Person("Иванов Иван Иванович", 1999);
        //when
        mockMvc.perform((post("/people")
                .flashAttr("person", person)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name( "redirect:/people"));

        person.setName("name");

        mockMvc.perform((post("/people")
                .flashAttr("person", person)))
                .andExpect(status().isOk())
                .andExpect(view().name( "people/newPerson"));
        //then
        Mockito.verify(peopleService).savePerson(person);

    }

    @Test
    public void shouldEditPerson() throws Exception {
        //given
        Person person = new Person("name", 1999);
        Mockito.when(peopleService.showPerson(1)).thenReturn(person);
        //when
        mockMvc.perform(get("/people/1/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("person", person))
                .andExpect(view().name( "people/editPerson"));
        //then
        Mockito.verify(peopleService).showPerson(1);
    }

    @Test
    public void shouldUpdatePerson() throws Exception {
        //given
        Person person = new Person("Иванов Иван Иванович", 1999);
        //when
        mockMvc.perform((patch("/people/1")
                .flashAttr("person", person)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name( "redirect:/people"));

        person.setName("name");

        mockMvc.perform((patch("/people/1")
                .flashAttr("person", person)))
                .andExpect(status().isOk())
                .andExpect(view().name( "people/editPerson"));
        //then
        Mockito.verify(peopleService).updatePerson(1, person);

    }

    @Test
    public void shouldDeletePerson() throws Exception {
        //when
        mockMvc.perform((delete("/people/1")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name( "redirect:/people"));

        //then
        Mockito.verify(peopleService).deletePerson(1);
    }

}
