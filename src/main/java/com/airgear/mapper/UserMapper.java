package com.airgear.mapper;

import com.airgear.dto.UserGetResponse;
import com.airgear.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {GoodsMapper.class, UserReviewMapper.class})
public interface UserMapper {

    @Mapping(target = "goods", ignore = true)
    UserGetResponse toDto(User user);

    @Mapping(target = "goods", ignore = true)
    User toModel(UserGetResponse dto);

    List<UserGetResponse> toDtoList(List<User> users);

    List<User> toModelList(List<UserGetResponse> dtos);
}
