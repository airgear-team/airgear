package com.airgear.mapper;


import com.airgear.dto.DepositDto;
import com.airgear.model.goods.Deposit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepositMapper {

    DepositDto toDto(Deposit price);

    Deposit toModel(DepositDto dto);

}
