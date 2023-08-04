package com.leight.orderservice.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
	@JsonProperty("orderLineItems")
	private List<OrderLineItemsDto> orderLineItemsDto;
}
