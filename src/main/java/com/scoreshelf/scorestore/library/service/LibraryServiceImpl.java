package com.scoreshelf.scorestore.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scoreshelf.scorestore.base.entity.MusicScore;
import com.scoreshelf.scorestore.base.entity.User;
import com.scoreshelf.scorestore.base.repository.MusicScoreRepository;
import com.scoreshelf.scorestore.base.repository.UserRepository;
import com.scoreshelf.scorestore.base.service.ILibraryService;

@Service
public class LibraryServiceImpl implements ILibraryService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MusicScoreRepository musicScoreRepository;
    @Override
    public boolean addMusicScoreToLibrary(String userId, String musicScoreId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<MusicScore> musicScoreOptional = musicScoreRepository.findById(musicScoreId);

        if (userOptional.isPresent() && musicScoreOptional.isPresent()) {
            User user = userOptional.get();

            user.getOwnedScoreIds().add(musicScoreId);
            userRepository.save(user);

            return true;
        }
        return false;
    }

    @Override
    public boolean removeMusicScoreFromLibrary(String userId, String musicScoreId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            user.getOwnedScoreIds().remove(musicScoreId);
            userRepository.save(user);

            return true;
        }
        return false;
    }

    @Override
    public List<MusicScore> getMusicScoresByUserId(String userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Fetch all music scores owned by the user
            return musicScoreRepository.findAllById(user.getOwnedScoreIds());
        }
        return List.of(); // Return an empty list if the user is not found
    }

    @Override
    public boolean isMusicScoreOwnedByUser(String userId, String musicScoreId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Check if the user owns the music score
            return user.getOwnedScoreIds().contains(musicScoreId);
        }
        return false;
    }
}