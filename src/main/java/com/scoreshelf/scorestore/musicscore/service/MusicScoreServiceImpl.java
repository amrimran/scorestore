package com.scoreshelf.scorestore.musicscore.service;

import com.scoreshelf.scorestore.base.service.IMusicScoreService;
import com.scoreshelf.scorestore.base.entity.MusicScore;
import com.scoreshelf.scorestore.base.repository.MusicScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicScoreServiceImpl implements IMusicScoreService {

    @Autowired
    private MusicScoreRepository musicScoreRepository;

    @Override
    public List<MusicScore> getAllMusicScores() {
        return musicScoreRepository.findAll();
    }

    @Override
    public List<MusicScore> getMusicScoresNotOwnedByUser(List<String> ownedScoreIds) {
        if (ownedScoreIds == null || ownedScoreIds.isEmpty()) {
            return musicScoreRepository.findAll();
        }
        return musicScoreRepository.findMusicScoresNotOwnedByUser(ownedScoreIds);
    }

    @Override
    public List<MusicScore> getMusicScoresByIds(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
           
            return List.of(); 
        }
        
        List<MusicScore> scores = musicScoreRepository.findByIdIn(ids);
        return scores;
    }

    @Override
    public MusicScore getMusicScoreById(String id) {
        return musicScoreRepository.findById(id).orElse(null);
    }

    @Override
    public void updateMusicScore(MusicScore musicScore) {
        musicScoreRepository.save(musicScore); 
    }
}