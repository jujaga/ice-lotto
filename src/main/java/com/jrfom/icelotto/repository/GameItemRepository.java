package com.jrfom.icelotto.repository;

import com.jrfom.icelotto.model.GameItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameItemRepository extends JpaRepository<GameItem, Long> {
}