package com.airgear.model;

import com.airgear.model.goods.Goods;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class GoodsView {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column
    private long userId;

    @Column
    private String ip;

    @Column(name = "created_at", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime createdAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "goods_id")
    private Goods goods;


    public GoodsView(long userId, String ip, OffsetDateTime createdAt, Goods goods) {
        this.userId = userId;
        this.ip = ip;
        this.createdAt = createdAt;
        this.goods = goods;
    }
}
