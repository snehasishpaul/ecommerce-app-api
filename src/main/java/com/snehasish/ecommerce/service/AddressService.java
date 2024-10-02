package com.snehasish.ecommerce.service;

import com.snehasish.ecommerce.dto.AddressDto;
import com.snehasish.ecommerce.dto.ResponseDto;

public interface AddressService {
    ResponseDto saveAndUpdateAddress(AddressDto addressDto);
}
