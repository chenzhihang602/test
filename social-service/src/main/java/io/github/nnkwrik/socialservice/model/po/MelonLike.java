package io.github.nnkwrik.socialservice.model.po;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "melon_like", uniqueConstraints = {@UniqueConstraint(columnNames = {"melon_id", "open_id"})})
public class MelonLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "melon_id", nullable = false)
    private Integer melonId;

    @Column(name = "open_id", nullable = false, length = 50)
    private String openId;

    private LocalDateTime createTime;
}