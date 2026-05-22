package com.skpGroup.SA.rateLimiter.Model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record requestDTO(

            @NotBlank
            String user_id,

            @NotNull
            Object payload
) {}
