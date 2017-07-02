package com.teamtreehouse.courses.model;

import java.util.List;

/**
 * Created by alexlazau on 15/02/2017.
 */

// Data Access Object provides an abstract interface to some type
// of database or other persistence mechanism
public interface CourseIdeaDAO {
    boolean add(CourseIdea idea);

    List<CourseIdea> findAll();

    CourseIdea findBySlug(String slug);
}
