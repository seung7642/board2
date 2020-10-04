package com.pangtrue.practice.application.board.web;

import com.pangtrue.practice.application.board.domain.AttachFile;
import com.pangtrue.practice.application.board.domain.BoardAttach;
import com.pangtrue.practice.application.board.service.UploadDownloadService;
import com.pangtrue.practice.commons.exception.ResourceNotFoundException;
import com.pangtrue.practice.commons.utils.DownloadFileUtils;
import com.pangtrue.practice.commons.utils.UploadFileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * User: SeungHo Lee (seung7642@gmail.com)
 * Date: 2020. 2. 6.
 * Time: 오전 9:50
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class UploadDownloadRestController {

    private final UploadDownloadService uploadDownloadService;

    @PostMapping("/upload")
    public ResponseEntity<AttachFile> upload(MultipartFile uploadFile) {
        try {
            return new ResponseEntity<>(UploadFileUtils.uploadFile(uploadFile), HttpStatus.CREATED);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> display(@RequestParam("filename") String fileName) {
        HttpHeaders headers = new HttpHeaders();

        try {
            headers.add("Content-Type", UploadFileUtils.getMimeType(fileName));
            return new ResponseEntity<>(UploadFileUtils.displayFile(fileName), headers, HttpStatus.OK);
        } catch (IOException ex) {
            log.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> download(@RequestHeader("User-Agent") String userAgent,
                                             @RequestParam("filename") String fileName) {
        try {
            Resource resource = DownloadFileUtils.getResource(fileName);
            String[] headerName = { "Content-Disposition" };
            String[] headerValue = { "attachment; filename=" + DownloadFileUtils.getDownloadName(userAgent, resource.getFilename()) };
            HttpHeaders headers = DownloadFileUtils.getHttpHeaders(headerName, headerValue);
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (UnsupportedEncodingException ex) {
            log.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException ex) {
            log.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<BoardAttach>> getAttachList(int boardIdx) {
        return new ResponseEntity<>(uploadDownloadService.getAttachList(boardIdx), HttpStatus.OK);
    }
}
