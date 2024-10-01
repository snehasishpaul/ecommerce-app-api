package com.snehasish.ecommerce.service.impl;

import com.snehasish.ecommerce.dto.LoginRequest;
import com.snehasish.ecommerce.dto.ResponseDto;
import com.snehasish.ecommerce.dto.UserDto;
import com.snehasish.ecommerce.entity.User;
import com.snehasish.ecommerce.enums.UserRole;
import com.snehasish.ecommerce.exception.InvalidCredentialsException;
import com.snehasish.ecommerce.exception.NotFoundException;
import com.snehasish.ecommerce.mapper.EntityDtoMapper;
import com.snehasish.ecommerce.repository.UserRepo;
import com.snehasish.ecommerce.security.JwtUtils;
import com.snehasish.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final EntityDtoMapper entityDtoMapper;


    @Override
    public ResponseDto registerUser(UserDto userDto) {
        UserRole userRole = UserRole.USER;

        if (userDto.getRole() != null && userDto.getRole().equalsIgnoreCase("admin")) {
            userRole = UserRole.ADMIN;
        }

        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .phoneNumber(userDto.getPhoneNumber())
                .role(userRole)
                .build();

        User savedUser = userRepo.save(user);

        UserDto savedUserDto = entityDtoMapper.mapUserToUserDto(savedUser);
        log.info("{} registered successfully", userDto.getEmail());
        return ResponseDto.builder()
                .status(200)
                .message("User registered successfully")
                .user(savedUserDto)
                .build();
    }

    @Override
    public ResponseDto loginUser(LoginRequest loginRequest) {
        User user = this.userRepo.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new NotFoundException("Email not found"));
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Password does not match");
        }

        log.info("Logged in user: {}", user.getEmail());

        String token = jwtUtils.generateToken(user);

        return ResponseDto.builder()
                .status(200)
                .message("User successfully logged in")
                .token(token)
                .expirationTime("10 minutes")
                .role(user.getRole().name())
                .build();
    }

    @Override
    public ResponseDto getAllUsers() {
        List<User> userList = this.userRepo.findAll();
        List<UserDto> userDtoList = userList.stream()
                .map(entityDtoMapper::mapUserToUserDto)
                .toList();

        return ResponseDto.builder()
                .status(200)
                .userList(userDtoList)
                .build();
    }

    @Override
    public User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        log.info("User Email is: {}", email);
        return userRepo.findByEmail(email).orElseThrow(() -> new NotFoundException("Email not found"));
    }

    @Override
    public ResponseDto getUserInfoAndOrderHistory() {
        User user = getLoginUser();
        UserDto userDto = entityDtoMapper.mapUserToDtoPlusAddressAndOrderHistory(user);

        return ResponseDto.builder()
                .status(200)
                .user(userDto)
                .build();
    }

}
