package com.skpGroup.SA.Catalog.Service;


import com.skpGroup.SA.Catalog.Model.AddMediaRequest;
import com.skpGroup.SA.Catalog.Model.CreateProductRequest;
import com.skpGroup.SA.Catalog.Model.Product;
import org.springframework.stereotype.Service;

import java.net.URI;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {

    private final Map<Long, Product> products =
            new ConcurrentHashMap<>();

    private final Map<String,Long> skuIndex =
            new ConcurrentHashMap<>();

    private final AtomicLong ids =
            new AtomicLong();

    public Product create(
            CreateProductRequest req
    ){

        if(skuIndex.containsKey(req.sku()))
            throw new RuntimeException(
                    "Duplicate SKU"
            );

        validateUrls(
                req.image_urls()
        );

        validateUrls(
                req.video_urls()
        );

        Product p =
                new Product(
                        ids.incrementAndGet(),
                        req.name(),
                        req.sku()
                );

        if(req.image_urls()!=null)
            p.getImageUrls()
                    .addAll(
                            req.image_urls()
                    );

        if(req.video_urls()!=null)
            p.getVideoUrls()
                    .addAll(
                            req.video_urls()
                    );

        products.put(
                p.getId(),
                p
        );

        skuIndex.put(
                p.getSku(),
                p.getId()
        );

        return p;
    }

    public List<Map<String,Object>> list(
            int limit,
            int offset
    ){

        return products
                .values()
                .stream()
                .skip(offset)
                .limit(limit)
                .map(p->{
                    Map<String,Object> m = new HashMap<>();
                    m.put("id", p.getId());
                    m.put("name", p.getName());
                    m.put("sku", p.getSku());
                    m.put("image_count", p.getImageUrls().size());
                    m.put("video_count", p.getVideoUrls().size());
                    return m;
                })
                .toList();
    }

    public Product detail(
            Long id
    ){

        Product p =
                products.get(id);

        if(p==null)
            throw new RuntimeException(
                    "Not found"
            );

        return p;
    }

    public Product addMedia(
            Long id,
            AddMediaRequest req
    ){

        Product p =
                detail(id);

        validateUrls(
                req.image_urls()
        );

        validateUrls(
                req.video_urls()
        );

        if(req.image_urls()!=null)
            p.getImageUrls()
                    .addAll(
                            req.image_urls()
                    );

        if(req.video_urls()!=null)
            p.getVideoUrls()
                    .addAll(
                            req.video_urls()
                    );

        return p;
    }

    private void validateUrls(
            List<String> urls
    ){

        if(urls==null)
            return;

        if(urls.size()>20)
            throw new RuntimeException(
                    "Max 20 URLs"
            );

        for(String u:urls){

            URI uri =
                    URI.create(u);

            if(
                    !(
                            uri.getScheme()
                                    .equals("http")
                                    ||
                                    uri.getScheme()
                                            .equals("https")
                    )
            ){
                throw new RuntimeException(
                        "Invalid URL"
                );
            }

        }

    }

}
