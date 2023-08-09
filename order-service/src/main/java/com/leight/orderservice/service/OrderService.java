package com.leight.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.leight.orderservice.dto.InventoryResponse;
import org.springframework.stereotype.Service;

import com.leight.orderservice.dto.OrderLineItemsDto;
import com.leight.orderservice.dto.OrderRequest;
import com.leight.orderservice.model.Order;
import com.leight.orderservice.model.OrderLineItems;
import com.leight.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
	
	private final OrderRepository orderRepository;
	private final WebClient.Builder webClientBuilder;
	

	public void placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		
		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDto()
				.stream()
				.map(this::mapToDto)
				.toList();
		order.setOrderLineItems(orderLineItems);

		List<String> skuCodes =  orderLineItems.stream().map(OrderLineItems::getSkuCode).toList();

		InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
				.uri("http://inventory-service/api/inventory",
						uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
				.retrieve()
				.bodyToMono(InventoryResponse[].class)
				.block();

        assert inventoryResponses != null;
        boolean result = Arrays.stream(inventoryResponses)
				.allMatch(InventoryResponse::getInStock);

		if(result) {
			orderRepository.save(order);
		}
		else {
			throw new IllegalArgumentException("product not found, try again later");
		}
	}

	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
		orderLineItems.setPrice(orderLineItemsDto.getPrice());
		orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
		
		return orderLineItems;
	}
}
