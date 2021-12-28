package com.dao.Repository.UserRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dao.Entity.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByEmail(String username);

  Boolean existsByEmail(String email);
 

//  Boolean existsByEmail(String email);
}
