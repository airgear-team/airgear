package com.airgear.dto;

import com.airgear.model.goods.Goods;
import com.airgear.model.location.Location;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
@Data
@Setter(AccessLevel.NONE)
@AllArgsConstructor
public class GoodsResponseDTO {
    long id;
    String name;
    String description;
    double price;
    Location location;
    long userId;
    public static GoodsResponseDTO fromGoods(Goods goods) {
        return new GoodsResponseDTO(
                goods.getId(),
                goods.getName(),
                goods.getDescription(),
                goods.getPrice().doubleValue(),
                goods.getLocation(),
                goods.getUser().getId()
        );
    }
}
