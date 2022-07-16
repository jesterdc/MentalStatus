package com.example.mentalstatus;

import java.io.Serializable;

public class QuestionList_SleepDisorder implements Serializable {

    private final String question;
    private final String option1, option2, option3, option4;
    private int equivalent;


    public QuestionList_SleepDisorder(String question, String option1, String option2, String option3, String option4, int equivalent) {
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
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }


    public int getUserSelectedAnswerEquivalent() {
        return equivalent;
    }

    public void setAnswerEquivalent (int equivalent){
        this.equivalent = equivalent;
    }
}
