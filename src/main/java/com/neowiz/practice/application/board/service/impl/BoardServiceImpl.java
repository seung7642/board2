package com.neowiz.practice.application.board.service.impl;

import com.neowiz.practice.application.board.dao.BoardAttachMapper;
import com.neowiz.practice.commons.utils.PreconditionUtils;
import com.neowiz.practice.application.board.dao.BoardMapper;
import com.neowiz.practice.application.board.domain.Board;
import com.neowiz.practice.application.board.domain.PageMaker;
import com.neowiz.practice.application.board.service.BoardService;
import com.neowiz.practice.commons.exception.NotValidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final BoardAttachMapper attachMapper;

    // 캡처1 : <
    // 캡처2 : br을 제외한 문자
    // 캡처3 : >
    private static final String XSS_FILTER_REGEX = "([&]lt[;])([^br][/a-zA-Z]+)([&]gt[;])";

    @Override
    public Board getArticle(Integer idx) {
        return boardMapper.getArticle(idx);
    }

    @Override
    public PageMaker<Board> getArticleList(Pageable pageable) {
        PageMaker<Board> pageMaker = new PageMaker<>(
                boardMapper.findAll(boardMapper.getLastIdx() - (pageable.getPageNumber() - 1) * pageable.getPageSize() + 1),
                pageable,
                boardMapper.getArticleCount()
        );
        return pageMaker;
    }

    @Override
    public int getArticleCount() { return boardMapper.getArticleCount(); }

    @Transactional
    @Override
    public boolean insertArticle(Board board) throws NotValidException {
        validationCheck(board);
        preventXss(board);

        boardMapper.insertArticle(board);
        boardMapper.plusArticleCount();

        if (board.getAttach().getUuid() != null) { // 파일 데이터가 있다면
            board.getAttach().setBoardIdx(board.getIdx());
            attachMapper.insert(board.getAttach());
        }

        log.info("INSERT success");
        return true;
    }

    @Transactional
    @Override
    public void deleteArticle(Integer idx) {
        boardMapper.deleteArticle(idx);
        boardMapper.minusArticleCount();
        log.info("DELETE success");
    }

    @Override
    public void updateArticle(Board board) {
        validationCheck(board);
        preventXss(board);

        boardMapper.updateArticle(board);
        log.info("UPDATE success");
    }

    @Override
    public void updateHits(Integer idx) {
        boardMapper.updateHits(idx);
        log.info("조회수 +1");
    }

    private void validationCheck(Board board) {
        PreconditionUtils.invalidCondition(board == null, "입력할 정보가 없습니다 !");
        PreconditionUtils.invalidCondition(StringUtils.isEmpty(board.getTitle()), "제목을 입력해주세요 !");
        PreconditionUtils.invalidCondition(StringUtils.isEmpty(board.getContent()), "내용을 입력해주세요 !");
    }

    private void preventXss(Board board) {
//        board.setContent(board.getContent().replaceAll(XSS_FILTER_REGEX, "<>"));
        board.setContent(preventXss(board.getContent()));
    }

    private String preventXss(String checkString) {
        if (checkString != null) {
            StringBuffer strBuff = new StringBuffer();

            for (int i = 0; i < checkString.length(); i++) {
                char c = checkString.charAt(i);
                switch (c) {
                    case '<':
                        strBuff.append("&lt;");
                        break;
                    case '>':
                        strBuff.append("&gt;");
                        break;
                    case '&':
                        strBuff.append("&amp;");
                        break;
                    case '"':
                        strBuff.append("&quot;");
                        break;
                    case '\'':
                        strBuff.append("&apos;");
                        break;
                    default:
                        strBuff.append(c);
                        break;
                }
            }
            checkString = strBuff.toString();
        } else {
            checkString = null;
        }
        return checkString;
    }
}
