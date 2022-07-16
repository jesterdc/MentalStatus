package com.example.mentalstatus;

import java.io.Serializable;

public class QuestionList_Anxiety implements Serializable {

    private final String question;
    private final String option1, option2, option3, option4;
    private int equivalent;


    public QuestionList_Anxiety(String question, String option1, String option2, String option3, String option4, int equivalent) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.equivalent = 0;
    }


    public String getQuestion() {
        return question;
    }



    public String getOption1() {
        String option1 = "Never";
        return option1;
    }

    public String getOption2() {
        String option2 = "Some of the time";
        return option2;
    }

    public String getOption3() {
        String option3 = "Much of the time";
        return option3;
    }

    public String getOption4() {
        String option4 = "Nearly all the time";
        return option4;
    }


    public int getUserSelectedAnswerEquivalent() {
        return equivalent;
    }

    public void setAnswerEquivalent (int equivalent){
        this.equivalent = equivalent;
    }
}
