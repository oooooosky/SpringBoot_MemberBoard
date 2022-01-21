package com.ex.memberboard.repository;

import com.ex.memberboard.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits = b.boardHits+1 where b.id = :boardId")
    int boardHits(@Param("boardId") Long boardId);

    List<BoardEntity> findByBoardWriterContaining(String keyword);

    List<BoardEntity> findByBoardTitleContaining(String keyword);

//    Page<BoardEntity> findByBoardWriter(String keyword, PageRequest id);

//    Page<BoardEntity> findByBoardTitle(String keyword, PageRequest id);
}
