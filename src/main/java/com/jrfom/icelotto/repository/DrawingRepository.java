package com.jrfom.icelotto.repository;

import com.jrfom.icelotto.model.Drawing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrawingRepository extends JpaRepository<Drawing, Long> {
}
