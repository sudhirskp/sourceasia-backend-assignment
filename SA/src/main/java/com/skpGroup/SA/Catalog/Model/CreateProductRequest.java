package com.skpGroup.SA.Catalog.Model;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateProductRequest(

        @NotBlank
        String name,

        @NotBlank
        String sku,

        List<String> image_urls,

        List<String> video_urls

) {}
