package ru.VYurkin.UpdateSpringBoot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.VYurkin.UpdateSpringBoot.models.Book;
import ru.VYurkin.UpdateSpringBoot.services.BooksService;

@Component
public class BookValidator implements Validator {

    private final BooksService booksService;

    @Autowired
    public BookValidator(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
            Book book = (Book) o;
            if(!(booksService.showBooks(book.getNameAuthor(), book.getNameBook()).isEmpty())){
                errors.rejectValue("nameBook", "", "Эта книга уже зарегистрирована");
            }
        }
    }

