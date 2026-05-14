package com.backend.jobland.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import com.backend.jobland.lib.enums.ApplicationStatus;

@Getter
@Setter
@Entity
@Table(name = "Applications")
@DynamicInsert
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(name = "companyId", nullable = false, columnDefinition = "VARCHAR(36)")
    private String companyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId", insertable = false, updatable = false)
    private Member company;

    @Column(name = "jobId", nullable = false, columnDefinition = "VARCHAR(36)")
    private String jobId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jobId", insertable = false, updatable = false)
    private Job job;

    @Column(name = "candidateId", nullable = false, columnDefinition = "VARCHAR(36)")
    private String candidateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidateId", insertable = false, updatable = false)
    private Member candidate;

    @Column(columnDefinition = "VARCHAR(20) DEFAULT 'SUBMITTED'")
    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus = ApplicationStatus.SUBMITTED;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
