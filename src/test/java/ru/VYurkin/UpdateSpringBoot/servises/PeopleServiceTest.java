package ru.VYurkin.UpdateSpringBoot.servises;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.VYurkin.UpdateSpringBoot.models.Book;
import ru.VYurkin.UpdateSpringBoot.models.Person;
import ru.VYurkin.UpdateSpringBoot.repositories.PeopleRepository;
import ru.VYurkin.UpdateSpringBoot.services.PeopleService;


import java.util.*;

@DisplayName("Unit-level testing for PeopleService")
public class PeopleServiceTest {

    private PeopleService peopleService;
    private PeopleRepository peopleRepository;

    @BeforeEach
    public void init() {
        peopleRepository = Mockito.mock(PeopleRepository.class);
        peopleService = new PeopleService(peopleRepository);
    }

    @Test
    public void shouldProperlyIndexPerson() {

        //given
        List<Person> list = new ArrayList<>();
        list.add(new Person());
        list.add(new Person());
        list.add(new Person());
        list.add(new Person());


        Mockito.when(peopleRepository.findAll()).thenReturn(list);

        List<Person> people =peopleService.indexPerson();

        //then
        Assertions.assertFalse(people.isEmpty());
        Assertions.assertEquals(4, people.size());
    }

    @Test
    public void shouldProperlyShowPerson() {

        //given
        Person person = new Person("name", 1999);
        person.setPersonId(1);
        Mockito.when(peopleRepository.findById(1)).thenReturn(Optional.of(person));
        Mockito.when(peopleRepository.findByName("name")).thenReturn(Collections.singletonList(person));

        Person findingPersonById =peopleService.showPerson(1);
        Optional<Person> findingPersonByName =peopleService.showPerson("name");

        //then
        Assertions.assertNotNull(findingPersonById);
        Assertions.assertEquals(person, findingPersonById );
        Assertions.assertTrue(findingPersonByName.isPresent());
        Assertions.assertEquals(person, findingPersonByName.get() );
    }

    @Test
    public void shouldProperlySavePerson() {

        //given
        Person person = new Person("name", 1999);

        Mockito.when(peopleRepository.save(person)).thenReturn(person);

        peopleService.savePerson(person);

        //then
        Mockito.verify(peopleRepository).save(person);
    }

    @Test
    public void shouldProperlyUpdatePerson() {

        //given
        Person person = new Person("name", 1999);

        Mockito.when(peopleRepository.save(person)).thenReturn(person);

        peopleService.updatePerson(1, person);

        //then
        Mockito.verify(peopleRepository).save(person);
        Assertions.assertEquals(1, person.getPersonId());
    }

    @Test
    public void shouldProperlyListBookFromPerson() {

        //given

        List<Book> list = new ArrayList<>();
        list.add(new Book("name", "author", 1999, new Date()));
        list.add(new Book("name", "author", 1999, new Date()));
        list.add(new Book("name", "author", 1999, new Date()));
        list.add(new Book("name", "author", 1999, new Date(123, 2, 11)));

        Person person = new Person("name", 1999);
        person.setBooks(list);
        person.setPersonId(1);

        Mockito.when(peopleRepository.findById(1)).thenReturn(Optional.of(person));

        List<Book> books =peopleService.listBookFromPerson(1);

        //then
        Assertions.assertFalse(books.isEmpty());
        Assertions.assertEquals(4, books.size());
        Assertions.assertTrue(books.get(3).isMarkRedBook());
    }

}
