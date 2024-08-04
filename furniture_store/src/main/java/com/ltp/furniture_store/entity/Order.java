package com.ltp.furniture_store.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private RegisteredCustomer customer;

    @NonNull
    @Column(name = "total_price")
    private Double totalPrice;

    @NonNull
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status", nullable = false)
    private Status status;

    @NonNull
    @Column(name = "address")
    private String address;

    @NonNull
    @Column(name = "delivery")
    private Boolean delivery;

}
