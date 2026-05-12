package com.backend.jobland.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.jobland.entity.Job;
import com.backend.jobland.lib.enums.CategoryType;
import com.backend.jobland.lib.enums.Country;
import com.backend.jobland.lib.enums.JobLevel;
import com.backend.jobland.lib.enums.JobStatus;
import com.backend.jobland.lib.enums.JobType;

@Repository
public interface JobRepository extends JpaRepository<Job, String> {
    Optional<Job> findByIdAndCompanyId(String id, String companyId);

    @Query("SELECT j FROM Job j WHERE " +
            "( " +
            "  (:mode = 'ADMIN') OR " +
            "  (:mode = 'COMPANY' AND j.jobStatus != com.backend.jobland.lib.enums.JobStatus.DELETE AND j.companyId = :currentCompanyId) OR "
            +
            "  (:mode = 'PUBLIC' AND j.jobStatus = com.backend.jobland.lib.enums.JobStatus.ACTIVE) " +
            ") AND " +
            "(:jobType IS NULL OR j.jobType = :jobType) AND " +
            "(:jobStatus IS NULL OR j.jobStatus = :jobStatus) AND " +
            "(:jobLevel IS NULL OR j.jobLevel = :jobLevel) AND " +
            "(:jobCountry IS NULL OR j.jobCountry = :jobCountry) AND " +
            "(:jobCategory IS NULL OR j.jobCategory = :jobCategory) AND " +
            "(:targetCompanyId IS NULL OR j.companyId = :targetCompanyId) AND " +
            "(:search IS NULL OR LOWER(j.jobTitle) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Job> findJobsByFilters(
            @Param("mode") String mode,
            @Param("currentCompanyId") String currentCompanyId,
            @Param("targetCompanyId") String targetCompanyId,
            @Param("jobType") JobType jobType,
            @Param("jobStatus") JobStatus jobStatus,
            @Param("jobLevel") JobLevel jobLevel,
            @Param("jobCountry") Country jobCountry,
            @Param("jobCategory") CategoryType jobCategory,
            @Param("search") String search,
            Pageable pageable);
}
