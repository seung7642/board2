package com.pangtrue.practice.application.board.web;

import com.pangtrue.practice.application.board.domain.Board;
import com.pangtrue.practice.application.board.service.BoardService;
import com.pangtrue.practice.commons.exception.NotValidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * User: SeungHo Lee (seung7642@gmail.com)
 * Date: 2020. 1. 20.
 * Time: 오전 9:50
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardRestController {

    private final BoardService boardService;

    // 유효성 검증에 실패하면 Spring Boot는 MethodArgumentNotValidException 예외를 던진다.
    @PostMapping("/write")
    public ResponseEntity<Board> writePost(@Valid @RequestBody Board board) {
        log.info("INSERT ajax로 넘어온 데이터 : {}", board.toString());
        log.info("넘어온 attach 객체 정보 : {}", board.getAttach());
        ResponseEntity<Board> entity;

        try {
            boardService.insertArticle(board);
            entity = new ResponseEntity<>(board, HttpStatus.OK);
        } catch (NotValidException e) {
            log.debug(e.getMessage(), e);
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @PutMapping("/update")
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

    @DeleteMapping("/delete/{idx}")
    public ResponseEntity<Board> delete(@PathVariable("idx") Integer idx) {
        ResponseEntity<Board> entity;

        try {
            Board board = boardService.getArticle(idx);
            boardService.deleteArticle(idx);
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
