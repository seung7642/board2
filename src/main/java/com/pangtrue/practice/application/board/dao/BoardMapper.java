package com.pangtrue.practice.application.board.dao;

import com.pangtrue.practice.application.board.domain.Board;
import com.pangtrue.practice.application.board.domain.Criteria;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: SeungHo Lee (seung7642@gmail.com)
 * Date: 2020. 2. 11.
 * Time: 오전 9:50
 */
@Repository
@Mapper
public interface BoardMapper { //  /mappers/sql-board.xml 파일에서 해당 클래스와 연결시켜놓았기 때문에 <select id="getArticle" ...> 을 사용할 수 있는 것.

    /**
     * 메세지를 가져온다.
     *
     * @param idx
     * @return Board
     */
    Board getArticle(Integer idx);

    /**
     * 전체 게시글을 가져온다.
     *
     * @param criteria
     * @return List<Board>
     */
    List<Board> getArticleList(Criteria criteria);

    /**
     * 전체 게시글을 가져온다.
     *
     * @param
     * @return List<Board>
     */
    List<Board> findAll(Integer idx);

    /**
     * 마지막 행의 idx값을 가져온다.
     *
     * @return int
     */
    Integer getLastIdx();

    /**
     * 전체 게시글의 갯수를 가져온다.
     *
     * @param
     * @return int
     */
    Integer getArticleCount();

    /**
     * 게시글 하나를 생성한다.
     *
     * @param board
     * @return int
     */
    Integer insertArticle(Board board);

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

    /**
     * 게시글 갯수를 +1 한다.
     *
     * @return void
     */
    void plusArticleCount();

    /**
     * 게시글 갯수를 -1 한다.
     *
     * @return void
     */
    void minusArticleCount();
}
