package demo.controller;

import demo.entity.Address;
import demo.entity.Person;
import demo.repository.PersonRepository;
import demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class DemoController {
    @Autowired
    PersonService service;

    @Autowired
    PersonRepository repository;

    private final AtomicLong counter = new AtomicLong();

    @GetMapping(value = "/start", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getStart() {
        Address add = new Address("newark", "ca", "91234");
        Person p1 = new Person("anna", 19, add);
        Address add2 = new Address("sfo", "ca", "91222");
        Person p2 = new Person("mina", 5, add2);
        Address add3 = new Address("sj", "ca", "91111");
        Person p3 = new Person("jerry", 12, add3);
        List<Person> allPerson = new ArrayList<>();
        allPerson.add(p1);
        allPerson.add(p2);
        allPerson.add(p3);

        repository.saveAll(allPerson);
        return "Success";
        // return new ResponseEntity<>(allPerson, HttpStatus.OK);
    }


    @GetMapping(value = "/persons", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Person>> getPersons() {
        List<Person> findAll = service.findAll();
        return new ResponseEntity<>(findAll, HttpStatus.OK);
    }

    @GetMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Person> getPersonByID(@RequestParam(value = "id", defaultValue = "1") Integer id) throws EntityNotFoundException {
        Person person = new Person();
        try {
            person = service.findById(id);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(person,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PostMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Person> postNewPerson(@RequestBody Person newPerson) {
        repository.save(newPerson);
        return new ResponseEntity<>(newPerson, HttpStatus.OK);
    }

    @DeleteMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Person> deletePersonById(@RequestParam(value = "id") Integer id) {
        Person p = service.findById(id);
        repository.deleteById(id);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PutMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Person> putAgeById(@RequestParam(value = "id") Integer id,
                                             @RequestParam(value = "age") Integer age) {
        //TODO: add exception check
        Person p = service.findById(id);
        p.setAge(age);
        repository.save(p);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }
}
