package com.scoreshelf.scorestore.base.service;

import com.scoreshelf.scorestore.base.entity.MusicScore;
import java.util.List;

public interface IMusicScoreService {
    List<MusicScore> getAllMusicScores();
    List<MusicScore> getMusicScoresNotOwnedByUser(List<String> ownedScoreIds);
    List<MusicScore> getMusicScoresByIds(List<String> ids);
    MusicScore getMusicScoreById(String id);
    void updateMusicScore(MusicScore musicScore); 
}






