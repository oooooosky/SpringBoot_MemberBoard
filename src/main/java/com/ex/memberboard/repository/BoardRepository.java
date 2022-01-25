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

    // native query
    // jpql (java persistence query language)
    // 반드시 테이블애 대한 약칭을 써야함. as 는 생략 가능
    // query dsl
    // value 안에 오는 테이블이름의 기준은 엔티티에서 선언한 클래스 이름을 가져와야함.
    // value 안에 오는 테이블 컬럼이름의 기준은 엔티티에서 선언한 컬럼 변수를 가져와야함.
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits = b.boardHits+1 where b.id = :boardId")
    int boardHits(@Param("boardId") Long boardId);

    List<BoardEntity> findByBoardWriterContaining(String keyword);

    List<BoardEntity> findByBoardTitleContaining(String keyword);

//    Page<BoardEntity> findByBoardWriter(String keyword, PageRequest id);

//    Page<BoardEntity> findByBoardTitle(String keyword, PageRequest id);
}
