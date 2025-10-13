package com.quiz.demo.repository;

import com.quiz.demo.model.Time;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeRepository extends JpaRepository<Time, Long> {
    Time findById(long id);
}
