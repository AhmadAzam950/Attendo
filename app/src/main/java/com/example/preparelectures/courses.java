package com.example.preparelectures;

public class courses {
    private String courseId;

    public courses() {
    }

    public courses(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
