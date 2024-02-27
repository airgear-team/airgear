package com.airgear.model.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * The {@code GoodsStatus} enumeration representing the status of goods.
 * Goods can have one of the following statuses: ACTIVE, INACTIVE, or DELETED.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class GoodsStatus {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

}