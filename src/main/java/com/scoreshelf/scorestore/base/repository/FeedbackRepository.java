package com.scoreshelf.scorestore.base.repository;

import com.scoreshelf.scorestore.base.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String> {
    List<Feedback> findByUserId(String userId); 
}