package io.github.nnkwrik.socialservice.service.impl;

import io.github.nnkwrik.common.dto.SimpleUser;
import io.github.nnkwrik.socialservice.dao.MelonRepository;
import io.github.nnkwrik.socialservice.dao.MelonCommentRepository;
import io.github.nnkwrik.socialservice.dao.MelonLikeRepository;
import io.github.nnkwrik.socialservice.model.po.Melon;
import io.github.nnkwrik.socialservice.model.po.MelonComment;
import io.github.nnkwrik.socialservice.model.po.MelonLike;
import io.github.nnkwrik.socialservice.model.vo.MelonVo;
import io.github.nnkwrik.socialservice.model.vo.MelonCommentVo;
import io.github.nnkwrik.socialservice.service.MelonService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MelonServiceImpl implements MelonService {

    @Autowired
    private MelonRepository melonRepository;

    @Autowired
    private MelonCommentRepository commentRepository;

    @Autowired
    private MelonLikeRepository likeRepository;

    // 这里需要注入用户服务的Feign客户端，用于获取用户信息
    // @Autowired
    // private UserClient userClient;

    @Override
    @Transactional
    public MelonVo createMelon(Melon melon) {
        melon.setLikeCount(0);
        melon.setCommentCount(0);
        melon.setDeleted(0);
        melon.setCreateTime(LocalDateTime.now());
        melon.setUpdateTime(LocalDateTime.now());

        // 这里需要调用用户服务获取用户信息
        // Response<SimpleUser> userResponse = userClient.getSimpleUser(melon.getOpenId());
        // if (userResponse.getCode() == 200 && userResponse.getData() != null) {
        //     SimpleUser user = userResponse.getData();
        //     melon.setUsername(user.getNickName());
        //     melon.setAvatar(user.getAvatarUrl());
        // } else {
        //     melon.setUsername("未知用户");
        //     melon.setAvatar("https://i.postimg.cc/RVbDV5fN/anonymous.png");
        // }

        // 暂时使用模拟数据
        melon.setUsername("测试用户");
        melon.setAvatar("https://i.postimg.cc/RVbDV5fN/anonymous.png");

        Melon savedMelon = melonRepository.save(melon);
        return convertToVo(savedMelon, null);
    }

    @Override
    public MelonVo getMelonById(Integer id, String openId) {
        Melon melon = melonRepository.findById(id).orElse(null);
        if (melon == null || melon.getDeleted() == 1) {
            return null;
        }
        return convertToVo(melon, openId);
    }

    @Override
    public List<MelonVo> getAllMelons(String openId) {
        List<Melon> melons = melonRepository.findByDeletedOrderByCreateTimeDesc(0);
        return melons.stream()
                .map(melon -> convertToVo(melon, openId))
                .collect(Collectors.toList());
    }

    @Override
    public List<MelonVo> getMelonsByCategory(String category, String openId) {
        List<Melon> melons = melonRepository.findByCategoryAndDeletedOrderByCreateTimeDesc(category, 0);
        return melons.stream()
                .map(melon -> convertToVo(melon, openId))
                .collect(Collectors.toList());
    }

    @Override
    public List<MelonVo> searchMelons(String keyword, String openId) {
        List<Melon> melons = melonRepository.searchMelons(keyword);
        return melons.stream()
                .map(melon -> convertToVo(melon, openId))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MelonVo updateMelon(Integer id, Melon melon) {
        Melon existingMelon = melonRepository.findById(id).orElse(null);
        if (existingMelon == null || existingMelon.getDeleted() == 1) {
            return null;
        }

        existingMelon.setTitle(melon.getTitle());
        existingMelon.setContent(melon.getContent());
        existingMelon.setCategory(melon.getCategory());
        existingMelon.setUpdateTime(LocalDateTime.now());

        Melon updatedMelon = melonRepository.save(existingMelon);
        return convertToVo(updatedMelon, null);
    }

    @Override
    @Transactional
    public void deleteMelon(Integer id) {
        Melon melon = melonRepository.findById(id).orElse(null);
        if (melon != null && melon.getDeleted() == 0) {
            melon.setDeleted(1);
            melon.setUpdateTime(LocalDateTime.now());
            melonRepository.save(melon);
        }
    }

    @Override
    @Transactional
    public MelonCommentVo createComment(MelonComment comment) {
        comment.setLikeCount(0);
        comment.setDeleted(0);
        comment.setCreateTime(LocalDateTime.now());

        // 这里需要调用用户服务获取用户信息
        // Response<SimpleUser> userResponse = userClient.getSimpleUser(comment.getOpenId());
        // if (userResponse.getCode() == 200 && userResponse.getData() != null) {
        //     SimpleUser user = userResponse.getData();
        //     comment.setUsername(user.getNickName());
        //     comment.setAvatar(user.getAvatarUrl());
        // } else {
        //     comment.setUsername("未知用户");
        //     comment.setAvatar("https://i.postimg.cc/RVbDV5fN/anonymous.png");
        // }

        // 暂时使用模拟数据
        comment.setUsername("测试用户");
        comment.setAvatar("https://i.postimg.cc/RVbDV5fN/anonymous.png");

        MelonComment savedComment = commentRepository.save(comment);

        // 更新瓜的评论数
        Melon melon = melonRepository.findById(comment.getMelonId()).orElse(null);
        if (melon != null) {
            melon.setCommentCount(melon.getCommentCount() + 1);
            melonRepository.save(melon);
        }

        return convertToCommentVo(savedComment, null);
    }

    @Override
    public List<MelonCommentVo> getCommentsByMelonId(Integer melonId, String openId) {
        List<MelonComment> comments = commentRepository.findByMelonIdAndDeletedOrderByCreateTimeDesc(melonId, 0);
        return comments.stream()
                .map(comment -> convertToCommentVo(comment, openId))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MelonCommentVo updateComment(Integer id, MelonComment comment) {
        MelonComment existingComment = commentRepository.findById(id).orElse(null);
        if (existingComment == null || existingComment.getDeleted() == 1) {
            return null;
        }

        existingComment.setContent(comment.getContent());
        MelonComment updatedComment = commentRepository.save(existingComment);
        return convertToCommentVo(updatedComment, null);
    }

    @Override
    @Transactional
    public void deleteComment(Integer id) {
        MelonComment comment = commentRepository.findById(id).orElse(null);
        if (comment != null && comment.getDeleted() == 0) {
            comment.setDeleted(1);
            commentRepository.save(comment);

            // 更新瓜的评论数
            Melon melon = melonRepository.findById(comment.getMelonId()).orElse(null);
            if (melon != null) {
                melon.setCommentCount(melon.getCommentCount() - 1);
                melonRepository.save(melon);
            }
        }
    }

    @Override
    @Transactional
    public boolean likeMelon(Integer melonId, String openId) {
        if (likeRepository.existsByMelonIdAndOpenId(melonId, openId)) {
            return false;
        }

        MelonLike like = new MelonLike();
        like.setMelonId(melonId);
        like.setOpenId(openId);
        like.setCreateTime(LocalDateTime.now());
        likeRepository.save(like);

        // 更新瓜的点赞数
        Melon melon = melonRepository.findById(melonId).orElse(null);
        if (melon != null) {
            melon.setLikeCount(melon.getLikeCount() + 1);
            melonRepository.save(melon);
        }

        return true;
    }

    @Override
    @Transactional
    public boolean unlikeMelon(Integer melonId, String openId) {
        if (!likeRepository.existsByMelonIdAndOpenId(melonId, openId)) {
            return false;
        }

        likeRepository.deleteByMelonIdAndOpenId(melonId, openId);

        // 更新瓜的点赞数
        Melon melon = melonRepository.findById(melonId).orElse(null);
        if (melon != null) {
            melon.setLikeCount(melon.getLikeCount() - 1);
            melonRepository.save(melon);
        }

        return true;
    }

    @Override
    public boolean isMelonLiked(Integer melonId, String openId) {
        return likeRepository.existsByMelonIdAndOpenId(melonId, openId);
    }

    @Override
    public long getMelonLikeCount(Integer melonId) {
        return likeRepository.countByMelonId(melonId);
    }

    // 评论点赞的相关操作
    @Override
    @Transactional
    public boolean likeComment(Integer commentId, String openId) {
        // 这里需要实现评论点赞的逻辑
        // 由于当前没有评论点赞的实体类，暂时返回false
        return false;
    }

    @Override
    @Transactional
    public boolean unlikeComment(Integer commentId, String openId) {
        // 这里需要实现取消评论点赞的逻辑
        // 由于当前没有评论点赞的实体类，暂时返回false
        return false;
    }

    @Override
    public boolean isCommentLiked(Integer commentId, String openId) {
        // 这里需要实现检查评论是否被点赞的逻辑
        // 由于当前没有评论点赞的实体类，暂时返回false
        return false;
    }

    @Override
    public long getCommentLikeCount(Integer commentId) {
        // 这里需要实现获取评论点赞数的逻辑
        // 由于当前没有评论点赞的实体类，暂时返回0
        return 0;
    }

    private MelonVo convertToVo(Melon melon, String openId) {
        MelonVo vo = new MelonVo();
        BeanUtils.copyProperties(melon, vo);
        vo.setLiked(openId != null && isMelonLiked(melon.getId(), openId));
        vo.setComments(new ArrayList<>());
        return vo;
    }

    private MelonCommentVo convertToCommentVo(MelonComment comment, String openId) {
        MelonCommentVo vo = new MelonCommentVo();
        BeanUtils.copyProperties(comment, vo);
        vo.setLiked(openId != null && isCommentLiked(comment.getId(), openId));
        return vo;
    }
}