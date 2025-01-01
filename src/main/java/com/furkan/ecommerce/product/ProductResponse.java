package com.furkan.ecommerce.product;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String description,
        double quantity,
        BigDecimal price,
        Long categoryId,
        String categoryName
) { }