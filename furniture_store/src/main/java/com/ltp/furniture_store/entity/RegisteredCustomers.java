package com.ltp.furniture_store.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registered_customers")
public class RegisteredCustomers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;

    @NonNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NonNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NonNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @Id
    @NonNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "permissions", nullable = false)
    private PermissionType permissions;

    @NonNull
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @NonNull
    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @NonNull
    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date updatedAt;

    public enum PermissionType {
        ADMIN, USER, GUEST;
    }

}
