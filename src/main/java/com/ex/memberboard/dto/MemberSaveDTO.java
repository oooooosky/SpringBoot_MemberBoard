package com.ex.memberboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveDTO {


    private String memberEmail;


    private String memberPassword;


    private String memberName;


    private String memberPhone;


    private MultipartFile memberFile;

    private String memberFilename;

}
