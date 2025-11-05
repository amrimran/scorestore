package com.scoreshelf.scorestore.base.service;

import com.scoreshelf.scorestore.base.entity.Feedback;

import java.util.List;

public interface IFeedbackService {
    Feedback saveFeedback(Feedback feedback); 
    List<Feedback> getFeedbackByUserId(String userId); 
    Feedback updateFeedbackStatus(String feedbackId, String newStatus); 
    void deleteFeedback(String feedbackId);
    List<Feedback> getAllFeedback();
}