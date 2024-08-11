package com.ltp.furniture_store;

import com.ltp.furniture_store.entity.PermissionType;
import com.ltp.furniture_store.repository.PermissionTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(PermissionTypeRepository permissionTypeRepository) {
        return args -> {
            // Check if the table is empty
            if (permissionTypeRepository.count() == 0) {
                PermissionType admin = new PermissionType();
                admin.setPermissionStatus("admin");
                permissionTypeRepository.save(admin);


                PermissionType user = new PermissionType();
                user.setPermissionStatus("user");
                permissionTypeRepository.save(user);
            }
        };
    }
}


