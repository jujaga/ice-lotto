package com.jrfom.icelotto.repository;

import com.jrfom.icelotto.model.PrizeItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrizeItemRepository extends JpaRepository<PrizeItem, Long> {
}