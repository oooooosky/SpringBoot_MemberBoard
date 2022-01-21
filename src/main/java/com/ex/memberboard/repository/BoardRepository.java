package com.ex.memberboard.repository;

import com.ex.memberboard.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findByBoardWriter(String keyword);

    List<BoardEntity> findByBoardTitle(String keyword);

//    Page<BoardEntity> findByBoardWriter(String keyword, PageRequest id);

//    Page<BoardEntity> findByBoardTitle(String keyword, PageRequest id);
}
