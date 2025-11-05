package com.scoreshelf.scorestore.base.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "feedbacks")
public class Feedback {

    @Id
    @Column(name = "feedback_id", length = 4, unique = true, nullable = false)
    private String feedbackId; 

    @Column(name = "feedback_title", nullable = false)
    private String feedbackTitle; 

    @Column(name = "feedback_detail", nullable = false, columnDefinition = "TEXT")
    private String feedbackDetail; 

    @Column(name = "feedback_status", nullable = false)
    private String feedbackStatus; 

    @Column(name = "user_id", nullable = false)
    private String userId; 
    
    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getFeedbackTitle() {
        return feedbackTitle;
    }

    public void setFeedbackTitle(String feedbackTitle) {
        this.feedbackTitle = feedbackTitle;
    }

    public String getFeedbackDetail() {
        return feedbackDetail;
    }

    public void setFeedbackDetail(String feedbackDetail) {
        this.feedbackDetail = feedbackDetail;
    }

    public String getFeedbackStatus() {
        return feedbackStatus;
    }

    public void setFeedbackStatus(String feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}