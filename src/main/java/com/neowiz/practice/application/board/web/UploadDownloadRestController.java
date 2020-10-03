package com.neowiz.practice.application.board.web;

import com.neowiz.practice.application.board.domain.AttachFile;
import com.neowiz.practice.application.board.domain.BoardAttach;
import com.neowiz.practice.application.board.service.UploadDownloadService;
import com.neowiz.practice.commons.exception.ResourceNotFoundException;
import com.neowiz.practice.commons.utils.DownloadFileUtils;
import com.neowiz.practice.commons.utils.UploadFileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * User: SeungHo Lee (seung7642@neowiz.com)
 * Date: 2020. 2. 6.
 * Time: 오전 9:50
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/board")
public class UploadDownloadRestController {

    private final UploadDownloadService uploadDownloadService;

    @PostMapping(value = "/upload")
    public ResponseEntity<AttachFile> upload(MultipartFile uploadFile) {
        ResponseEntity<AttachFile> entity = null;

        try {
            entity = new ResponseEntity<>(UploadFileUtils.uploadFile(uploadFile), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("파일첨부 작업 중 예외 발생 : {}", e.getMessage());
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @GetMapping(value = "/display")
    public ResponseEntity<byte[]> display(@RequestParam("filename") String fileName) {
        log.info("브라우저에 노출시킬 이미지 파일명 : {}", fileName);
        ResponseEntity<byte[]> entity = null;
        HttpHeaders headers = new HttpHeaders();

        try {
            headers.add("Content-Type", UploadFileUtils.getMimeType(fileName));
            entity = new ResponseEntity<>(UploadFileUtils.displayFile(fileName), headers, HttpStatus.OK);
        } catch (IOException e) {
            log.debug(e.getMessage(), e);
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> download(@RequestHeader("User-Agent") String userAgent,
                                             @RequestParam("filename") String fileName) {
        log.info("다운로드할 파일명 : {}", fileName);
        ResponseEntity<Resource> entity = null;

        try {
            Resource resource = DownloadFileUtils.getResource(fileName);
            String[] headerName = { "Content-Disposition" };
            String[] headerValue = { "attachment; filename=" + DownloadFileUtils.getDownloadName(userAgent, resource.getFilename()) };
            HttpHeaders headers = DownloadFileUtils.getHttpHeaders(headerName, headerValue);
            entity = new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (UnsupportedEncodingException e) {
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            entity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<BoardAttach>> getAttachList(int boardIdx) {
        return new ResponseEntity<>(uploadDownloadService.getAttachList(boardIdx), HttpStatus.OK);
    }
}
