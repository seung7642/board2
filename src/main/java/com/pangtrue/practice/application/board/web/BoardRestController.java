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
        try {
            boardService.insertArticle(board);
            return new ResponseEntity<>(board, HttpStatus.OK);
        } catch (NotValidException ex) {
            log.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Board> updatePut(@Valid @RequestBody Board board) {
        try {
            boardService.updateArticle(board);
            return new ResponseEntity<>(board, HttpStatus.OK);
        } catch (NotValidException ex) {
            log.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{idx}")
    public ResponseEntity<Board> delete(@PathVariable("idx") Integer idx) {
        try {
            Board board = boardService.getArticle(idx);
            boardService.deleteArticle(idx);
            return new ResponseEntity<>(board, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
