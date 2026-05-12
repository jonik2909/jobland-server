package com.backend.jobland.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.jobland.entity.Member;
import com.backend.jobland.lib.enums.CategoryType;
import com.backend.jobland.lib.enums.MemberFeatured;
import com.backend.jobland.lib.enums.MemberStatus;
import com.backend.jobland.lib.enums.MemberType;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
        Optional<Member> findByMemberNick(String memberNick); // SELECT * FROM members WHERE memberNick = 'justin'

        @Modifying
        @Query("UPDATE Member m SET m.memberViews = m.memberViews + 1 WHERE m.id = :id")
        void incrementMemberViews(@Param("id") String id);

        @Modifying
        @Query("UPDATE Member m SET m.activeJobs = m.activeJobs + :amount WHERE m.id = :id")
        void updateActiveJobsCount(@Param("id") String id, @Param("amount") int amount);

        @Query("SELECT m FROM Member m WHERE " +
                        "(:isAdmin = true OR (m.memberStatus = com.backend.jobland.lib.enums.MemberStatus.ACTIVE AND m.memberType != com.backend.jobland.lib.enums.MemberType.ADMIN)) "
                        +
                        "AND (:memberType IS NULL OR m.memberType = :memberType) " +
                        "AND (:memberCategory IS NULL OR m.memberCategory = :memberCategory) " +
                        "AND (:search IS NULL OR LOWER(m.memberNick) LIKE LOWER(CONCAT('%', :search, '%'))) " +
                        "AND (:memberFeatured IS NULL OR m.memberFeatured = :memberFeatured) " +
                        "AND (:memberStatus IS NULL OR m.memberStatus = :memberStatus)")
        Page<Member> findMembersByFilters(
                        @Param("memberType") MemberType memberType,
                        @Param("memberCategory") CategoryType memberCategory,
                        @Param("search") String search,
                        @Param("memberFeatured") MemberFeatured memberFeatured,
                        @Param("memberStatus") MemberStatus memberStatus,
                        @Param("isAdmin") boolean isAdmin,
                        Pageable pageable);
}
