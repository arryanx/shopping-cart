package com.aryanproject.dreamshop.service.user;

import com.aryanproject.dreamshop.dto.UserDto;
import com.aryanproject.dreamshop.model.User;
import com.aryanproject.dreamshop.request.CreateUserRequest;
import com.aryanproject.dreamshop.request.UserUpdateRequest;

public interface UserServiceInterface {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
}
