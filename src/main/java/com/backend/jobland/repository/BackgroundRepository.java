package com.backend.jobland.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.jobland.entity.Background;

@Repository
public interface BackgroundRepository extends JpaRepository<Background, String> {
    Optional<Background> findByIdAndMemberId(String id, String memberId);

    // List<Background>
    // findBackgroundsByMemberIdOrderByBackTypeAscCreatedAtDesc(String memberId);

    @Query("SELECT b FROM Background b WHERE b.memberId = :memberId ORDER BY b.backType ASC, b.createdAt DESC")
    List<Background> findBackgroundsByMemberId(@Param("memberId") String memberId);
}
