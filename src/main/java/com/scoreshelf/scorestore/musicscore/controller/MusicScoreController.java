package com.scoreshelf.scorestore.musicscore.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scoreshelf.scorestore.base.entity.MusicScore;
import com.scoreshelf.scorestore.base.service.IMusicScoreService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MusicScoreController {

    @Autowired
    private IMusicScoreService musicScoreService;

    @GetMapping("/admin-manage-scores")
    public String adminManageScores(Model model, HttpSession session) {

        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        if (username == null || !"admin".equals(role)) {
            return "redirect:/login";
        }

        List<MusicScore> allMusicScores = musicScoreService.getAllMusicScores();

        model.addAttribute("musicScores", allMusicScores);

        return "admin/admin-manage-scores";
    }

    @GetMapping("/customer-music-score-view/{id}")
    public String viewMusicScore(@PathVariable String id, Model model, HttpSession session) {

        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        if (username == null || !"customer".equals(role)) {
            return "redirect:/login";
        }

        MusicScore musicScore = musicScoreService.getMusicScoreById(id);
        model.addAttribute("musicScore", musicScore);

        return "customer/customer-music-score-view";
    }

    @GetMapping("/admin-edit-score/{id}")
    public String adminEditScore(@PathVariable String id, Model model, HttpSession session) {

        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        if (username == null || !"admin".equals(role)) {
            return "redirect:/login";
        }

        MusicScore musicScore = musicScoreService.getMusicScoreById(id);
        model.addAttribute("musicScore", musicScore);

        return "admin/admin-edit-score";
    }

    @PutMapping("/update-score/{id}")
    @ResponseBody
    public ResponseEntity<?> updateMusicScore(
            @PathVariable String id,
            @RequestBody MusicScore updatedScore,
            HttpSession session) {

        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        if (username == null || !"admin".equals(role)) {
            return ResponseEntity.status(401)
                    .body(Map.of("message", "Unauthorized"));
        }

        try {

            MusicScore existingScore = musicScoreService.getMusicScoreById(id);
            if (existingScore == null) {
                return ResponseEntity.status(404)
                        .body(Map.of("message", "Music score not found"));
            }

            existingScore.setTitle(updatedScore.getTitle());
            existingScore.setComposer(updatedScore.getComposer());
            existingScore.setGenre(updatedScore.getGenre());
            existingScore.setEmotion(updatedScore.getEmotion());
            existingScore.setGender(updatedScore.getGender());
            existingScore.setPrice(updatedScore.getPrice());

            musicScoreService.updateMusicScore(existingScore);

            return ResponseEntity.ok()
                    .body(Map.of(
                            "message", "Music score updated successfully",
                            "data", existingScore));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Error updating music score: " + e.getMessage()));
        }
    }

}