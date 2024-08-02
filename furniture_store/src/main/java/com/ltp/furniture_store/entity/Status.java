package com.ltp.furniture_store.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Short statusId;

    @NonNull
    @Column(name = "description_status", nullable = false)
    private String descriptionStatus;
}
