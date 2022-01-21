package com.ex.memberboard.entity;

import com.ex.memberboard.dto.BoardSaveDTO;
import com.ex.memberboard.dto.BoardUpdateDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column
    private String boardWriter;

    @Column
    private String boardFilename;

    @Column
    private String boardTitle;

    @Column
    private String boardContents;

    @Column
    private int boardHits;

    // 회원 엔티티와의 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();


    public static BoardEntity toSaveBoardEntity(BoardSaveDTO boardSaveDTO, MemberEntity memberEntity) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(memberEntity.getMemberEmail());
        boardEntity.setBoardTitle(boardSaveDTO.getBoardTitle());
        boardEntity.setBoardContents(boardSaveDTO.getBoardContents());
        boardEntity.setBoardFilename(boardSaveDTO.getBoardFilename());
        boardEntity.setMemberEntity(memberEntity);
        boardEntity.setBoardHits(0);
        return boardEntity;
    }

    public static BoardEntity toUpdateBoardEntity(BoardUpdateDTO boardUpdateDTO, MemberEntity memberEntity) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardUpdateDTO.getBoardId());
        boardEntity.setBoardWriter(memberEntity.getMemberEmail());
        boardEntity.setBoardTitle(boardUpdateDTO.getBoardTitle());
        boardEntity.setBoardContents(boardUpdateDTO.getBoardContents());
        boardEntity.setBoardFilename(boardUpdateDTO.getBoardFilename());
        boardEntity.setMemberEntity(memberEntity);
        return boardEntity;
    }

    public static BoardEntity toHitsBoardEntity(BoardEntity boardEntity) {
        boardEntity.setId(boardEntity.getId());
        boardEntity.setBoardHits((boardEntity.getBoardHits()+1));
        return boardEntity;
    }
}
