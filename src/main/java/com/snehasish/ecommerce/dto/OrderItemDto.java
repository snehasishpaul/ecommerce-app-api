package com.snehasish.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.snehasish.ecommerce.entity.Order;
import com.snehasish.ecommerce.entity.Product;
import com.snehasish.ecommerce.entity.User;
import com.snehasish.ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    private Long id;
    private int quantity;
    private BigDecimal price;
    private String status;
    private UserDto user;
    private ProductDto product;
//    private Order order;
    private LocalDateTime createdAt;
}
