package com.backend.jobland.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.jobland.entity.Application;
import com.backend.jobland.lib.enums.ApplicationStatus;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, String> {
        boolean existsByCandidateIdAndJobId(String candidateId, String jobId);

        Optional<Application> findByIdAndCandidateId(String id, String candidateId);

        @Query("SELECT a FROM Application a WHERE " +
                        "(:candidateId IS NULL OR a.candidateId = :candidateId) AND " +
                        "(:applicationStatus IS NULL OR a.applicationStatus = :applicationStatus)")
        Page<Application> findApplicationsByFilters(
                        @Param("candidateId") String candidateId,
                        @Param("applicationStatus") ApplicationStatus applicationStatus,
                        Pageable pageable);

        @Query(value = "SELECT a FROM Application a " +
                        "JOIN FETCH a.candidate c " +
                        "JOIN FETCH a.job j " +
                        "WHERE a.companyId = :companyId " +
                        "AND (:jobId IS NULL OR a.jobId = :jobId) " +
                        "AND (:status IS NULL OR a.applicationStatus = :status)", countQuery = "SELECT COUNT(a) FROM Application a "
                                        +
                                        "WHERE a.companyId = :companyId " +
                                        "AND (:jobId IS NULL OR a.jobId = :jobId) " +
                                        "AND (:status IS NULL OR a.applicationStatus = :status)")
        Page<Application> findCompanyApplications(
                        @Param("companyId") String companyId,
                        @Param("jobId") String jobId,
                        @Param("status") ApplicationStatus status,
                        Pageable pageable);

        long countByJobId(String jobId);

        long countByJobIdAndApplicationStatus(String jobId, ApplicationStatus applicationStatus);

        Optional<Application> findByIdAndCompanyId(String id, String companyId);
}
