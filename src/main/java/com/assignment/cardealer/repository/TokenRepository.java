package com.assignment.cardealer.repository;

import com.assignment.cardealer.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

  @Query(value = "select t.id, t.expired, t.revoked, t.token, t.token_type, t.id_users from token t inner join users u on t.id_users = u.id_users where u.id_users = :id and (t.expired = false or t.revoked = false)", nativeQuery = true)
  List<Token> findAllValidTokenByUser(int id);

  Optional<Token> findByToken(String token);
}
