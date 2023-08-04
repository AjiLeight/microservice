package com.leight.inventoryservice.controller;

import com.leight.inventoryservice.dto.InventoryResponse;
import com.leight.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<InventoryResponse> inStock(@RequestParam List<String> skuCode ){
        return inventoryService.isInStock(skuCode);
    }
}
