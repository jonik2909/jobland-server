package com.backend.jobland.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.jobland.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByMemberNick(String memberNick); // SELECT * FROM members WHERE memberNick = 'justin'
}
