package com.airgear.repository;

import com.airgear.model.GoodsView;
import com.airgear.model.goods.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsViewRepository extends JpaRepository<GoodsView, Long> {

    boolean existsByIpAndGoods(String ip, Goods goods);

    boolean existsByUserIdAndGoods(long userId, Goods goods);

}
