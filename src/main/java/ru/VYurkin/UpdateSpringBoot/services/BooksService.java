package ru.VYurkin.UpdateSpringBoot.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.VYurkin.UpdateSpringBoot.models.Book;
import ru.VYurkin.UpdateSpringBoot.models.Person;
import ru.VYurkin.UpdateSpringBoot.repositories.BooksRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> indexBook(Integer page, Integer bookPerPage, Boolean sort_by_year) {
        if(page!=null&bookPerPage!=null) {if (sort_by_year!=null) {if(sort_by_year){return booksRepository.findAll(PageRequest.of(page, bookPerPage, Sort.by("yearWritten"))).getContent();}
                                                                   else{return booksRepository.findAll(PageRequest.of(page, bookPerPage)).getContent();
                                                                   }
                                          } else {return booksRepository.findAll(PageRequest.of(page, bookPerPage)).getContent();
                                          }
        }else{if (sort_by_year!=null) {if(sort_by_year){return booksRepository.findAll(Sort.by("yearWritten"));
                                       }else{return booksRepository.findAll();
                                       }
              } else {return booksRepository.findAll();
              }
        }
    }

    public Book searchBook(String startingWith){
        if(startingWith==null){return null;}
        Optional<Book> book = booksRepository.findByNameBookStartingWith(startingWith).stream().findAny();
        return book.orElse(null);
    }

    public Person searchBookOwner(String startingWith){
        if(startingWith==null){return null;}
        Optional<Book> book = booksRepository.findByNameBookStartingWith(startingWith).stream().findAny();
        if(book.isEmpty()){return null;}
        return book.orElse(null).getOwner();
    }

    public Book showBook(int bookId) {
        return booksRepository.findById(bookId).orElse(null);
    }

    public List<Book> showBooks(String nameAuthor, String nameBook) {
        return booksRepository.findByNameAuthorAndNameBook(nameAuthor,nameBook);
    }

    @Transactional
    public void saveBook(Book book) {
        booksRepository.save(book);
        }

    @Transactional
    public void updateBook(int bookId, Book updatedBook) {
        updatedBook.setBookId(bookId);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void deleteBook(int bookId) {
        booksRepository.deleteById(bookId);
    }
    public Person owner(int bookId){
        Optional<Book> book = booksRepository.findById(bookId).stream().findAny();
        if(book.isPresent()){
            Hibernate.initialize(book.get().getOwner());
            return book.get().getOwner();
        } else {return null;}
    }


    @Transactional
    public void personGetBook (int bookId, Person owner){
        Book book = booksRepository.findById(bookId).orElse(null);
        if(book.getOwner()!=null){return;}
          book.setOwner(owner);
          book.setDateOfPerson(new Date());
    }

    @Transactional
    public void personReturnBook(int bookId){
        Book book = booksRepository.findById(bookId).orElse(null);
        if(book.getOwner()==null){return;}
        book.getOwner().getBooks().remove(book);
        book.setOwner(null);
        book.setDateOfPerson(null);
    }

}
