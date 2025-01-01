package com.furkan.ecommerce.mappers;


import com.furkan.ecommerce.Item.Item;
import com.furkan.ecommerce.Item.ItemDto;
import com.furkan.ecommerce.cart.Cart;
import com.furkan.ecommerce.cart.CartDto;
import com.furkan.ecommerce.customer.Customer;
import com.furkan.ecommerce.customer.CustomerDto;
import com.furkan.ecommerce.product.Product;
import com.furkan.ecommerce.product.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "cartItems", source = "cartItems")
    @Mapping(target = "totalAmount", source = "totalAmount")
    CartDto cartToCartDto(Cart cart, BigDecimal totalAmount, List<ItemDto> cartItems);

    @Mapping(target = "unitPrice", source = "price")
    ProductDto productToProductDto(Product product);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "cartId", source = "cart.id")
    CustomerDto customerToCustomerDto(Customer customer);

    @Mapping(target = "quantity", source = "quantity")
    ItemDto itemToItemDto(Item item);

}