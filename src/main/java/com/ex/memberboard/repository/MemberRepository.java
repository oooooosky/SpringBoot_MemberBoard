package com.ex.memberboard.repository;

import com.ex.memberboard.dto.MemberDetailDTO;
import com.ex.memberboard.dto.MemberLoginDTO;
import com.ex.memberboard.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    MemberEntity findByMemberEmail(String memberEmail);
}
