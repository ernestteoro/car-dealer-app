package com.assignment.cardealer.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Objects;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles_user", schema = "car_dealers")
public class RolesUser {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_roles_user", nullable = false)
    private int idRolesUser;
    @Basic
    @Column(name = "id_users", insertable=false, updatable=false)
    private int idUsers;
    @Basic
    @Column(name = "roles", nullable = true, length = 45)
    private String roles;
    @Basic
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    @Basic
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private Timestamp updatedAt = new Timestamp(System.currentTimeMillis());

    @ManyToOne
    @JoinColumn(name = "id_users", referencedColumnName = "id_users", nullable = false)
    private Users usersByIdUsers;

    /*
    public int getIdRolesUser() {
        return idRolesUser;
    }

    public void setIdRolesUser(int idRolesUser) {
        this.idRolesUser = idRolesUser;
    }

    public int getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(int idUsers) {
        this.idUsers = idUsers;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getupdatedAt() {
        return updatedAt;
    }

    public void setupdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolesUser rolesUser = (RolesUser) o;
        return idRolesUser == rolesUser.idRolesUser && idUsers == rolesUser.idUsers && Objects.equals(roles, rolesUser.roles) && Objects.equals(createdAt, rolesUser.createdAt) && Objects.equals(updatedAt, rolesUser.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRolesUser, idUsers, roles, createdAt, updatedAt);
    }

    public Users getUsersByIdUsers() {
        return usersByIdUsers;
    }

    public void setUsersByIdUsers(Users usersByIdUsers) {
        this.usersByIdUsers = usersByIdUsers;
    }

     */
}
