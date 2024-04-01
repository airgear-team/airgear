package com.airgear.mapper;

import com.airgear.dto.CheckoutDto;
import com.airgear.model.Checkout;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface CheckoutDtoMapper {

    @Mapping(target = "order_id", source = "id")
    @Mapping(target = "action", expression = "java(checkout.getAction().toString())")
    @Mapping(target = "currency", expression = "java(checkout.getCurrency().toString())")
    CheckoutDto toDto(Checkout checkout);

    @Mapping(target = "id", source = "order_id")
    @Mapping(target = "action", expression = "java(Checkout.Action.valueOf(checkoutDto.getAction()))")
    @Mapping(target = "currency", expression = "java(Checkout.Currency.valueOf(checkoutDto.getCurrency()))")
    Checkout toModel(CheckoutDto checkoutDto);

    default Map<String, String> toMap(CheckoutDto checkoutDto) throws IllegalAccessException {
        Map<String, String> map = new HashMap<>();
        Field[] fields = checkoutDto.getClass().getDeclaredFields();

        for (Field field: fields) {
            field.setAccessible(true);
            if (field.get(checkoutDto) != null) {
                map.put(field.getName(), field.get(checkoutDto).toString());
            }
        }

        return map;
    }

}