package com.binhdc.springbootcommonproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.binhdc.springbootcommonproject.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
}
