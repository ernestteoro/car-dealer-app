package com.assignment.cardealer.service;

import com.assignment.cardealer.model.RolesUser;
import com.assignment.cardealer.repository.RolesUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolesUserService {


    private final RolesUserRepository rolesUserRepository;

    @Autowired
    RolesUserService(RolesUserRepository rolesUserRepository){
        this.rolesUserRepository = rolesUserRepository;
    }

    public RolesUser addRolesUser(RolesUser rolesUser){
        return this.rolesUserRepository.saveAndFlush(rolesUser);
    }

    public List<RolesUser> findAllRolesUser(){
        return rolesUserRepository.findAll();
    }

    public Optional<RolesUser> findOneRolesUserById(long idRolesUser){
        return rolesUserRepository.findById(idRolesUser);
    }

    public Optional<List<RolesUser>> findAllRolesUserByRoles(String name){
        return rolesUserRepository.findRolesUserByRoles(name);
    }

}