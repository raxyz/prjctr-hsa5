package com.prjctr.hsa.greeting.service;

import java.util.List;

import com.prjctr.hsa.greeting.dto.FullUser;
import com.prjctr.hsa.greeting.dto.UserDto;

public interface UserService {
    public FullUser createUser(UserDto userDto);
    public List<FullUser> getAllUsers();
}
