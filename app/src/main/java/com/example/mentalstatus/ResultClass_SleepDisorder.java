package com.example.mentalstatus;

public class ResultClass_SleepDisorder {

    String date, score, result;

    public ResultClass_SleepDisorder(){

    }

    public ResultClass_SleepDisorder(String date, String score, String result) {
        this.date = date;
        this.score = score;
        this.result = result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
