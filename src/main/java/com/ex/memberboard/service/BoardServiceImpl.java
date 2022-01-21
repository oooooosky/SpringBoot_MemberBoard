package com.ex.memberboard.service;

import com.ex.memberboard.common.PagingConst;
import com.ex.memberboard.dto.*;
import com.ex.memberboard.entity.BoardEntity;
import com.ex.memberboard.entity.MemberEntity;
import com.ex.memberboard.repository.BoardRepository;
import com.ex.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository br;
    private final MemberRepository mr;

    @Override
    public Page<BoardPagingDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber(); // pageable 객체에서 .get을 통해 page 정보를 꺼낼 수 있음.
        // JPA가 관리하는 페이지 객체는 0번부터 관리
        // 그래서 삼항 연산자가 필요

        // 요청한 페이지가 1이면 페이지 값을 0으로 하고, 1이 아니면 요청 페이지에서 1을 뺀다.
//        page = page-1;
        page = (page==1)? 0:(page-1);
        // Pageable 타입의 pagealbe 객체를 넘겨주는 findAll() 호출
        Page<BoardEntity> boardEntities = br.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        // findAll(PageRequest.of(어떤 페이지를, 어떤 갯수로), 정렬 (어떤 정렬 ,"기준으로 삼을 Entity 클래스의 필드이름"))
        // 기준으로 삼을 Entity 클래스의 필드이름에 언더바( _ ) 가 포함되어있으면 JPA 가 인식을 못해 오류 발생
        // findAll의 리턴값은 Page<Entity타입>로 넘어옴. 따라서 DTO로 변환해주는 작업이 필요함.
        // Page<BoardEntity> -> Page<BoardPagingDTO>
        // 기존 DTO에서 변환해주는 작업을 할 필요 없이 Page객체가 제공해주느 메서드를 사용하면 자동으로 변환시켜줌
        // 아래와 같은 변환용 코드
        Page<BoardPagingDTO> boardList = boardEntities.map(
                board -> new BoardPagingDTO(board.getId(),
                        board.getMemberEntity().getMemberEmail(),
                        board.getBoardTitle(),
                        board.getBoardHits())
        );
        return boardList;
    }

    @Override
    public Long save(BoardSaveDTO boardSaveDTO) throws IOException {
        MultipartFile boardFile = boardSaveDTO.getBoardFile();
        String boardFilename = boardFile.getOriginalFilename();
        boardFilename = System.currentTimeMillis()+"-"+boardFilename;
        String savePath = "/Users/sky/EclipseJava/source/SpringBoot/MemberBoard/src/main/resources/static/image/"+boardFilename;
        if (!boardFile.isEmpty()) {
            boardFile.transferTo(new File(savePath));
        }
        boardSaveDTO.setBoardFilename(boardFilename);
        System.out.println("boardSaveDTO.getBoardFilename() = " + boardSaveDTO.getBoardFilename());

        MemberEntity memberEntity = mr.findById(boardSaveDTO.getMemberId()).get();
        BoardEntity boardEntity = BoardEntity.toSaveBoardEntity(boardSaveDTO, memberEntity);
        return br.save(boardEntity).getId();
    }

    @Override
    @Transactional
    public BoardDetailDTO findById(Long boardId) {
        int boardHits = br.boardHits(boardId);
        BoardEntity boardEntity = br.findById(boardId).get();
//        BoardEntity boardHits = BoardEntity.toHitsBoardEntity(boardEntity);
//        br.save(boardHits);
        BoardDetailDTO boardDetailDTO = BoardDetailDTO.toBoardDetailDTO(boardEntity);
        return boardDetailDTO;
    }

    @Override
    public Long update(BoardUpdateDTO boardUpdateDTO) {
        MemberEntity memberEntity = mr.findById(boardUpdateDTO.getMemberId()).get();
        BoardEntity boardEntity = BoardEntity.toUpdateBoardEntity(boardUpdateDTO, memberEntity);
        return br.save(boardEntity).getId();
    }

    @Override
    public void delete(Long boardId) {
        br.deleteById(boardId);
    }

//    @Override
//    public Page<BoardPagingDTO> searchPaging(BoardSearchDTO boardSearchDTO, Pageable pageable) {
//        int page = pageable.getPageNumber();
//        page = (page==1)? 0:(page-1);
//        if (boardSearchDTO.getChoice().equals("writer")) {
//            Page<BoardEntity> boardEntities = br.findByBoardWriter(boardSearchDTO.getKeyword(), PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
//            System.out.println("boardEntities = " + boardEntities);
//            Page<BoardPagingDTO> boardList = boardEntities.map(
//                    board -> new BoardPagingDTO(board.getId(),
//                            board.getMemberEntity().getMemberEmail(),
//                            board.getBoardTitle(),
//                            board.getBoardHits())
//            );
//            return boardList;
//        } else {
//            Page<BoardEntity> boardEntities = br.findByBoardTitle(boardSearchDTO.getKeyword(), PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
//            Page<BoardPagingDTO> boardList = boardEntities.map(
//                    board -> new BoardPagingDTO(board.getId(),
//                            board.getMemberEntity().getMemberEmail(),
//                            board.getBoardTitle(),
//                            board.getBoardHits())
//            );
//            return boardList;
//        }
//    }

    @Override
    public List<BoardDetailDTO> search(BoardSearchDTO boardSearchDTO) {
        if (boardSearchDTO.getChoice().equals("writer")) {
            List<BoardEntity> boardEntityList = br.findByBoardWriterContaining(boardSearchDTO.getKeyword());
            List<BoardDetailDTO> boardDetailDTOList = BoardDetailDTO.toBoardDetailList(boardEntityList);
            System.out.println("boardDetailDTOList = " + boardDetailDTOList);
            return boardDetailDTOList;
        } else {
            List<BoardEntity> boardEntityList = br.findByBoardTitleContaining(boardSearchDTO.getKeyword());
            List<BoardDetailDTO> boardDetailDTOList = BoardDetailDTO.toBoardDetailList(boardEntityList);
            System.out.println("boardDetailDTOList = " + boardDetailDTOList);
            return boardDetailDTOList;
        }
    }


}