package com.mongo.demo.mongoapp.controller;


import com.mongo.demo.mongoapp.collection.Photo;
import com.mongo.demo.mongoapp.service.PhotoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.IOException;

@RestController
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    private PhotoServiceImpl photoService;

    @PostMapping("/add")
    public String addPhoto(@RequestParam("image") MultipartFile image)
            throws IOException {
        String id = photoService.addPhoto(image.getOriginalFilename(), image);
        return  id;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getPhoto(@PathParam("id") String id)
            throws IOException {
        Photo photo = photoService.getPhoto(id);

        Resource resource  = new ByteArrayResource(photo.getPhoto().getData());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + photo.getTitle() + "\"")
                .body(resource);
    }
}
