package com.assignment.cardealer.repository;

import com.assignment.cardealer.model.RolesUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RolesUserRepository extends JpaRepository<RolesUser, Long> {

    Optional<List<RolesUser>> findRolesUserByRoles(String roleUser);

    Optional<List<RolesUser>> findRolesUserByIdUsers(int idUser);

}