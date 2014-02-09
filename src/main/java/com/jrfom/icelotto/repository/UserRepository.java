package com.jrfom.icelotto.repository;

import com.jrfom.icelotto.model.PrizeTier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<PrizeTier, Long> {
//  public User findByGw2DisplayName() throws UserNotFoundException;
}
