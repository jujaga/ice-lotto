package com.jrfom.icelotto.repository;

import com.jrfom.icelotto.model.PrizeTier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<PrizeTier, Long> {
}
