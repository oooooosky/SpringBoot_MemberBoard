package com.ex.memberboard.dto;

import com.ex.memberboard.entity.BoardEntity;
import com.ex.memberboard.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDetailDTO {

    private Long boardId;
    private Long memberId;
    private String boardWriter;
    private String boardTitle;
    private String boardContents;
    private String boardFilename;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private List<CommentDetailDTO> commentList;
    private int boardHits;

    public static BoardDetailDTO toBoardDetailDTO(BoardEntity boardEntity) {
        BoardDetailDTO boardDetailDTO = new BoardDetailDTO();
        boardDetailDTO.setBoardId(boardEntity.getId());
        boardDetailDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDetailDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDetailDTO.setBoardContents(boardEntity.getBoardContents());
        boardDetailDTO.setBoardFilename(boardEntity.getBoardFilename());
        boardDetailDTO.setMemberId(boardEntity.getMemberEntity().getId());
        boardDetailDTO.setCreateTime(boardEntity.getCreateTime());
        boardDetailDTO.setUpdateTime(boardEntity.getUpdateTime());
        boardDetailDTO.setBoardHits(boardEntity.getBoardHits());
        boardDetailDTO.setCommentList(CommentEntity.toCommentEntityList(boardEntity.getCommentEntityList()));
        return boardDetailDTO;
    }

    public static List<BoardDetailDTO> toBoardDetailList(List<BoardEntity> boardEntityList) {
        List<BoardDetailDTO> boardDetailDTOList = new ArrayList<>();
        for (BoardEntity b: boardEntityList) {
            boardDetailDTOList.add(toBoardDetailDTO(b));
        }
        return boardDetailDTOList;
    }
}
