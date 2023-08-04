package com.leight.productservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.leight.productservice.dto.ProductRequest;
import com.leight.productservice.dto.ProductResponse;
import com.leight.productservice.model.Product;
import com.leight.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
	
	private final ProductRepository productRepository;
	
	public void createProduct(ProductRequest productRequest) {
		Product product = Product.builder()
				.name(productRequest.getName())
				.description(productRequest.getDescription())
				.price(productRequest.getPrice())
				.build();
		productRepository.save(product);
		log.info("Product {} is created", product.getId());
	}

	public List<ProductResponse> getAllProducts() {
		List<Product> products = productRepository.findAll();
		return products.stream().map(this::mapToProductResponse).toList();
	}
	
	private ProductResponse mapToProductResponse(Product product) {
		return ProductResponse.builder()
				.name(product.getName())
				.description(product.getDescription())
				.id(product.getId())
				.price(product.getPrice())
				.build();
	}
}
