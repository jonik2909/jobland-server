package com.backend.jobland.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import com.backend.jobland.lib.enums.CategoryType;
import com.backend.jobland.lib.enums.Country;
import com.backend.jobland.lib.enums.JobLevel;
import com.backend.jobland.lib.enums.JobStatus;
import com.backend.jobland.lib.enums.JobType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DynamicInsert
@Table(name = "Jobs")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(nullable = false, columnDefinition = "VARCHAR(36)")
    private String companyId;

    @Column(nullable = false)
    private String jobTitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String jobDesc;

    @Column(nullable = false, columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String jobRequirement;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String jobExpertise;

    @Column(nullable = false, columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private Country jobCountry;

    @Column(nullable = false)
    private String jobCity;

    @Column(nullable = false)
    private String jobAddress;

    @Column(nullable = false)
    private String jobSalary;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JobLevel jobLevel;

    @Column(nullable = false)
    private String jobExperience;

    @Column(nullable = false)
    private Double jobHourRate;

    @Column(nullable = false)
    private String jobDeadline;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryType jobCategory;

    @Column(columnDefinition = "VARCHAR(20) DEFAULT 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private JobStatus jobStatus = JobStatus.ACTIVE;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer jobViews = 0;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId", insertable = false, updatable = false)
    private Member company;

    @Transient
    private Long appliedCount;

    @Transient
    @JsonIgnoreProperties({ "company", "candidate", "job" })
    private Application meApplied;
}
