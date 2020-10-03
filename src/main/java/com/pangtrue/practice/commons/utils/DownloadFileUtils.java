package com.pangtrue.practice.commons.utils;

import com.pangtrue.practice.commons.exception.ResourceNotFoundException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * User: SeungHo Lee (seung7642@neowiz.com)
 * Date: 2020. 2. 6.
 * Time: 오전 9:50
 */
public class DownloadFileUtils {

    private static final String UPLOAD_PATH = "/neowiz-data/board";

    /**
     * 브라우저(Chrome, IE, Edge)에 따른 이름 처리에 따라 다운로드할 파일명을 가져온다.
     *
     * @param userAgent
     * @param resourceName
     * @return String
     * @throws UnsupportedEncodingException
     */
    public static String getDownloadName(String userAgent, String resourceName) throws UnsupportedEncodingException {
        String downloadName = null;

        if (userAgent.contains("Trident")) { // IE browser
            downloadName = URLEncoder.encode(resourceName, "UTF-8");
        } else if (userAgent.contains("Edge")) { // Edge browser
            downloadName = URLEncoder.encode(resourceName, "UTF-8");
        } else { // Chrome browser
            downloadName = new String(resourceName.getBytes("UTF-8"), "ISO-8859-1");
        }
        return downloadName;
    }

    /**
     * 파라미터로 넘어온 헤더이름-헤더값으로 세팅된 HttpHeaders 값을 반환한다.
     *
     * @param headerName
     * @param headerValue
     * @return HttpHeaders
     */
    public static HttpHeaders getHttpHeaders(String[] headerName, String[] headerValue) {
        HttpHeaders headers = new HttpHeaders();

        for (int i = 0; i<headerName.length; ++i) {
            headers.add(headerName[i], headerValue[i]);
        }
        return headers;
    }

    /**
     * 파라미터로 넘어온 filaName 값을 가지고 Resource 객체를 만든 후 반환한다.
     *
     * @param fileName
     * @return Resource
     * @throws ResourceNotFoundException
     */
    public static Resource getResource(String fileName) throws ResourceNotFoundException {
        Resource resource = new FileSystemResource(UPLOAD_PATH + "/" + fileName);
        if (!resource.exists()) {
            throw new ResourceNotFoundException();
        }
        return resource;
    }
}
