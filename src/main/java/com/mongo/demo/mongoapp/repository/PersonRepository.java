package com.mongo.demo.mongoapp.repository;

import com.mongo.demo.mongoapp.collection.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PersonRepository extends MongoRepository<Person, String> {
    List<Person> findByFirstNameStartingWith(String startChar);

    //findByAgeBetween equivalent
    @Query( value = "{ 'age' : { $gt: ?0, $lt: ?1 } }" , fields = "{addresses : 0}")
    List<Person> findByPersonAgeBetween(int ageGT, int ageLT);
}
