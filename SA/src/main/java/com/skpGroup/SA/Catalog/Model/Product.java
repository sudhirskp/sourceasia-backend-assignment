package com.skpGroup.SA.Catalog.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {

    private Long id;

    private String name;

    private String sku;

    private List<String> imageUrls =
            new ArrayList<>();

    private List<String> videoUrls =
            new ArrayList<>();

    private Instant createdAt =
            Instant.now();

    public Product(
            Long id,
            String name,
            String sku
    ) {
        this.id = id;
        this.name = name;
        this.sku = sku;
    }
}
