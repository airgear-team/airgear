package com.airgear.mapper;

import com.airgear.dto.UserDto;
import com.airgear.model.User;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface UserListMapper {
    List<UserDto> toDtoList(List<User> users);

    List<User> toUserList(List<UserDto> dtos);
}
