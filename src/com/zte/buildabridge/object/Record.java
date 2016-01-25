package com.zte.buildabridge.object;

public class Record {
    private String name;
    private int score;
    private String date;

    public Record(String name, int score, String date) {
        if(name == null || name.length() < 1){
            this.name = "ÄäÃû";
        }else{
            this.name = name;
        }
        this.score = score;
        this.date = date;
    }

    public Record(String String, int score) {
        this(String, score, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Record [name=" + name + ", score=" + score + ", date=" + date
                + "]";
    }
}
