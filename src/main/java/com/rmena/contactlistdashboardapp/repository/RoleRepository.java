package com.rmena.contactlistdashboardapp.repository;

import com.rmena.contactlistdashboardapp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByUserRole(String role);
}
