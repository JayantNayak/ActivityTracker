package com.hobby.jayant.activitytracker.custom;

import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.Utils;
import com.hobby.jayant.activitytracker.activities.MainActivity;

/**
 * Created by I329687 on 12/18/2017.
 */

public class CustomEntry extends Entry {


    private  String comment;
    private int rating= -1;
    private MainActivity.PAGETYPE entryType;
    private int totalShots;

    public  CustomEntry(MainActivity.PAGETYPE entryType, float x, float y, int rating, String comment){
        super(x, y);
        this.entryType=entryType;
        this.rating =rating;
        this.comment=comment;

    }
    public  CustomEntry(MainActivity.PAGETYPE entryType,float x, float y,int rating ){
        super(x, y);
        this.entryType=entryType;
        this.rating =rating;
    }

    public  CustomEntry(MainActivity.PAGETYPE entryType,int totalShots,float x, float y){
        super(x, y);
        this.entryType=entryType;
        this.rating =rating;
        this.totalShots = totalShots;
    }
    public  CustomEntry(MainActivity.PAGETYPE entryType,int totalShots, float x, float y,String comment){
        super(x, y);
        this.entryType=entryType;
        this.rating =rating;
        this.comment=comment;
        this.totalShots = totalShots;

    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }
    public MainActivity.PAGETYPE getEntryType() {
        return entryType;
    }
    public  String toString(){
    StringBuilder sb = new StringBuilder("");
    switch (entryType){
        case YOGA:
            sb.append("Time : " + this.getY() + " minutes");
            break;
        case EXERCISE:
            sb.append("Time : " + this.getY() + " minutes");
            break;
        case SHOOTING:
            sb.append("Points : " + this.getY());
            sb.append("\nShots : " + this.totalShots);
            break;
    }
    if(this.rating!=-1){
        sb.append("\nRating : "+this.rating);
    }
    if(this.comment!=null){
        sb.append("\nComment : "+this.comment);

    }
    return  sb.toString();
    }
}
