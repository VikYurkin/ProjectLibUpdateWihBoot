package ru.VYurkin.UpdateSpringBoot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name="book")
public class Book {

    @Id
    @Column(name="book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min = 2, max = 100, message = "Название книги должно быть не короче 2 символов, но и не длиннее 100 символов")
    @Column(name="name_book")
    private String nameBook;

    @NotEmpty(message = "Поле автор не должно быть пустым")
    @Size(min = 2, max = 100, message = "Поле автор должно быть заполнено текстом не короче 2 символов, но и не длиннее 100 символов")
    @Column(name="name_author")
    private String nameAuthor;

    @Min(value = 1600, message = "Год издания должен быть больше 1600")
    @Column(name="year_written")
    private int yearWritten;

    @Column(name="date_of_person")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfPerson;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;


    @Transient
    private boolean markRedBook;

    public Book() {
    }

    public Book(String nameBook, String nameAuthor, int yearWritten, Date dateOfPerson) {
        this.nameBook = nameBook;
        this.nameAuthor = nameAuthor;
        this.yearWritten = yearWritten;
        this.dateOfPerson = dateOfPerson;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }

    public int getYearWritten() {
        return yearWritten;
    }

    public void setYearWritten(int yearWritten) {
        this.yearWritten = yearWritten;
    }

    public Date getDateOfPerson() {
        return dateOfPerson;
    }

    public void setDateOfPerson(Date dateOfPerson) {
        this.dateOfPerson = dateOfPerson;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public boolean isMarkRedBook() {
        return markRedBook;
    }

    public void setMarkRedBook(boolean markRedBook) {
        this.markRedBook = markRedBook;
    }
}
