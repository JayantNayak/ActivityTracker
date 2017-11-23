package com.hobby.jayant.activitytracker.models;




//import org.joda.time.LocalDate;

public class Yoga {

	private Long id;


	private Long duration;


	private Long datetime;


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

	public Long getDateTime() {
		return datetime;
	}

	public void setDateTime(Long datetime) {
		this.datetime = datetime;
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

	public Yoga(Long datetime, Long duration, int rating, String comment) {

		this.datetime = datetime;
		this.duration = duration;
		this.rating = rating;
		this.comment = comment;
	}

	public Yoga(Long datetime, Long duration, int rating) {

		this.datetime = datetime;
		this.duration = duration;
		this.rating = rating;
	}
}
