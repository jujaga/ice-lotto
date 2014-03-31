package com.jrfom.icelotto.repository;

import java.util.List;

import com.jrfom.icelotto.exception.UserNotFoundException;
import com.jrfom.icelotto.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
  @Query(value =
    "select u " +
    "from User u " +
    "where lower(u.gw2DisplayName) = lower(?1)"
  )
  User findByGw2DisplayName(String gw2DisplayName) throws UserNotFoundException;

  @Query(value =
    "select u " +
    "from User u " +
    "where lower(u.gw2DisplayName) = lower(?1) " +
    "and u.claimKey = ?2 " +
    "and u.enabled = false"
  )
  User findByGw2DisplayNameAndClaimKey(String gw2DisplayName, String claimKey) throws UserNotFoundException;

  @Query(value =
    "select u " +
    "from User u " +
    "where lower(u.gw2DisplayName) like lower(?1) " +
    "or lower(u.displayName) like lower(?1) " +
    "or lower(u.email) like lower(?1) " +
    "or (" +
      "select count(z.name) from Character z " +
      "where lower(z.name) like lower(?1) " +
      "and z member of u.characters" +
    ") > 0"
  )
  List<User> findAllLike(String term);

  @Query(value =
    "select u " +
    "from User u " +
    "order by u.gw2DisplayName asc"
  )
  List<User> findAllOrderByGw2DisplayName();
}
