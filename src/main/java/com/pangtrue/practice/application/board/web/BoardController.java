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
    public ModelAndView list(@PageableDefault(size = 10, page = 1) Pageable pageable, ModelAndView mnv) {
        try {
            mnv.addObject("boardList", boardService.getArticleList(pageable));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
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
        try {
            boardService.updateHits(idx);
            mnv.addObject("article", boardService.getArticle(idx));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        mnv.setViewName("board/read");
        return mnv;
    }

    @GetMapping("/update/{idx}")
    public ModelAndView updateGet(@PathVariable("idx") Integer idx, ModelAndView mnv) {
        try {
            mnv.addObject("article", boardService.getArticle(idx));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        mnv.setViewName("board/update");
        return mnv;
    }
}
