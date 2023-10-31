package com.project.schoolmanagment.repository.user;

import com.project.schoolmanagment.entity.concretes.user.UserRole;
import com.project.schoolmanagment.entity.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {

    @Query("Select r From UserRole r WHERE r.roleType=?1")
    Optional<UserRole> findByEnumRoleEquals(RoleType roleType);


}
