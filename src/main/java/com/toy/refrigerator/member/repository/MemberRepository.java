package com.toy.refrigerator.member.repository;

import com.toy.refrigerator.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByEmail(String email);
}
