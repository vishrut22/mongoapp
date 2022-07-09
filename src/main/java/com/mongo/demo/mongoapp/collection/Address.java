package com.mongo.demo.mongoapp.collection;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Address {
    private String address1;
    private String address2;
    private String city;
}
