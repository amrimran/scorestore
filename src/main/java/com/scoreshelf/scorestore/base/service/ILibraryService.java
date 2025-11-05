package com.scoreshelf.scorestore.base.service;

import com.scoreshelf.scorestore.base.entity.MusicScore;
import java.util.List;

public interface ILibraryService {

    boolean addMusicScoreToLibrary(String userId, String musicScoreId);

    boolean removeMusicScoreFromLibrary(String userId, String musicScoreId);

    List<MusicScore> getMusicScoresByUserId(String userId);

    boolean isMusicScoreOwnedByUser(String userId, String musicScoreId);
}