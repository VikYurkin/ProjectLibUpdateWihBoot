package ru.VYurkin.UpdateSpringBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.VYurkin.UpdateSpringBoot.models.Book;

import java.util.List;


@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
List<Book> findByNameAuthorAndNameBook(String nameAuthor, String nameBook);
List<Book> findByNameBookStartingWith(String startingWith);
}
