package ru.VYurkin.UpdateSpringBoot.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ru.VYurkin.UpdateSpringBoot.models.Person;

import java.util.List;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/clearDbs.sql", "/sql/people.sql"})
public class PeopleRepositoryIT {

    @Autowired
    private PeopleRepository peopleRepository;

    @Test
    public void shouldProperlyFindPeopleForName(){
        List<Person> people = peopleRepository.findByName("Иванов Иван Иванович");

        Assertions.assertFalse(people.isEmpty());
        Assertions.assertEquals(1, people.size());
        Assertions.assertEquals(1, people.get(0).getPersonId());
    }

    @Test
    public void shouldProperlyFindPeople(){
        List<Person> people = peopleRepository.findAll();

        Assertions.assertFalse(people.isEmpty());
        Assertions.assertEquals(5, people.size());
    }

    @Test
    public void shouldProperlySavePerson(){
        Person person = new Person();
        person.setName("Семенов Семен Семенович");
        person.setYearBirth(1999);

        peopleRepository.save(person);

        List <Person> saved = peopleRepository.findByName("Семенов Семен Семенович");
        Assertions.assertFalse(saved.isEmpty());
        Assertions.assertEquals(person, saved.get(0));
    }

    @Test
    public void shouldProperlyDeletePerson(){

        Optional<Person> personBeforeDelete =  peopleRepository.findById(5);

        peopleRepository.deleteById(5);

        Optional <Person> person = peopleRepository.findById(5);

        Assertions.assertTrue(personBeforeDelete.isPresent());
        Assertions.assertFalse(person.isPresent());
    }

}
