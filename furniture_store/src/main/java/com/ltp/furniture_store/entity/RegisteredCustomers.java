package com.ltp.furniture_store.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;



@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registered_customers")
public class RegisteredCustomers {

    @Id
    @Column(name = "customer_id",nullable = false)
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

    @NonNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "permission_type_id", nullable = false)
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

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

}
