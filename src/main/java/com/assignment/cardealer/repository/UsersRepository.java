package com.assignment.cardealer.repository;

import com.assignment.cardealer.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findUsersByUsername(String name);

}