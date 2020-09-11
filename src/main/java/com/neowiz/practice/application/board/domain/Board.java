package com.neowiz.practice.application.board.domain;

import lombok.Data;
import lombok.NonNull;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/**
 * User: SeungHo Lee (seung7642@neowiz.com)
 * Date: 2020. 1. 10.
 * Time: 오전 9:50
 */
@Alias("Board")
@Data // [ @RequiredArgsConstructor, @Getter, @Setter, @ToString, @EqualsAndHashCode ]
public class Board {
    private int idx;
    @NotBlank(message = "Title is mandatory !")
    @Length(max = 1024)
    private String title;
    @NotBlank(message = "Content is mandatory !")
    @Length(max = 1024 * 1024)
    private String content;
    private String writer;
    private Date regDate;
    private Date updDate;
    private int hits;

    private BoardAttach attach;
}
