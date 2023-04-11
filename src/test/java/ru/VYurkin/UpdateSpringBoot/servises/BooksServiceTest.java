package ru.VYurkin.UpdateSpringBoot.servises;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.VYurkin.UpdateSpringBoot.models.Book;
import ru.VYurkin.UpdateSpringBoot.models.Person;
import ru.VYurkin.UpdateSpringBoot.repositories.BooksRepository;
import ru.VYurkin.UpdateSpringBoot.services.BooksService;

import java.util.*;

@DisplayName("Unit-level testing for BooksService")
public class BooksServiceTest {

    private BooksService booksService;
    private BooksRepository booksRepository;

    @BeforeEach
    public void init() {
        booksRepository = Mockito.mock(BooksRepository.class);
        booksService = new BooksService(booksRepository);
    }

    @Test
    public void shouldProperlySearchBook() {

        //given
        Book book = new Book("name", "author", 1999, new Date());
        String startingWith = "n";
        Mockito.when(booksRepository.findByNameBookStartingWith(startingWith)).thenReturn(Collections.singletonList(book));

        Book findingBook =booksService.searchBook(startingWith);

        //then
        Assertions.assertNotNull(findingBook);
        Assertions.assertEquals(book, findingBook);
    }

    @Test
    public void shouldProperlySearchBookOwner() {

        //given
        Person person = new Person();
        Book book = new Book("name", "author", 1999, new Date());
        book.setOwner(person);
        String startingWith = "n";
        Mockito.when(booksRepository.findByNameBookStartingWith(startingWith)).thenReturn(Collections.singletonList(book));

        Person findingPerson =booksService.searchBookOwner(startingWith);

        //then
        Assertions.assertNotNull(findingPerson);
        Assertions.assertEquals(person, findingPerson);
    }

    @Test
    public void shouldProperlyShowBooks() {

        //given
        String name= "name";
        String author ="author";
        Book book = new Book(name, author, 1999, new Date());
        Mockito.when(booksRepository.findByNameAuthorAndNameBook(name,author)).thenReturn(Collections.singletonList(book));

        List<Book> findingBooks =booksService.showBooks(name, author);

        //then
        Assertions.assertFalse(findingBooks.isEmpty());
        Assertions.assertEquals(book, findingBooks.get(0));
    }

    @Test
    public void shouldProperlyShowBook() {

        //given
        Book book = new Book("name", "author1", 1999, new Date());
        book.setBookId(1);
        Mockito.when(booksRepository.findById(1)).thenReturn(Optional.of(book));

        Book findingBook =booksService.showBook(1);

        //then
        Assertions.assertNotNull(findingBook);
        Assertions.assertEquals(book, findingBook);
    }

    @Test
    public void shouldProperlySaveBook() {

        //given
        Book book = new Book("name", "author1", 1999, new Date());

        Mockito.when(booksRepository.save(book)).thenReturn(book);

        booksService.saveBook(book);

        //then
        Mockito.verify(booksRepository).save(book);
    }

    @Test
    public void shouldProperlyUpdateBook() {

        //given
        Book book = new Book("name", "author1", 1999, new Date());

        Mockito.when(booksRepository.save(book)).thenReturn(book);

        booksService.updateBook(1, book);

        //then
        Mockito.verify(booksRepository).save(book);
        Assertions.assertEquals(1, book.getBookId());
    }

    @Test
    public void shouldProperlyOwner() {

        //given
        Person person = new Person();
        Book book = new Book("name", "author1", 1999, new Date());
        book.setOwner(person);
        Mockito.when(booksRepository.findById(1)).thenReturn(Optional.of(book));

        Person owner =booksService.owner(1);

        //then
        Assertions.assertNotNull(owner);
        Assertions.assertEquals(person, owner);
    }

    @Test
    public void shouldProperlyPersonGetBook() {

        //given
        Person person = new Person();
        Book book = new Book("name", "author1", 1999, new Date());

        Mockito.when(booksRepository.findById(1)).thenReturn(Optional.of(book));

        booksService.personGetBook(1, person);

        //then
        Assertions.assertEquals(person, book.getOwner());
        Assertions.assertEquals(book.getDateOfPerson().getTime(), new Date().getTime(), 1000);

    }

    @Test
    public void shouldProperlyPersonReturnBook() {

        //given
        Person person = new Person();
        Book book = new Book("name", "author1", 1999, new Date());
        book.setOwner(person);
        List<Book> list = new ArrayList<>();
        list.add(book);
        person.setBooks(list);
        Mockito.when(booksRepository.findById(1)).thenReturn(Optional.of(book));

        booksService.personReturnBook(1);

        //then
        Assertions.assertNull(book.getOwner());
        Assertions.assertNull(book.getDateOfPerson());

    }

}
