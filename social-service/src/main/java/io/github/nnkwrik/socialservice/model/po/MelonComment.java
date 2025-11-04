package io.github.nnkwrik.socialservice.model.po;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "melon_comment")
public class MelonComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer melonId;

    @Column(nullable = false, length = 50)
    private String openId;

    @Column(length = 50)
    private String username;

    @Column(length = 100)
    private String avatar;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer likeCount;

    @CreationTimestamp
    private LocalDateTime createTime;

    @Column(nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private Integer deleted;
}