package ru.VYurkin.UpdateSpringBoot.contollers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.VYurkin.UpdateSpringBoot.models.Book;
import ru.VYurkin.UpdateSpringBoot.models.Person;
import ru.VYurkin.UpdateSpringBoot.services.BooksService;
import ru.VYurkin.UpdateSpringBoot.services.PeopleService;
import ru.VYurkin.UpdateSpringBoot.util.BookValidator;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final PeopleService peopleService;
    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BooksService booksService,
                           PeopleService peopleService,
                           BookValidator bookValidator) {
        this.booksService = booksService;
        this.peopleService = peopleService;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String indexBooks(@RequestParam(required = false) Integer page,
                             @RequestParam(required = false) Integer books_per_page,
                             @RequestParam(required = false) Boolean sort_by_year,
                             Model model){
        model.addAttribute("books", booksService.indexBook(page, books_per_page, sort_by_year));
        return "books/indexBooks";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam(required = false) String startingWith,
                             Model model){
        model.addAttribute("book", booksService.searchBook(startingWith));
        model.addAttribute("personWithBook", booksService.searchBookOwner(startingWith));
        model.addAttribute("startingWith", startingWith);
        return "books/searchBook";
    }

    @GetMapping("/{bookId}")
    public String showBook(@PathVariable("bookId") int bookId,
                           Model model,
                           @ModelAttribute("person") Person person){
        model.addAttribute("book", booksService.showBook(bookId));
        model.addAttribute("personWithBook", booksService.owner(bookId));
        model.addAttribute("people", peopleService.potentialOwner(booksService.owner(bookId)));
        return "books/showBook";
    }

    @PatchMapping("/{bookId}/add")
    public String personGetBook(@PathVariable("bookId") int bookId,
                                @ModelAttribute("person") Person person){
        booksService.personGetBook(bookId, person);
        return "redirect:/books/{bookId}";
    }

    @PatchMapping ("/{bookId}/take_of")
    public String personReturnBook(@PathVariable("bookId") int bookId){
        booksService.personReturnBook(bookId);
        return "redirect:/books/{bookId}";
    }

    @GetMapping("/new")
    public String newBooks(@ModelAttribute("book") Book book){
        return "books/newBook";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult){
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors())
            return "books/newBook";

        booksService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{bookId}/edit")
    public String editBook(Model model,
                           @PathVariable("bookId") int bookId){
        model.addAttribute("book",booksService.showBook(bookId));
        return "books/editBook";
    }

    @PatchMapping("/{bookId}")
    public String updateBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult,
                             @PathVariable("bookId") int bookId){
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors())
            return "books/editBook";

        booksService.updateBook(bookId, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable("bookId") int bookId){
        booksService.deleteBook(bookId);
        return "redirect:/books";
    }

}
