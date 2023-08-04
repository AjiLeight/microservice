package com.leight.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.leight.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{

}
