package com.mongo.demo.mongoapp.service;

import com.mongo.demo.mongoapp.collection.Address;
import com.mongo.demo.mongoapp.collection.Person;
import com.mongo.demo.mongoapp.repository.PersonRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class PersonServiceImpl {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public String save(Person p){
        Person saved = personRepository.save(p);
        List<Person> lsPerson = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Person p1 = Person.builder().hobbies(Arrays.asList("Cricket")).firstName("Test "+i).lastName(" Last "+i)
                    .age(10+i).addresses(Arrays.asList(Address.builder()
                            .address1("abc")
                            .address2("abc")
                            .city("Pune").build()))
                    .build();
            lsPerson.add(p1);
        }
        for (int i = 0; i < 5; i++) {
            Person p1 = Person.builder().hobbies(Arrays.asList("Football")).firstName("Test new "+i).lastName(" Last "+i)
                    .age(10+i).addresses(Arrays.asList(Address.builder()
                            .address1("abc")
                            .address2("abc")
                            .city("Bengaluru").build()))
                    .build();
            lsPerson.add(p1);
        }
        for (int i = 0; i < 20; i++) {
            Person p1 = Person.builder().hobbies(Arrays.asList("Basketball")).firstName("Test old "+i).lastName(" Last "+i)
                    .age(10+i).addresses(Arrays.asList(Address.builder()
                            .address1("abc")
                            .address2("abc")
                            .city("Ahmedabad").build()))
                    .build();
            lsPerson.add(p1);
        }
        personRepository.saveAll(lsPerson);
        return saved.getPersonId();
    }

    public void delete(String id){
        personRepository.deleteById(id);
    }

    public List<Person>  getPersonsStartsWith(String startChar) {
        getOldestPersonByCityAsc();
        return personRepository.findByFirstNameStartingWith(startChar);
    }

    public List<Person>  getAllPersonAgeBetween(int ageGt, int agetLt) {
        return personRepository.findByPersonAgeBetween(ageGt , agetLt);
    }

    public Page<Person> search(String name, Integer minAge, Integer maxAge, String city, Pageable pageable) {
        Query query  = new Query().with(pageable);
        List<Criteria> criteria = new ArrayList<>();
        if (name != null && !name.isBlank())
            criteria.add(Criteria.where("firstName").regex(name, "i"));
        if (minAge != null && maxAge != null)
            criteria.add(Criteria.where("age").gte(minAge).lte(maxAge));
        if (city != null && !city.isBlank())
            criteria.add(Criteria.where("addresses.city").is(city));

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }
        Page<Person> pages = PageableExecutionUtils.getPage(mongoTemplate.find(query, Person.class)
        ,pageable , () -> mongoTemplate.count(query.skip(0).limit(0), Person.class));

        return pages;
    }
    public  void getOldestPersonByCityAsc() {
        UnwindOperation unwindOperation = unwind("addresses");
        GroupOperation groupOperation = Aggregation.group("addresses.city").count().as("popCount");
        SortOperation sortOperation = sort(Sort.Direction.DESC,"popCount");
        Aggregation aggregation = newAggregation(unwindOperation , groupOperation,sortOperation);
        List<Document> document = mongoTemplate.aggregate(aggregation,Person.class, org.bson.Document.class).getMappedResults();
        for (Document single: document) {
            System.out.println("City :"+single.getString("_id") +" , Count :"+single.getInteger("popCount"));
        }
        UnwindOperation unwindResponse =Aggregation.unwind("addresses");
        sortOperation = sort(Sort.Direction.DESC,"age");
        groupOperation = Aggregation.group("addresses.city").first(Aggregation.ROOT).as("oldMan");;

       // ProjectionOperation projectionOperation = project().;
         aggregation = newAggregation(unwindResponse,sortOperation,groupOperation);
        List<Document> person = mongoTemplate.aggregate(aggregation,Person.class, Document.class).getMappedResults();
        System.out.println("::"+person);
    }
}
