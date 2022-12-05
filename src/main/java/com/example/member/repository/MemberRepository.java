package com.example.member.repository;

import com.example.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
    // memberEmail로 조회
    // select*from member_table where memberEmail = ?
    Optional<MemberEntity> findByMemberEmail(String memberEmail);

}
