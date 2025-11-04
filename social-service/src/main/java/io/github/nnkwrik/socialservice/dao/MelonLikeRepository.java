package io.github.nnkwrik.socialservice.dao;

import io.github.nnkwrik.socialservice.model.po.MelonLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MelonLikeRepository extends JpaRepository<MelonLike, Integer> {

    boolean existsByMelonIdAndOpenId(Integer melonId, String openId);

    void deleteByMelonIdAndOpenId(Integer melonId, String openId);

    long countByMelonId(Integer melonId);

    List<MelonLike> findByOpenId(String openId);
}