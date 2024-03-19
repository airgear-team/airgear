package com.airgear.dto;

import com.airgear.model.goods.Goods;
import com.airgear.model.goods.Location;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

/**
 * The {@code GoodsResponse} representing the response for a Goods entity.
 * This class includes essential information about a goods entity such as its identifier, name,
 * description, price, location, and the user's identifier who owns the goods.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */

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

    /**
     * Creates a new GoodsResponseDTO object from a Goods entity.
     *
     * @param goods The Goods entity from which to create the response.
     * @return A new GoodsResponse object with information extracted from the provided Goods entity.
     */
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
