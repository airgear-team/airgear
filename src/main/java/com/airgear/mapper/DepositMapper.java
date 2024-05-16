package com.airgear.mapper;


import com.airgear.dto.DepositRequest;
import com.airgear.dto.DepositResponse;
import com.airgear.model.Deposit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepositMapper {

    DepositResponse toDto(Deposit price);

    Deposit toModel(DepositRequest dto);

}
