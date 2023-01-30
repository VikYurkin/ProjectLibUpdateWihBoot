package ru.VYurkin.UpdateSpringBoot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name="person")
public class Person {

    @Id
    @Column(name="person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personId;

    @NotEmpty(message = "ФИО не должны быть пустыми")
    @Size(min = 2, max = 100, message = "ФИО должны быть не короче 2 символов, и не длиннее 100 символов")
    @Pattern(regexp ="[А-Я][а-я]+ [А-Я][а-я]+ [А-Я][а-я]+", message = "ФИО необходимо записать в виде: Иванов Иван Иванович")
    @Column(name="name")
    private String name;

    @Min(value = 1900, message = "Год рождения должен быть больше 1900")
    @Column(name="year_birth")
    private int yearBirth;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person() {
    }

    public Person(String name, int yearBirth) {
        this.name = name;
        this.yearBirth = yearBirth;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearBirth() {
        return yearBirth;
    }

    public void setYearBirth(int yearBirth) {
        this.yearBirth = yearBirth;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}


