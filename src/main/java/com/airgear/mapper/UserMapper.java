package com.airgear.mapper;

import com.airgear.dto.UserDto;
import com.airgear.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {GoodsSetMapper.class, RoleSetMapper.class, UserReviewSetMapper.class, AccountStatusMapper.class})
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "goods", ignore = true)
    UserDto toDto(User user);

    @Mapping(target = "goods", ignore = true)
    User toUser(UserDto dto);
}
