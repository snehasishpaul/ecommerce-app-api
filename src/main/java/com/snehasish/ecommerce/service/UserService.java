package com.snehasish.ecommerce.service;

import com.snehasish.ecommerce.dto.LoginRequest;
import com.snehasish.ecommerce.dto.ResponseDto;
import com.snehasish.ecommerce.dto.UserDto;
import com.snehasish.ecommerce.entity.User;

public interface UserService {

    ResponseDto registerUser(UserDto userDto);

    ResponseDto loginUser(LoginRequest loginRequest);

    ResponseDto getAllUsers();

    User getLoginUser();

    ResponseDto getUserInfoAndOrderHistory();

}
