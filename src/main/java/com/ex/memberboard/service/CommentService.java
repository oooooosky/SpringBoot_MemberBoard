package com.ex.memberboard.service;

import com.ex.memberboard.dto.CommentDetailDTO;
import com.ex.memberboard.dto.CommentSaveDTO;

import java.util.List;

public interface CommentService {
    Long save(CommentSaveDTO commentSaveDTO);

    List<CommentDetailDTO> findAll(Long boardId);
}
