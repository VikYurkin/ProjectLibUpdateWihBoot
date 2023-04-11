package ru.VYurkin.UpdateSpringBoot.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.VYurkin.UpdateSpringBoot.contollers.PeopleController;
import ru.VYurkin.UpdateSpringBoot.models.Book;
import ru.VYurkin.UpdateSpringBoot.models.Person;
import ru.VYurkin.UpdateSpringBoot.services.BooksService;
import ru.VYurkin.UpdateSpringBoot.services.PeopleService;
import ru.VYurkin.UpdateSpringBoot.util.BookValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class BooksControllerTest {
    @MockBean
    private PeopleController peopleController;

    @MockBean
    private BookValidator bookValidator;

    @MockBean
    private BooksService booksService;
    @MockBean
    private PeopleService peopleService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void shouldIndexBooks() throws Exception {
        //given
        Book book = new Book();
        Mockito.when(booksService.indexBook(null, null, null)).thenReturn(Collections.singletonList(book));

        //when
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name( "books/indexBooks"))
                .andExpect(model().attribute("books", Collections.singletonList(book)));
        //then
        Mockito.verify(booksService).indexBook(null, null, null);
    }

    @Test
    public void shouldSearchBooks() throws Exception {
        //given
        Book book = new Book();
        Person person = new Person();
        Mockito.when(booksService.searchBook("name")).thenReturn(book);
        Mockito.when(booksService.searchBookOwner("name")).thenReturn(person);
        //when
        mockMvc.perform(get("/books/search")
                        .param("startingWith", "name"))
                .andExpect(status().isOk())
                .andExpect(view().name( "books/searchBook"))
                .andExpect(model().attribute("book", book))
                .andExpect(model().attribute("personWithBook", person))
                .andExpect(model().attribute("startingWith", "name"));
    }

    @Test
    public void shouldShowBook() throws Exception {
        //given
        List<Person> people = new ArrayList<>();
        people.add(new Person());
        people.add(new Person());
        people.add(new Person());

        Person personWithBook = new Person("name", 1999);

        Book book =new Book();

        Mockito.when(booksService.showBook(1)).thenReturn(book);
        Mockito.when(booksService.owner(1)).thenReturn(personWithBook);
        Mockito.when(peopleService.potentialOwner(personWithBook)).thenReturn(people);

        //when
        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(view().name( "books/showBook"))
                .andExpect(model().attribute("book", book))
                .andExpect(model().attribute("personWithBook", personWithBook))
                .andExpect(model().attribute("people", people));
    }

    @Test
    public void shouldPersonGetBook() throws Exception {
        //given
        Person person = new Person();
        //when
        mockMvc.perform((patch("/books/1/add")
                .flashAttr("person", person)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name( "redirect:/books/{bookId}"));

        //then
        Mockito.verify(booksService).personGetBook(1, person);
    }

    @Test
    public void shouldPersonReturnBook() throws Exception {

        //when
        mockMvc.perform((patch("/books/1/take_of")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name( "redirect:/books/{bookId}"));

        //then
        Mockito.verify(booksService).personReturnBook(1);
    }



    @Test
    public void shouldNewBook() throws Exception {
        //given
        Book book = new Book();
        //when
        mockMvc.perform(get("/books/new")
                .flashAttr("book", book))
                .andExpect(status().isOk())
                .andExpect(view().name( "books/newBook"));
    }

    @Test
    public void shouldCreateBook() throws Exception {
        //given
        Book book = new Book("name", "author", 1999, new Date());
        //when
        mockMvc.perform((post("/books")
                .flashAttr("book", book)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name( "redirect:/books"));

        book.setNameBook(null);

        mockMvc.perform((post("/books")
                .flashAttr("book", book)))
                .andExpect(status().isOk())
                .andExpect(view().name( "books/newBook"));
        //then
        Mockito.verify(booksService).saveBook(book);
    }

    @Test
    public void shouldEditBook() throws Exception {
        //given
        Book book = new Book();
        Mockito.when(booksService.showBook(1)).thenReturn(book);
        //when
        mockMvc.perform(get("/books/1/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", book))
                .andExpect(view().name( "books/editBook"));
        //then
        Mockito.verify(booksService).showBook(1);
    }

    @Test
    public void shouldUpdateBook() throws Exception {
        //given
        Book book = new Book("name", "author", 1999, new Date());
        //when
        mockMvc.perform((patch("/books/1")
                .flashAttr("book", book)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name( "redirect:/books"));

        book.setNameBook(null);

        mockMvc.perform((patch("/books/1")
                .flashAttr("book", book)))
                .andExpect(status().isOk())
                .andExpect(view().name( "books/editBook"));
        //then
        Mockito.verify(booksService).updateBook(1, book);
    }

    @Test
    public void shouldDeleteBook() throws Exception {
        //when
        mockMvc.perform((delete("/books/1")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name( "redirect:/books"));

        //then
        Mockito.verify(booksService).deleteBook(1);
    }
}
