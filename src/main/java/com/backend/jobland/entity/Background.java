package com.backend.jobland.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.backend.jobland.lib.enums.BackgroundType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Backgrounds")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Background {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(nullable = false, columnDefinition = "VARCHAR(36)")
    private String memberId;

    @Column(nullable = false, columnDefinition = "VARCHAR(36)")
    @Enumerated(EnumType.STRING)
    private BackgroundType backType;

    @Column(nullable = false)
    private String backName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String backDesc;

    @Column(nullable = false)
    private LocalDate backStart;

    @Column(nullable = false)
    private LocalDate backEnd;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
