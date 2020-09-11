package com.neowiz.practice.application.board.web;

import com.neowiz.practice.application.board.domain.Board;
import com.neowiz.practice.application.board.service.BoardService;
import com.neowiz.practice.commons.exception.NotValidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * User: SeungHo Lee (seung7642@neowiz.com)
 * Date: 2020. 1. 20.
 * Time: 오전 9:50
 */
@Slf4j
@RestController
@RequestMapping(value = "/board")
public class BoardRestController {

    private final BoardService boardService;

    @Autowired
    public BoardRestController(BoardService boardService) { this.boardService = boardService; }

    // 유효성 검증에 실패하면 Spring Boot는 MethodArgumentNotValidException 예외를 던진다.
    @PostMapping(value = "/write")
    public ResponseEntity<Board> writePost(@Valid @RequestBody Board board) {
        log.info("INSERT ajax로 넘어온 데이터 : {}", board.toString());
        log.info("넘어온 attach 객체 정보 : {}", board.getAttach());
        ResponseEntity<Board> entity = null;

        try {
            boardService.insertArticle(board);
            entity = new ResponseEntity<>(board, HttpStatus.OK);
        } catch (NotValidException e) {
            log.debug(e.getMessage(), e);
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Board> updatePut(@Valid @RequestBody Board board) {
        log.info("UPDATE ajax로 넘어온 데이터 : {}", board.toString());
        ResponseEntity<Board> entity = null;

        try {
            boardService.updateArticle(board);
            entity = new ResponseEntity<>(board, HttpStatus.OK);
        } catch (NotValidException e) {
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Board> delete(@RequestBody Board board) {
        log.info("delete로 들어온 idx : {}", board.getIdx());
        ResponseEntity<Board> entity = null;

        try {
            Board retBoard = boardService.getArticle(board.getIdx());
            boardService.deleteArticle(board.getIdx());
            entity = new ResponseEntity<>(board, HttpStatus.OK);
        } catch (Exception e) {
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("유효성 검증 실패 예외 발생 : {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            errors.put(fieldName, errorMessage); // [ KEY : 유효성 검증에 실패한 필드, VALUE : 에러 메시지 ]
        });

        return errors;
    }
}
