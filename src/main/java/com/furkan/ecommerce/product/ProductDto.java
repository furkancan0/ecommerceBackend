package com.furkan.ecommerce.product;

import com.furkan.ecommerce.category.Category;
import com.furkan.ecommerce.image.ImageDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal unitPrice;
    private int inventory;
    private String description;
}