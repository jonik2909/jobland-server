package com.backend.jobland.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.jobland.entity.View;

@Repository
public interface ViewRepository extends JpaRepository<View, String> {
    Optional<View> findFirstByMemberIdAndViewRefId(String memberId, String viewRefId);
}
