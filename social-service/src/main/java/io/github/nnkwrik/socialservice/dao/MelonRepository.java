package io.github.nnkwrik.socialservice.dao;

import io.github.nnkwrik.socialservice.model.po.Melon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MelonRepository extends JpaRepository<Melon, Integer> {

    List<Melon> findByDeletedOrderByCreateTimeDesc(Integer deleted);

    List<Melon> findByCategoryAndDeletedOrderByCreateTimeDesc(String category, Integer deleted);

    @Query("SELECT m FROM Melon m WHERE m.deleted = 0 AND (m.title LIKE %:keyword% OR m.content LIKE %:keyword%) ORDER BY m.createTime DESC")
    List<Melon> searchMelons(@Param("keyword") String keyword);
}