package com.airgear.model.goods;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "settlement", nullable = false)
    private String settlement;

    @Column(name = "region_id", nullable = false)
    private Long regionId;

}
