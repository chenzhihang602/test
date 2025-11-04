package io.github.nnkwrik.socialservice.controller;

import io.github.nnkwrik.common.dto.Response;
import io.github.nnkwrik.socialservice.model.po.Melon;
import io.github.nnkwrik.socialservice.model.po.MelonComment;
import io.github.nnkwrik.socialservice.model.vo.MelonVo;
import io.github.nnkwrik.socialservice.model.vo.MelonCommentVo;
import io.github.nnkwrik.socialservice.service.MelonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/social-service/melons")
public class MelonController {

    @Autowired
    private MelonService melonService;

    // 创建瓜
    @PostMapping
    public Response<MelonVo> createMelon(@RequestBody Melon melon) {
        MelonVo createdMelon = melonService.createMelon(melon);
        if (createdMelon != null) {
            return Response.success(createdMelon, "创建成功");
        } else {
            return Response.fail("创建失败");
        }
    }

    // 根据ID获取瓜
    @GetMapping("/{id}")
    public Response<MelonVo> getMelonById(@PathVariable Integer id, @RequestParam(required = false) String openId) {
        MelonVo melon = melonService.getMelonById(id, openId);
        if (melon != null) {
            return Response.success(melon, "获取成功");
        } else {
            return Response.fail("瓜不存在或已删除");
        }
    }

    // 获取所有瓜
    @GetMapping
    public Response<List<MelonVo>> getAllMelons(@RequestParam(required = false) String openId) {
        List<MelonVo> melons = melonService.getAllMelons(openId);
        return Response.success(melons, "获取成功");
    }

    // 根据分类获取瓜
    @GetMapping("/category/{category}")
    public Response<List<MelonVo>> getMelonsByCategory(@PathVariable String category, @RequestParam(required = false) String openId) {
        List<MelonVo> melons = melonService.getMelonsByCategory(category, openId);
        return Response.success(melons, "获取成功");
    }

    // 搜索瓜
    @GetMapping("/search")
    public Response<List<MelonVo>> searchMelons(@RequestParam String keyword, @RequestParam(required = false) String openId) {
        List<MelonVo> melons = melonService.searchMelons(keyword, openId);
        return Response.success(melons, "搜索成功");
    }

    // 更新瓜
    @PutMapping("/{id}")
    public Response<MelonVo> updateMelon(@PathVariable Integer id, @RequestBody Melon melon) {
        MelonVo updatedMelon = melonService.updateMelon(id, melon);
        if (updatedMelon != null) {
            return Response.success(updatedMelon, "更新成功");
        } else {
            return Response.fail("更新失败，瓜不存在或已删除");
        }
    }

    // 删除瓜
    @DeleteMapping("/{id}")
    public Response<Void> deleteMelon(@PathVariable Integer id) {
        melonService.deleteMelon(id);
        return Response.success(null, "删除成功");
    }

    // 创建评论
    @PostMapping("/{melonId}/comments")
    public Response<MelonCommentVo> createComment(@PathVariable Integer melonId, @RequestBody MelonComment comment) {
        comment.setMelonId(melonId);
        MelonCommentVo createdComment = melonService.createComment(comment);
        if (createdComment != null) {
            return Response.success(createdComment, "评论成功");
        } else {
            return Response.fail("评论失败");
        }
    }

    // 获取评论列表
    @GetMapping("/{melonId}/comments")
    public Response<List<MelonCommentVo>> getCommentsByMelonId(@PathVariable Integer melonId, @RequestParam(required = false) String openId) {
        List<MelonCommentVo> comments = melonService.getCommentsByMelonId(melonId, openId);
        return Response.success(comments, "获取成功");
    }

    // 更新评论
    @PutMapping("/comments/{id}")
    public Response<MelonCommentVo> updateComment(@PathVariable Integer id, @RequestBody MelonComment comment) {
        MelonCommentVo updatedComment = melonService.updateComment(id, comment);
        if (updatedComment != null) {
            return Response.success(updatedComment, "更新成功");
        } else {
            return Response.fail("更新失败，评论不存在或已删除");
        }
    }

    // 删除评论
    @DeleteMapping("/comments/{id}")
    public Response<Void> deleteComment(@PathVariable Integer id) {
        melonService.deleteComment(id);
        return Response.success(null, "删除成功");
    }

    // 点赞瓜
    @PostMapping("/{melonId}/like")
    public Response<Void> likeMelon(@PathVariable Integer melonId, @RequestParam String openId) {
        boolean success = melonService.likeMelon(melonId, openId);
        if (success) {
            return Response.success(null, "点赞成功");
        } else {
            return Response.fail("已经点赞过了");
        }
    }

    // 取消点赞瓜
    @DeleteMapping("/{melonId}/like")
    public Response<Void> unlikeMelon(@PathVariable Integer melonId, @RequestParam String openId) {
        boolean success = melonService.unlikeMelon(melonId, openId);
        if (success) {
            return Response.success(null, "取消点赞成功");
        } else {
            return Response.fail("还没有点赞过");
        }
    }

    // 检查是否点赞
    @GetMapping("/{melonId}/like/status")
    public Response<Boolean> isMelonLiked(@PathVariable Integer melonId, @RequestParam String openId) {
        boolean liked = melonService.isMelonLiked(melonId, openId);
        return Response.success(liked, "获取成功");
    }

    // 获取点赞数
    @GetMapping("/{melonId}/like/count")
    public Response<Long> getMelonLikeCount(@PathVariable Integer melonId) {
        long count = melonService.getMelonLikeCount(melonId);
        return Response.success(count, "获取成功");
    }

    // 点赞评论
    @PostMapping("/comments/{commentId}/like")
    public Response<Void> likeComment(@PathVariable Integer commentId, @RequestParam String openId) {
        boolean success = melonService.likeComment(commentId, openId);
        if (success) {
            return Response.success(null, "点赞成功");
        } else {
            return Response.fail("已经点赞过了");
        }
    }

    // 取消点赞评论
    @DeleteMapping("/comments/{commentId}/like")
    public Response<Void> unlikeComment(@PathVariable Integer commentId, @RequestParam String openId) {
        boolean success = melonService.unlikeComment(commentId, openId);
        if (success) {
            return Response.success(null, "取消点赞成功");
        } else {
            return Response.fail("还没有点赞过");
        }
    }

    // 检查评论是否被点赞
    @GetMapping("/comments/{commentId}/like/status")
    public Response<Boolean> isCommentLiked(@PathVariable Integer commentId, @RequestParam String openId) {
        boolean liked = melonService.isCommentLiked(commentId, openId);
        return Response.success(liked, "获取成功");
    }

    // 获取评论点赞数
    @GetMapping("/comments/{commentId}/like/count")
    public Response<Long> getCommentLikeCount(@PathVariable Integer commentId) {
        long count = melonService.getCommentLikeCount(commentId);
        return Response.success(count, "获取成功");
    }
}