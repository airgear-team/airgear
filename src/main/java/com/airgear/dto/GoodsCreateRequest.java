package com.airgear.dto;

import com.airgear.model.GoodsCondition;
import com.airgear.model.GoodsStatus;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
public class GoodsCreateRequest {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 255, message = "Name length must be between 3 and 255 characters")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 10, max = 1000, message = "Description length must be between 10 and 1000 characters")
    private String description;

    private PriceDto price;

    private WeekendsPriceDto weekendsPrice;

    private DepositDto deposit;

    private Long locationId;

    @NotNull(message = "Category is required")
    @Valid
    private CategoryDto category;

    @Pattern(regexp = "^\\+\\d{1,3}\\s?\\(?\\d{1,4}\\)?[\\s.-]?\\d{1,4}[\\s.-]?\\d{1,4}$", message = "The phone number must be in the format +380XXXXXXXXX")
    private String phoneNumber;

    private UserDto user;

    @NotNull(message = "Goods condition is required")
    private GoodsCondition goodsCondition;

    private GoodsStatus status;
}