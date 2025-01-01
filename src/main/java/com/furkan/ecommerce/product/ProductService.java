package com.furkan.ecommerce.product;

import com.furkan.ecommerce.category.CategoryRepository;
import com.furkan.ecommerce.exception.ResourceNotFoundException;
import com.furkan.ecommerce.image.Image;
import com.furkan.ecommerce.category.Category;
import com.furkan.ecommerce.image.ImageDto;
import com.furkan.ecommerce.image.ImageRepository;
import com.furkan.ecommerce.mappers.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CartMapper cartMapper;
    private final ImageRepository imageRepository;


    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
        return cartMapper.productToProductDto(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }



}