package com.hobby.jayant.activitytracker.models;




//import org.joda.time.LocalDate;

public class Yoga {

	private Long id;


	private Long duration;


	private Long date;


	private String comment;


	private int rating;


	private long userId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Yoga() {

	}

	public Yoga(Long date, Long duration, int rating, String comment) {

		this.date = date;
		this.duration = duration;
		this.rating = rating;
		this.comment = comment;
	}


	public Yoga(Long date, Long duration, int rating) {

		this.date = date;
		this.duration = duration;
		this.rating = rating;
	}
}
