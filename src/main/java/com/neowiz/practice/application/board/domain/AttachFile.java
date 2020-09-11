package com.neowiz.practice.application.board.domain;

import lombok.Data;

/**
 * User: SeungHo Lee (seung7642@neowiz.com)
 * Date: 2020. 2. 11.
 * Time: 오전 9:50
 */
@Data
public class AttachFile {

    private String uuid;
    private String uploadPath;
    private String fileName;
    private boolean image;

    public AttachFile() {
        this.image = false;
    }
}
