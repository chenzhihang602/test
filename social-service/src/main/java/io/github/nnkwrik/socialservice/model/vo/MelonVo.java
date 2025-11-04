package io.github.nnkwrik.socialservice.model.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MelonVo {
    private Integer id;
    private String title;
    private String content;
    private Integer userId;
    private String username;
    private String avatar;
    private Integer likeCount;
    private Integer commentCount;
    private String category;
    private LocalDateTime createTime;
    private Boolean liked;
    private List<MelonCommentVo> comments;
}