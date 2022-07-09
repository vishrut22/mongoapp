package com.mongo.demo.mongoapp.service;

import com.mongo.demo.mongoapp.collection.Photo;
import com.mongo.demo.mongoapp.repository.PhotoRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PhotoServiceImpl {
    @Autowired
    private PhotoRepository photoRepository;

    public String addPhoto(String title, MultipartFile image) throws IOException {
        Photo p = new Photo();
        p.setTitle(title);
        p.setPhoto(new Binary(BsonBinarySubType.BINARY,image.getBytes()));
        return photoRepository.save(p).getId();
    }

    public Photo getPhoto(String id) {
        return photoRepository.findById(id).get();
    }
}
