package com.airgear.dto;

import com.airgear.model.goods.Category;
import com.airgear.model.goods.Goods;
import com.airgear.model.User;
import com.airgear.model.goods.Location;
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
    private BigDecimal weekendsPrice;
    private Location location;
    private Category category;
    private String phoneNumber;
    private User user;

    public Goods getGoodsFromDto(){
        Goods goods = new Goods();
        goods.setName(name);
        goods.setDescription(description);
        goods.setPrice(price);
        goods.setWeekendsPrice(weekendsPrice);
        goods.setLocation(location);
        goods.setCategory(category);
        goods.setPhoneNumber(phoneNumber);
        goods.setUser(user);
        goods.setLocation(location);
        return goods;
    }

}