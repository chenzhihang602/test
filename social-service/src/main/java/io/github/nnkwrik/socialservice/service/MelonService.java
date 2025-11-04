package io.github.nnkwrik.socialservice.service;

import io.github.nnkwrik.socialservice.model.po.Melon;
import io.github.nnkwrik.socialservice.model.po.MelonComment;
import io.github.nnkwrik.socialservice.model.vo.MelonVo;
import io.github.nnkwrik.socialservice.model.vo.MelonCommentVo;

import java.util.List;

public interface MelonService {

    // 瓜的相关操作
    MelonVo createMelon(Melon melon);

    MelonVo getMelonById(Integer id, String openId);

    List<MelonVo> getAllMelons(String openId);

    List<MelonVo> getMelonsByCategory(String category, String openId);

    List<MelonVo> searchMelons(String keyword, String openId);

    MelonVo updateMelon(Integer id, Melon melon);

    void deleteMelon(Integer id);

    // 评论的相关操作
    MelonCommentVo createComment(MelonComment comment);

    List<MelonCommentVo> getCommentsByMelonId(Integer melonId, String openId);

    MelonCommentVo updateComment(Integer id, MelonComment comment);

    void deleteComment(Integer id);

    // 点赞的相关操作
    boolean likeMelon(Integer melonId, String openId);

    boolean unlikeMelon(Integer melonId, String openId);

    boolean isMelonLiked(Integer melonId, String openId);

    long getMelonLikeCount(Integer melonId);

    // 评论点赞的相关操作
    boolean likeComment(Integer commentId, String openId);

    boolean unlikeComment(Integer commentId, String openId);

    boolean isCommentLiked(Integer commentId, String openId);

    long getCommentLikeCount(Integer commentId);
}