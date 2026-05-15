package com.backend.jobland.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import com.backend.jobland.lib.enums.CategoryType;
import com.backend.jobland.lib.enums.Country;
import com.backend.jobland.lib.enums.MemberFeatured;
import com.backend.jobland.lib.enums.MemberStatus;
import com.backend.jobland.lib.enums.MemberType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DynamicInsert
@Table(name = "Members")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(nullable = false, unique = true)
    private String memberNick;

    @Column(nullable = false)
    @JsonIgnore
    private String memberPassword;

    @Column(nullable = false, unique = true)
    private String memberPhone;

    @Column(nullable = false, columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer memberAge = 0;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer memberViews = 0;

    @Column(nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    @Column(unique = true)
    private String memberEmail;

    private String memberImage;

    private String memberWebsite;

    private String memberTeamSize;

    @Column(columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private Country memberCountry;

    private String memberCity;

    @Column(columnDefinition = "TEXT")
    private String memberDesc;

    private String memberSalary;

    private String memberExperience;

    private String memberLanguage;

    private Double memberHourRate;

    @Column(columnDefinition = "VARCHAR(36)")
    @Enumerated(EnumType.STRING)
    private CategoryType memberCategory;

    @Column(columnDefinition = "VARCHAR(10) DEFAULT 'NO'")
    @Enumerated(EnumType.STRING)
    private MemberFeatured memberFeatured;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer activeJobs = 0;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Transient
    private List<Background> membeBackgrounds;
}
