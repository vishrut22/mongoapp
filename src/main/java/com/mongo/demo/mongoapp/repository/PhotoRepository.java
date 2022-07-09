package com.mongo.demo.mongoapp.repository;

import com.mongo.demo.mongoapp.collection.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoRepository extends MongoRepository<Photo,String> {
}
