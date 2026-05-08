package com.backend.jobland.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.jobland.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByMemberNick(String memberNick); // SELECT * FROM members WHERE memberNick = 'justin'

    @Modifying
    @Query("UPDATE Member m SET m.memberViews = m.memberViews + 1 WHERE m.id = :id")
    void incrementMemberViews(@Param("id") String id);
}
