package demo.service;
import java.util.*;

import javax.persistence.EntityNotFoundException;

import demo.entity.Person;
import demo.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PersonService {

    private PersonRepository personPersistence;

    public PersonService(PersonRepository personPersistence) {
        this.personPersistence = personPersistence;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Person create(Person person) {
        return personPersistence.save(person);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Person findById(Integer id) {
        return personPersistence.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Person not found with id:" + id));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Person> findAll() {
        List<Person> people = new ArrayList<>();
        Iterator<Person> iterator = personPersistence.findAll().iterator();
        iterator.forEachRemaining(people::add);
        return people;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Person person) {
        personPersistence.delete(person);
    }

}