package io.github.nnkwrik.socialservice.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MelonCommentVo {
    private Integer id;
    private Integer melonId;
    private Integer userId;
    private String username;
    private String avatar;
    private String content;
    private Integer likeCount;
    private LocalDateTime createTime;
    private Boolean liked;
}