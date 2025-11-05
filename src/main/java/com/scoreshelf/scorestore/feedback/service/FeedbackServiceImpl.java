package com.scoreshelf.scorestore.feedback.service;

import com.scoreshelf.scorestore.base.entity.Feedback;
import com.scoreshelf.scorestore.base.repository.FeedbackRepository;
import com.scoreshelf.scorestore.base.service.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FeedbackServiceImpl implements IFeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    @Override
    public Feedback saveFeedback(Feedback feedback) {
        
        feedback.setFeedbackId(generateFeedbackId());
        
        logger.info("Saving feedback: {}", feedback);
        
        Feedback savedFeedback = feedbackRepository.save(feedback);
        logger.info("Feedback saved successfully with ID: {}", savedFeedback.getFeedbackId());

        return savedFeedback;
    }
    
    private String generateFeedbackId() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(4);
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    @Override
    public List<Feedback> getFeedbackByUserId(String userId) {
        return feedbackRepository.findByUserId(userId);
    }

    @Override
    public void deleteFeedback(String feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }

    @Override
    public Feedback updateFeedbackStatus(String feedbackId, String newStatus) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));
        feedback.setFeedbackStatus(newStatus);
        return feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll(); 
    }
}