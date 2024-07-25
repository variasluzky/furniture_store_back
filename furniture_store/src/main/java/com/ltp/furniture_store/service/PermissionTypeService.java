package com.ltp.furniture_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ltp.furniture_store.entity.PermissionType;
import com.ltp.furniture_store.repository.PermissionTypeRepository;

import java.util.List;

@Service
public class PermissionTypeService {

    @Autowired
    private PermissionTypeRepository repository;

    public List<PermissionType> getAllPermissionTypes() {
        return repository.findAll();
    }

    public PermissionType getPermissionTypeById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public PermissionType createPermissionType(PermissionType permissionType) {
        return repository.save(permissionType);
    }

    public PermissionType updatePermissionType(Long id, PermissionType permissionType) {
        PermissionType existingPermissionType = repository.findById(id).orElse(null);
        if (existingPermissionType != null) {
            existingPermissionType.setName(permissionType.getName());
            existingPermissionType.setDescription(permissionType.getDescription());
            return repository.save(existingPermissionType);
        }
        return null;
    }

    public void deletePermissionType(Long id) {
        repository.deleteById(id);
    }
}
