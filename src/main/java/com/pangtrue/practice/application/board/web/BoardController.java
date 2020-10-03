package com.pangtrue.practice.application.board.web;

import com.pangtrue.practice.application.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * User: SeungHo Lee (seung7642@gmail.com)
 * Date: 2020. 1. 12.
 * Time: 오전 9:50
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public ModelAndView list(ModelAndView mnv,
                             @PageableDefault(size = 10, page = 1) Pageable pageable) {
        log.debug("요청으로 들어온 pageNumber = {}, pageSize = {}", pageable.getPageNumber(), pageable.getPageSize());

        mnv.addObject("boardList", boardService.getArticleList(pageable));
        mnv.setViewName("board/list");
        return mnv;
    }

    @GetMapping("/write")
    public ModelAndView writeGet(ModelAndView mnv) {
        mnv.setViewName("board/write");
        return mnv;
    }

    @GetMapping("/read/{idx}")
    public ModelAndView readGet(@PathVariable("idx") Integer idx, ModelAndView mnv) {
        log.info("읽고자하는 글 번호 : {}", idx);

        try {
            boardService.updateHits(idx);
            mnv.addObject("article", boardService.getArticle(idx));
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
        mnv.setViewName("board/read");
        return mnv;
    }

    @GetMapping("/update/{idx}")
    public ModelAndView updateGet(@PathVariable("idx") Integer idx, ModelAndView mnv) {
        log.info("수정하고자하는 글 번호 : {}", idx);

        try {
            mnv.addObject("article", boardService.getArticle(idx));
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
        mnv.setViewName("board/update");
        return mnv;
    }
}
