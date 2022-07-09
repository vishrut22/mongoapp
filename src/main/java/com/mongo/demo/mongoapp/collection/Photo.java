package com.mongo.demo.mongoapp.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "photo")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Photo {
    @Id
    private String id;
    private String title;
    private Binary photo;
}
