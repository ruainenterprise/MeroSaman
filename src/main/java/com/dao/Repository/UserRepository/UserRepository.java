package com.dao.Repository.UserRepository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dao.Entity.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByEmail(String username);

  Boolean existsByEmail(String email);

  Optional<UserEntity> findByEmailAndAccountStatus(String username, int i);

  int findByEmailAndTempValue(String email, String code);

  int countByEmailAndTempValue(String email, String code);
  

//   Update QUery
  @Transactional
  @Modifying
  @Query("UPDATE UserEntity u SET u.accountStatus = :accountStatus,u.tempValue = :tempValue WHERE u.email = :email")
  int updateEntityAccountStatusAndTempValue(@Param("accountStatus") int accountStatus,@Param("tempValue") String tempValue,
  @Param("email") String email);
}
