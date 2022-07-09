package com.mongo.demo.mongoapp.controller;

import com.mongo.demo.mongoapp.collection.Person;
import com.mongo.demo.mongoapp.service.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonServiceImpl personService;
    @PostMapping
    public String save(@RequestBody  Person p) {
        return personService.save(p);
    }

    @GetMapping
    public List<Person> getPersonsStartsWith(@RequestParam("name") String name ) {
        return personService.getPersonsStartsWith(name);
    }

    @GetMapping("/search")
    public Page<Person> save(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    ) {
        Pageable pageable = PageRequest.of(page,size);
        return personService.search(name ,minAge,maxAge,city,pageable);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id ) {
        personService.delete(id);
    }

    @GetMapping("/age")
    public List<Person> getByPersonAge(
            @RequestParam Integer minAge,
            @RequestParam Integer maxAge
    ) {
        return personService.getAllPersonAgeBetween(minAge , maxAge);
    }
}
