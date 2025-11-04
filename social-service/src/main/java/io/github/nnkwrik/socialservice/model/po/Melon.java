package io.github.nnkwrik.socialservice.model.po;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "melon")
public class Melon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, length = 50)
    private String openId;

    @Column(length = 50)
    private String username;

    @Column(length = 100)
    private String avatar;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer likeCount;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer commentCount;

    @Column(length = 20)
    private String category;

    @CreationTimestamp
    private LocalDateTime createTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;

    @Column(nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private Integer deleted;
}