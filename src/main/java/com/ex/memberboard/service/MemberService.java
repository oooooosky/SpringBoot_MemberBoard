package com.ex.memberboard.service;

import com.ex.memberboard.dto.MemberDetailDTO;
import com.ex.memberboard.dto.MemberLoginDTO;
import com.ex.memberboard.dto.MemberSaveDTO;
import com.ex.memberboard.dto.MemberUpdateDTO;

import java.io.IOException;
import java.util.List;

public interface MemberService {
    Long save(MemberSaveDTO memberSaveDTO) throws IOException;

    MemberDetailDTO findByEmail(MemberLoginDTO memberLoginDTO);

    String emailCheck(String memberEmail);

    MemberDetailDTO findById(Long memberId);

    Long update(MemberUpdateDTO memberUpdateDTO);

    List<MemberDetailDTO> findAll();

    void deleteById(Long memberId);
}
