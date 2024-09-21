package com.snehasish.ecommerce.mapper;

import com.snehasish.ecommerce.dto.*;
import com.snehasish.ecommerce.entity.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EntityDtoMapper {

    //user entity to userDto
    public UserDto mapUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setRole(user.getRole().name());
        userDto.setPassword(user.getPassword());
        userDto.setCreatedAt(user.getCreatedAt());
        return userDto;
    }

    //address entity to addressDto
    public AddressDto mapAddressToAddressDto(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setCity(address.getCity());
        addressDto.setStreet(address.getStreet());
        addressDto.setState(address.getState());
        addressDto.setCountry(address.getCountry());
        addressDto.setZipCode(address.getZipCode());
        addressDto.setCreatedAt(address.getCreatedAt());
        return addressDto;
    }

    //category entity to categoryDto
    public CategoryDto mapCategoryToCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setCreatedAt(category.getCreatedAt());
        return categoryDto;
    }

    //orderItem entity to orderItemDto
    public OrderItemDto mapOrderItemToOrderItemDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice());
        orderItemDto.setStatus(orderItem.getStatus().name());
        orderItemDto.setCreatedAt(orderItem.getCreatedAt());
        return orderItemDto;
    }

    //product entity to productDto
    public ProductDto mapProductToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setCreateDate(product.getCreateDate());
        return productDto;
    }

    public UserDto mapUserToDtoPlusAddress(User user) {
        UserDto userDto = mapUserToUserDto(user);
        if (user.getAddress() != null) {
            AddressDto addressDto = mapAddressToAddressDto(user.getAddress());
            userDto.setAddress(addressDto);
        }
        return userDto;
    }

    //orderItem to DTO plus product
    public OrderItemDto mapOrderItemToDtoPlusProduct(OrderItem orderItem) {
        OrderItemDto orderItemDto = mapOrderItemToOrderItemDto(orderItem);
        if (orderItem.getProduct() != null) {
            ProductDto productDto = mapProductToProductDto(orderItem.getProduct());
            orderItemDto.setProduct(productDto);
        }
        return orderItemDto;
    }

    //orderItem to Dto plus product and user
    public OrderItemDto mapOrderItemToDtoPlusProductAndUser(OrderItem orderItem) {
        OrderItemDto orderItemDto = mapOrderItemToDtoPlusProduct(orderItem);
        if (orderItem.getUser() != null) {
            UserDto userDto = mapUserToDtoPlusAddress(orderItem.getUser());
            orderItemDto.setUser(userDto);
        }
        return orderItemDto;
    }

    //user to dto with address and order items history
    public UserDto mapUserToDtoPlusAddressAndOrderHistory(User user) {
        UserDto userDto = mapUserToDtoPlusAddress(user);
        if (user.getOrderItemList() != null && !user.getOrderItemList().isEmpty()) {
            userDto.setOrderItemList(user.getOrderItemList()
                    .stream()
                    .map(this::mapOrderItemToDtoPlusProduct)
                    .collect(Collectors.toList()));
        }
        return userDto;
    }
}
