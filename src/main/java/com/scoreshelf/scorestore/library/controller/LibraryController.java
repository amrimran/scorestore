package com.scoreshelf.scorestore.library.controller;

import com.scoreshelf.scorestore.auth.service.AuthServiceImpl;
import com.scoreshelf.scorestore.base.entity.MusicScore;
import com.scoreshelf.scorestore.base.entity.User;
import com.scoreshelf.scorestore.base.service.IMusicScoreService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LibraryController {

    @Autowired
    private IMusicScoreService musicScoreService;

    @Autowired
    private AuthServiceImpl authService;

    @GetMapping("/customer-library")
    public String customerLibrary(Model model, HttpSession session) {

        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        if (username == null || !"customer".equals(role)) {
            return "redirect:/login";
        }

        User currentUser = authService.getCurrentUser(username);
        List<String> ownedScoreIds = currentUser.getOwnedScoreIds();
        List<MusicScore> ownedScores = musicScoreService.getMusicScoresByIds(ownedScoreIds);
        model.addAttribute("ownedScores", ownedScores);
        return "customer/customer-library";
    }

}