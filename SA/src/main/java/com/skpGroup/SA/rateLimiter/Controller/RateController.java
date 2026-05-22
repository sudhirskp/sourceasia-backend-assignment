package com.skpGroup.SA.rateLimiter.Controller;

import com.skpGroup.SA.rateLimiter.Model.requestDTO;
import com.skpGroup.SA.rateLimiter.Service.RateLimiterService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RateController {

    private final RateLimiterService service;

    public RateController(
            RateLimiterService service
    ) {
        this.service = service;
    }

    @PostMapping("/request")
    public ResponseEntity<?> request(
            @RequestBody
            @Valid
            requestDTO dto
    ) {

        if (!service.accept(dto.user_id())) {

            return ResponseEntity
                    .status(429)
                    .body(
                            Map.of(
                                    "error",
                                    "Rate limit exceeded"
                            )
                    );
        }

        return ResponseEntity
                .status(201)
                .body(
                        Map.of(
                                "message",
                                "accepted"
                        )
                );
    }

    @GetMapping("/stats")
    public Object stats() {
        return service.stats();
    }

}
