package com.scoreshelf.scorestore.feedback.controller;

import com.scoreshelf.scorestore.auth.service.AuthServiceImpl;
import com.scoreshelf.scorestore.base.entity.Feedback;
import com.scoreshelf.scorestore.base.entity.User;
import com.scoreshelf.scorestore.base.service.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FeedbackController {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private IFeedbackService feedbackService;

    @Autowired
    private AuthServiceImpl authService;

    @GetMapping("/customer-feedback")
    public String customerFeedback(Model model, HttpSession session) {

        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        if (username == null || !"customer".equals(role)) {
            return "redirect:/login";
        }

        User currentUser = authService.getCurrentUser(username);
        if (currentUser == null) {
            return "redirect:/login";
        }

        List<Feedback> feedbackList = feedbackService.getFeedbackByUserId(currentUser.getId());
        model.addAttribute("feedbackList", feedbackList);

        return "customer/customer-feedback";
    }

    @GetMapping("/admin-feedback")
    public String adminFeedback(Model model, HttpSession session) {

        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        if (username == null || !"admin".equals(role)) {
            return "redirect:/login";
        }

        List<Feedback> feedbackList = feedbackService.getAllFeedback();

        model.addAttribute("feedbackList", feedbackList);

        return "admin/admin-feedback";
    }

    @PostMapping("/submit")
    public String submitFeedback(
            @RequestParam String feedbackTitle,
            @RequestParam String feedbackDetail,
            HttpSession session) {

        logger.info("Received feedback submission:");
        logger.info("Title: {}", feedbackTitle);
        logger.info("Detail: {}", feedbackDetail);

        String username = (String) session.getAttribute("username");
        if (username == null) {
            logger.warn("User not logged in. Redirecting to login page.");
            return "redirect:/login";
        }

        User currentUser = authService.getCurrentUser(username);
        String userId = currentUser.getId();

        if (userId == null) {
            logger.warn("User ID not found. Redirecting to login page.");
            return "redirect:/login";
        }

        logger.info("User ID: {}", userId);

        Feedback feedback = new Feedback();
        feedback.setFeedbackTitle(feedbackTitle);
        feedback.setFeedbackDetail(feedbackDetail);
        feedback.setFeedbackStatus("Unresolved");
        feedback.setUserId(userId);

        Feedback savedFeedback = feedbackService.saveFeedback(feedback);
        logger.info("Feedback saved successfully with ID: {}", savedFeedback.getFeedbackId());

        return "redirect:/customer-feedback";
    }

    @PostMapping("/update-feedback-status")
    public String updateFeedbackStatus(@RequestParam String feedbackId, @RequestParam String feedbackStatus,
            HttpSession session) {

        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        if (username == null || !"admin".equals(role)) {
            return "redirect:/login";
        }

        feedbackService.updateFeedbackStatus(feedbackId, feedbackStatus);

        return "redirect:/admin-feedback";
    }

    @DeleteMapping("/delete-feedback/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteFeedback(@PathVariable String id, HttpSession session) {

        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        if (username == null || !"admin".equals(role)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            feedbackService.deleteFeedback(id);
            return ResponseEntity.ok("Feedback deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting feedback: " + e.getMessage());
        }
    }

}