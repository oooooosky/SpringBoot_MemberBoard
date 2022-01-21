package com.ex.memberboard;

import com.ex.memberboard.controller.MemberController;
import com.ex.memberboard.dto.MemberDetailDTO;
import com.ex.memberboard.dto.MemberSaveDTO;
import com.ex.memberboard.entity.MemberEntity;
import com.ex.memberboard.repository.MemberRepository;
import com.ex.memberboard.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberTest {

    @Autowired
    private MemberService ms;

    @Autowired
    private MemberRepository mr;


//    @Test
//    @Transactional
//    @Rollback
//    @DisplayName("회원가입 테스트")
//    public void saveTest() throws IOException {
//        assertThat(ms.save(new MemberSaveDTO("회원가입이메일", "회원가입비밀번호", "회원가입이름", "회원가입전화번호", "회원가입파일이름"))).isNotNull();
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    @DisplayName("로그인 테스트")
//    public void loginTest() throws IOException {
//        ms.save(new MemberSaveDTO("로그인이메일", "로그인비밀번호", "로그인이름", "로그인전화번호", "회원가입파일이름"));
//        MemberEntity memberEntity = mr.findByMemberEmail("로그인이메일");
//        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(memberEntity);
//        assertThat(memberDetailDTO).isNotNull();
//    }

    @Test
    @DisplayName("회원목록 테스트")
    public void findAllTest() {
        List<MemberDetailDTO> memberDetailDTOList = ms.findAll();
        System.out.println("memberDetailDTOList = " + memberDetailDTOList);
    }

}
