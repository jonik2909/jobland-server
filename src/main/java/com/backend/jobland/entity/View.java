package com.backend.jobland.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import com.backend.jobland.lib.enums.ViewGroup;
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
@DynamicInsert
@Table(name = "Views")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class View {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(nullable = false, columnDefinition = "VARCHAR(36)")
    private String memberId;

    @Column(nullable = false, columnDefinition = "VARCHAR(36)")
    private String viewRefId;

    @Column(nullable = false, columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private ViewGroup viewGroup;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
