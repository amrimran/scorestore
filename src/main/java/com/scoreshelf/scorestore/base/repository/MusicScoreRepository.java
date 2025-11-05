package com.scoreshelf.scorestore.base.repository;

import com.scoreshelf.scorestore.base.entity.MusicScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicScoreRepository extends JpaRepository<MusicScore, String> {
    @Query("SELECT m FROM MusicScore m WHERE :ownedScoreIds IS NULL OR m.id NOT IN :ownedScoreIds")
    List<MusicScore> findMusicScoresNotOwnedByUser(@Param("ownedScoreIds") List<String> ownedScoreIds);
   
    @Query("SELECT m FROM MusicScore m WHERE m.id IN :ids")
    List<MusicScore> findByIdIn(@Param("ids") List<String> ids);
}

