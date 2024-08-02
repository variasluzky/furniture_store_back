package com.ltp.furniture_store.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ltp.furniture_store.entity.PermissionType;
import com.ltp.furniture_store.service.PermissionTypeService;

import java.util.List;

@RestController
@RequestMapping("/permission-types")
public class PermissionTypeController {

    @Autowired
    private PermissionTypeService service;

    // Fetch all permission types
    @GetMapping
    public List<PermissionType> getAllPermissionTypes() {
        return service.getAllPermissionTypes();
    }

    // Fetch a single permission type by ID
    @GetMapping("/{id}")
    public PermissionType getPermissionTypeById(@PathVariable Short id) {
        return service.getPermissionTypeById(id);
    }

    // Create a new permission type
    @PostMapping
    public PermissionType createPermissionType(@RequestBody PermissionType permissionType) {
        return service.createPermissionType(permissionType);
    }

    // Update an existing permission type
    @PutMapping("/{id}")
    public PermissionType updatePermissionType(@PathVariable Short id, @RequestBody PermissionType updatedPermissionType) {
        return service.updatePermissionType(id, updatedPermissionType);
    }

    // Delete a permission type by ID
    @DeleteMapping("/{id}")
    public void deletePermissionType(@PathVariable Short id) {
        service.deletePermissionType(id);
    }
}
