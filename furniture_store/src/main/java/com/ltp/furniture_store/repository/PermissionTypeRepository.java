package com.ltp.furniture_store.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.ltp.furniture_store.entity.PermissionType;

public interface PermissionTypeRepository extends JpaRepository<PermissionType, Short> {

    PermissionType findByPermissionStatus(String status);
}