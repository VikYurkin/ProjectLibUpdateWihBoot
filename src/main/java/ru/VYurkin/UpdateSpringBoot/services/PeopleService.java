package ru.VYurkin.UpdateSpringBoot.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.VYurkin.UpdateSpringBoot.models.Book;
import ru.VYurkin.UpdateSpringBoot.models.Person;
import ru.VYurkin.UpdateSpringBoot.repositories.PeopleRepository;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;


    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> indexPerson() {
        return peopleRepository.findAll();
    }

    public Person showPerson(int personId) {
        return peopleRepository.findById(personId).orElse(null);
    }

    public Optional<Person> showPerson(String name) {
        return peopleRepository.findByName(name).stream().findAny();
    }

    @Transactional
    public void savePerson(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void updatePerson(int personId, Person updatedPerson) {
        updatedPerson.setPersonId(personId);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void deletePerson(int personId) {
        peopleRepository.deleteById(personId);
    }

    public List<Book> listBookFromPerson(int personalId) {
        Optional<Person> person = peopleRepository.findById(personalId);
        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            for (Book book : person.get().getBooks()) {
                book.setMarkRedBook(((new Date().getTime() - book.getDateOfPerson().getTime()) / 1000 / 60 / 60 / 24) > 10);
            }
            return person.get().getBooks();
        } else {
            return Collections.emptyList();
        }
    }

    public List<Person> potentialOwner(Person owner){
        if(owner!=null){return null;}
        else{return peopleRepository.findAll();
        }
    }

}
