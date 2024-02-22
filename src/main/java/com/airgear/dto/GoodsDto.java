package com.airgear.dto;

import com.airgear.model.Goods;
import com.airgear.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDto {
    private String name;
    private String description;
    private BigDecimal price;
    private String location;
    private User user;

    public Goods getGoodsFromDto(){
        Goods goods = new Goods();
        goods.setName(name);
        goods.setDescription(description);
        goods.setPrice(price);
        goods.setLocation(location);
        goods.setUser(user);

        return goods;
    }
}
