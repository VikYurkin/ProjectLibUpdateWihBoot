package ru.VYurkin.UpdateSpringBoot.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import ru.VYurkin.UpdateSpringBoot.models.Book;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts={"/sql/clearDbs.sql", "/sql/books.sql"})
public class BooksRepositoryIT {
    @Autowired
    private BooksRepository booksRepository;

    @Test
    public void shouldProperlyFindBookForNameAndNameAuthor(){
        List<Book> books = booksRepository.findByNameAuthorAndNameBook("Джером Сэлинджер", "Над пропастью во ржи");

        Assertions.assertFalse(books.isEmpty());
        Assertions.assertEquals(1, books.size());
        Assertions.assertEquals(1, books.get(0).getBookId());
    }

    @Test
    public void shouldProperlyFindBookForNameBookStartingWith(){
        List<Book> books = booksRepository.findByNameBookStartingWith("Над");

        Assertions.assertFalse(books.isEmpty());
        Assertions.assertEquals(1, books.size());
        Assertions.assertEquals(1, books.get(0).getBookId());
    }

    @Test
    public void shouldProperlyFindBookAndSort(){
        List<Book> books = booksRepository.findAll(Sort.by("yearWritten"));

        Assertions.assertFalse(books.isEmpty());
        Assertions.assertEquals(7, books.size());
        Assertions.assertEquals(2, books.get(0).getBookId());
    }

    @Test
    public void shouldProperlyFindBooksAndPagination(){
        List<Book> books = booksRepository.findAll(PageRequest.of(2, 3)).getContent();

        Assertions.assertFalse(books.isEmpty());
        Assertions.assertEquals(1, books.size());
    }

    @Test
    public void shouldProperlyFindAllBooks(){
        List<Book> books = booksRepository.findAll();

        Assertions.assertFalse(books.isEmpty());
        Assertions.assertEquals(7, books.size());
    }

    @Test
    public void shouldProperlySaveBook(){
        Book book = new Book();
        book.setNameBook("name");
        book.setNameAuthor("author");
        book.setYearWritten(2022);

        booksRepository.save(book);

        List <Book> saved = booksRepository.findByNameBookStartingWith("name");
        Assertions.assertFalse(saved.isEmpty());
        Assertions.assertEquals(book, saved.get(0));
    }

    @Test
    public void shouldProperlyDeleteBook(){

        Optional<Book> bookBeforeDelete =  booksRepository.findById(5);

        booksRepository.deleteById(5);

        Optional <Book> book = booksRepository.findById(5);

        Assertions.assertTrue(bookBeforeDelete.isPresent());
        Assertions.assertFalse(book.isPresent());
    }

}
