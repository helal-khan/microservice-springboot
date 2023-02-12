package com.microservice.inventoryservice.controller;


import com.microservice.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/skuCode")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("skuCode") String skuCode){
        return inventoryService.isInStock(skuCode);
    }
}
