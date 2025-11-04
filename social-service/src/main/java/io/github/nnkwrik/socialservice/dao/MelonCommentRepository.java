package io.github.nnkwrik.socialservice.dao;

import io.github.nnkwrik.socialservice.model.po.MelonComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MelonCommentRepository extends JpaRepository<MelonComment, Integer> {

    List<MelonComment> findByMelonIdAndDeletedOrderByCreateTimeDesc(Integer melonId, Integer deleted);

    long countByMelonIdAndDeleted(Integer melonId, Integer deleted);
}