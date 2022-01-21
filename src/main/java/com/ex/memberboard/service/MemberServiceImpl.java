package com.ex.memberboard.service;

import com.ex.memberboard.dto.MemberDetailDTO;
import com.ex.memberboard.dto.MemberLoginDTO;
import com.ex.memberboard.dto.MemberSaveDTO;
import com.ex.memberboard.dto.MemberUpdateDTO;
import com.ex.memberboard.entity.MemberEntity;
import com.ex.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository mr;

    @Override
    public Long save(MemberSaveDTO memberSaveDTO) throws IOException {

        MultipartFile memberFile = memberSaveDTO.getMemberFile();
        String memberFilename = memberFile.getOriginalFilename();
        memberFilename = System.currentTimeMillis()+"-"+memberFilename;
        String savePath = "/Users/sky/EclipseJava/source/SpringBoot/MemberBoard/src/main/resources/static/image/"+memberFilename;
        if (!memberFile.isEmpty()) {
            memberFile.transferTo(new File(savePath));
        }
        memberSaveDTO.setMemberFilename(memberFilename);
        System.out.println("memberSaveDTO.getMemberFilename() = " + memberSaveDTO.getMemberFilename());

        MemberEntity memberEntity = MemberEntity.toSaveEntity(memberSaveDTO);
        return mr.save(memberEntity).getId();
    }

    @Override
    public MemberDetailDTO findByEmail(MemberLoginDTO memberLoginDTO) {
        try {
            MemberEntity memberEntity = mr.findByMemberEmail(memberLoginDTO.getMemberEmail());
            if (memberEntity.getMemberPassword().equals(memberLoginDTO.getMemberPassword())) {
                MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(memberEntity);
                return memberDetailDTO;
            } else {
                return null;
            }
        } catch (NullPointerException nullPointerException) {
            return null;
        }
    }

    @Override
    public String  emailCheck(String memberEmail) {
        String result = "NO";
        try {
            MemberEntity memberEntity = mr.findByMemberEmail(memberEmail);
            if (memberEntity.equals(null)) {
                result="OK";
                return result;
            } else {
                return result;
            }
        } catch (NullPointerException nullPointerException) {
            result="OK";
            return result;
        }
    }

    @Override
    public MemberDetailDTO findById(Long memberId) {
        Optional<MemberEntity> optionalMember = mr.findById(memberId);
        if (!optionalMember.isEmpty()) {
            MemberEntity memberEntity = optionalMember.get();
            MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(memberEntity);
            return memberDetailDTO;
        }
        return null;
    }

    @Override
    public Long update(MemberUpdateDTO memberUpdateDTO) {
        MemberEntity memberEntity = MemberEntity.toUpdateEntity(memberUpdateDTO);
        return mr.save(memberEntity).getId();
    }

    @Override
    public List<MemberDetailDTO> findAll() {
        List<MemberEntity> memberEntityList = mr.findAll();
        List<MemberDetailDTO> memberDetailDTOList = MemberDetailDTO.toMemberDetailDTOList(memberEntityList);
        return memberDetailDTOList;
    }

    @Override
    public void deleteById(Long memberId) {
        mr.deleteById(memberId);
    }

}
