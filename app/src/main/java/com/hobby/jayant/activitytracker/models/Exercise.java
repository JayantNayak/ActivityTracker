package com.hobby.jayant.activitytracker.models;


public class Exercise {

    private Long duration;
    private Long date;
    private String comment;
    private int rating;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Exercise() {

    }

    public Exercise(Long date, Long duration, int rating, String comment) {

        this.date = date;
        this.duration = duration;
        this.rating = rating;
        this.comment = comment;
    }

    public Exercise(Long date, Long duration, int rating) {
        this.date = date;
        this.duration = duration;
        this.rating = rating;
    }
}
