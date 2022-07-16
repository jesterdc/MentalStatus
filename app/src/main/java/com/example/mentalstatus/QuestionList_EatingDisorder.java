package com.example.mentalstatus;

import java.io.Serializable;

public class QuestionList_EatingDisorder implements Serializable {

    private final String question;
    private final String option1, option2, option3, option4;
    private int equivalent;


    public QuestionList_EatingDisorder(String question, String option1, String option2, String option3, String option4, int equivalent) {
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
        String option1 = "Not true for me";
        return option1;
    }

    public String getOption2() {
        String option2 = "A little true for me";
        return option2;
    }

    public String getOption3() {
        String option3 = "Somewhat true for me";
        return option3;
    }

    public String getOption4() {
        String option4 = "Very true for me";
        return option4;
    }


    public int getUserSelectedAnswerEquivalent() {
        return equivalent;
    }

    public void setAnswerEquivalent (int equivalent){
        this.equivalent = equivalent;
    }
}
