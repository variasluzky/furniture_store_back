package com.ltp.furniture_store.repository;

import com.ltp.furniture_store.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion,Short> {

    Promotion findByDescription(String promotionDescription);
}
