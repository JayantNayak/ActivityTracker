package com.hobby.jayant.activitytracker.models;

public class Shooting {


    private float totalscore;

    private int totalshots;

    private Long date;

    private long userid;

    public long getUserId() {
        return userid;
    }

    public void setUserId(long userid) {
        this.userid = userid;
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

    private String comment;

    public float getTotalscore() {
        return totalscore;
    }

    public void setTotalscore(int totalscore) {
        this.totalscore = totalscore;
    }

    public int getTotalshots() {
        return totalshots;
    }

    public void setTotalshots(int totalshots) {
        this.totalshots = totalshots;
    }

    public Shooting() {

    }

    public Shooting(float totalscore, int totalshots, Long date) {
        this.totalscore = totalscore;
        this.totalshots = totalshots;
        this.date = date;

    }

    public Shooting(float totalscore, int totalshots, Long date, String comment) {
        this.totalscore = totalscore;
        this.totalshots = totalshots;
        this.date = date;
        this.comment = comment;

    }
}
