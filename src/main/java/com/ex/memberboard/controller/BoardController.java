package com.ex.memberboard.controller;

import com.ex.memberboard.common.PagingConst;
import com.ex.memberboard.dto.*;
import com.ex.memberboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/*")
public class BoardController {

    private final BoardService bs;

    @GetMapping
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<BoardPagingDTO> boardList = bs.paging(pageable);
        model.addAttribute("boardList", boardList);

        // startPage, endPage 계산
        // 현재 2페이지 일 때 시작 페이지 1, 끝 페이지 3
        // 현재 7페이지 일 때 시작 페이지 6, 끝 페이지 8
        int startPage = (((int)(Math.ceil((double) pageable.getPageNumber()/ PagingConst.BLOCK_LIMIT)))-1)*PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage+PagingConst.BLOCK_LIMIT-1)<boardList.getTotalPages())?startPage+PagingConst.BLOCK_LIMIT-1 : boardList.getTotalPages();

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "board/paging";
    }

    @GetMapping("save")
    public String saveForm(Model model) {
        model.addAttribute("board", new BoardSaveDTO());
        return "board/save";
    }

    @PostMapping("save")
    public String save(@ModelAttribute BoardSaveDTO boardSaveDTO) throws IOException {
        Long boardId = bs.save(boardSaveDTO);
        return "redirect:/board/";
    }

    @GetMapping("{boardId}")
    public String findById(@PathVariable Long boardId, Model model) {
        BoardDetailDTO boardDetailDTO = bs.findById(boardId);
        model.addAttribute("board", boardDetailDTO);
        model.addAttribute("comment", boardDetailDTO.getCommentList());
        return "board/findById";
    }

    @GetMapping("update/{boardId}")
    public String updateForm(@PathVariable Long boardId, Model model) {
        BoardDetailDTO boardDetailDTO = bs.findById(boardId);
        model.addAttribute("board", boardDetailDTO);
        return "board/update";
    }

    @PutMapping("{boardId}")
    public ResponseEntity update(@RequestBody BoardUpdateDTO boardUpdateDTO) {
        Long boardId = bs.update(boardUpdateDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("{boardId}")
    public ResponseEntity delete(@PathVariable Long boardId) {
        bs.delete(boardId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("search")
    public String search(@ModelAttribute BoardSearchDTO boardSearchDTO, Model model) {
        System.out.println("boardSearchDTO = " + boardSearchDTO);
//        Page<BoardPagingDTO> boardList = bs.searchPaging(boardSearchDTO, pageable);
        List<BoardDetailDTO> boardDetailDTOList = bs.search(boardSearchDTO);
        model.addAttribute("boardList", boardDetailDTOList);
//        model.addAttribute("boardList", boardList);
//
//        int startPage = (((int)(Math.ceil((double) pageable.getPageNumber()/ PagingConst.BLOCK_LIMIT)))-1)*PagingConst.BLOCK_LIMIT + 1;
//        int endPage = ((startPage+PagingConst.BLOCK_LIMIT-1)<boardList.getTotalPages())?startPage+PagingConst.BLOCK_LIMIT-1 : boardList.getTotalPages();
//
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);

        return "board/search";
    }

}
