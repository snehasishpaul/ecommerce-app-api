package com.snehasish.ecommerce.service.impl;

import com.snehasish.ecommerce.dto.AddressDto;
import com.snehasish.ecommerce.dto.ResponseDto;
import com.snehasish.ecommerce.entity.Address;
import com.snehasish.ecommerce.entity.User;
import com.snehasish.ecommerce.repository.AddressRepo;
import com.snehasish.ecommerce.service.AddressService;
import com.snehasish.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepo addressRepo;
    private final UserService userService;


    @Override
    public ResponseDto saveAndUpdateAddress(AddressDto addressDto) {
        User user = userService.getLoginUser();
        Address address = user.getAddress();

        if (address == null) {
            address = new Address();
            address.setUser(user);
        }

        if (addressDto.getStreet() != null) address.setStreet(addressDto.getStreet());
        if (addressDto.getCity() != null) address.setCity(addressDto.getCity());
        if (addressDto.getState() != null) address.setState(addressDto.getState());
        if (addressDto.getZipCode() != null) address.setZipCode(addressDto.getZipCode());
        if (addressDto.getCountry() != null) address.setCountry(addressDto.getCountry());

        addressRepo.save(address);

        String message = (user.getAddress() == null) ? "Address successfully created" : "Address successfully updated";

        return ResponseDto.builder()
                .status(200)
                .message(message)
                .build();
    }
}
