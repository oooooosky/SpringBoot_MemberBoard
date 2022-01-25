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

//    @NotBlank
    private String memberEmail;

//    @NotBlank
    private String memberPassword;

//    @NotBlank
    private String memberName;

//    @NotBlank
    private String memberPhone;


    private MultipartFile memberFile;


    private String memberFilename;

}
