package com.airgear.dto;

import com.airgear.model.goods.enums.GoodsCondition;
import com.airgear.model.goods.enums.GoodsStatus;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
public class GoodsDto {
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 255, message = "Name length must be between 3 and 255 characters")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 10, max = 1000, message = "Description length must be between 10 and 1000 characters")
    private String description;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @DecimalMin(value = "0.0", inclusive = false, message = "Weekend price must be greater than 0")
    private BigDecimal weekendsPrice;

    @Valid
    private LocationDto location;

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