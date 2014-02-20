package com.jrfom.icelotto.repository;

import com.jrfom.icelotto.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
//  public User findByGw2DisplayName() throws UserNotFoundException;
}
