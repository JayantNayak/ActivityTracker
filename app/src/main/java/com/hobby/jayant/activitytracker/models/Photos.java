package com.hobby.jayant.activitytracker.models;


import java.util.Map;

public class Photos {


    public Map<String, String> getYogaphotos() {
        return yogaphotos;
    }

    public void setYogaphotos(Map<String, String> yogaphotos) {
        this.yogaphotos = yogaphotos;
    }

    public Map<String, String> getShootingphotos() {
        return shootingphotos;
    }

    public void setShootingphotos(Map<String, String> shootingphotos) {
        this.shootingphotos = shootingphotos;
    }

    public Map<String, String> getExercisephotos() {
        return exercisephotos;
    }

    public void setExercisephotos(Map<String, String> exercisephotos) {
        this.exercisephotos = exercisephotos;
    }

    //https://gdurl.com/KD27
    private Map<String,String> yogaphotos;
    private Map<String,String> shootingphotos;
    private Map<String,String> exercisephotos;

    public Photos(){}
    public Photos(Map<String,String> yogaphotos,Map<String,String> shootingphotos,
                  Map<String,String> exercisephotos){
        this.yogaphotos=yogaphotos;
        this.shootingphotos=shootingphotos;
        this.exercisephotos=exercisephotos;

    }

   public String toString(){
       StringBuilder sb = new StringBuilder();
       sb.append("Shooting Pics \n");
       for (Map.Entry shootingPic:shootingphotos.entrySet()) {
           sb.append(shootingPic.getKey() +" : "+shootingPic.getValue() +"\n");

       }

       sb.append("Yoga Pics \n");
       for (Map.Entry yogaPic:yogaphotos.entrySet()) {
           sb.append(yogaPic.getKey() +" : "+yogaPic.getValue() +"\n");

       }
       sb.append("Exercise Pics \n");
       for (Map.Entry exercisePic:exercisephotos.entrySet()) {
           sb.append(exercisePic.getKey() +" : "+exercisePic.getValue() +"\n");

       }
       return sb.toString();
   }



}
