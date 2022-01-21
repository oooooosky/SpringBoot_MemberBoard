package com.ex.memberboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardPagingDTO {

    private Long boardId;
    private String boardWriter;
    private String boardTitle;
    private Long boardHits;

}