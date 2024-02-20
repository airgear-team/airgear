package com.airgear.model.goods.response;

import com.airgear.model.goods.Goods;

/**
 * The {@code GoodsResponse} representing the response for a Goods entity.
 *
 * This record includes essential information about a goods entity such as its identifier, name,
 * description, price, location, and the user's identifier who owns the goods.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public record GoodsResponse(long id,
                            String name,
                            String description,
                            double price,
                            String location,
                            long userId
) {

    /**
     * Creates a new GoodsResponse object from a Goods entity.
     *
     * @param goods The Goods entity from which to create the response.
     * @return A new GoodsResponse object with information extracted from the provided Goods entity.
     */
    public static GoodsResponse fromGoods(Goods goods) {
        return new GoodsResponse(
                goods.getId(),
                goods.getName(),
                goods.getDescription(),
                goods.getPrice().doubleValue(),
                goods.getLocation(),
                goods.getUser().getId()
        );
    }
}
