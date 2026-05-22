package com.skpGroup.SA.Catalog.Controller;

import com.skpGroup.SA.Catalog.Model.AddMediaRequest;
import com.skpGroup.SA.Catalog.Model.CreateProductRequest;
import com.skpGroup.SA.Catalog.Service.ProductService;

import jakarta.validation.Valid;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    private final ProductService service;

    public ProductController(
            ProductService service
    ){
        this.service=service;
    }

    @PostMapping("/products")
    public ResponseEntity<?> create(
            @RequestBody
            @Valid
            CreateProductRequest req
    ){

        return ResponseEntity
                .status(201)
                .body(
                        service.create(req)
                );

    }

    @GetMapping("/products")
    public Object list(

            @RequestParam(
                    defaultValue="20"
            )
            int limit,

            @RequestParam(
                    defaultValue="0"
            )
            int offset

    ){

        return service.list(
                limit,
                offset
        );

    }

    @GetMapping("/products/{id}")
    public Object detail(
            @PathVariable
            Long id
    ){

        return service.detail(
                id
        );

    }

    @PostMapping(
            "/products/{id}/media"
    )
    public Object media(

            @PathVariable
            Long id,

            @RequestBody
            AddMediaRequest req

    ){

        return service.addMedia(
                id,
                req
        );

    }

}
