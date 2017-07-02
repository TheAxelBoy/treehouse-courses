package com.teamtreehouse.courses.model;

import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexlazau on 15/02/2017.
 */

// Simple implementation of the DAO interface
// Does NOT provide persistence when server restarts
public class SimpleCourseIdeaDAO implements CourseIdeaDAO {
    private List<CourseIdea> ideas;

    public SimpleCourseIdeaDAO() {
        ideas = new ArrayList<>();
    }

    @Override
    public boolean add(CourseIdea idea) {
        return ideas.add(idea);
    }

    @Override
    public List<CourseIdea> findAll() {
        return new ArrayList<>(ideas);
    }

    @Override
    public CourseIdea findBySlug(String slug) {
        // Using the new Java 8 stream operations
        return ideas.stream()
                .filter(idea -> idea.getSlug().equals(slug))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }
}
