package com.airgear.mapper;

import com.airgear.dto.UserDto;
import com.airgear.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {GoodsMapper.class, RoleMapper.class, UserReviewMapper.class, AccountStatusMapper.class})
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "goods", ignore = true)
    UserDto toDto(User user);

    @Mapping(target = "goods", ignore = true)
    User toModel(UserDto dto);

    List<UserDto> toDtoList(List<User> users);

    List<User> toModelList(List<UserDto> dtos);
}
