package com.torodavid.thesis.financialregister.dal.dao;

import com.torodavid.thesis.financialregister.dal.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Role findByName(String name);
}