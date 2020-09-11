package com.neowiz.practice.application.board.service;

import com.neowiz.practice.application.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// Exception catch는 맨 끝에서만 잡기 ! (여기서는 컨트롤러가 맨 끝단에 속한다.)
public interface BoardService {

    /**
     * 게시글 1개를 가져온다.
     *
     * @param idx
     * @return Board
     */
    Board getArticle(Integer idx);

    /**
     * 전체 게시글을 가져온다.
     *
     * @Param pageable
     * @return Page<Board>
     */
    Page<Board> getArticleList(Pageable pageable);

    /**
     * 전체 게시글의 갯수를 가져온다.
     *
     * @param
     * @return int
     */
    int getArticleCount();

    /**
     * 게시글 하나를 생성한다.
     *
     * @param board
     * @return boolean
     */
    boolean insertArticle(Board board);

    /**
     * 게시글 하나를 삭제한다.
     *
     * @param idx
     * @return void
     */
    void deleteArticle(Integer idx);

    /**
     * 게시글 하나를 수정한다.
     *
     * @param board
     * @return void
     */
    void updateArticle(Board board);

    /**
     * 조회수를 업데이트한다.
     *
     * @param idx
     * @return void
     */
    void updateHits(Integer idx);
}
