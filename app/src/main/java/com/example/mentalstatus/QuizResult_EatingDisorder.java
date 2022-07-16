package com.example.mentalstatus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class QuizResult_EatingDisorder extends AppCompatActivity {

    private List<QuestionList_EatingDisorder> questionListEatingDisorders = new ArrayList<>();


    TextView evaluationResult, evalDes, score, totalQuestion, resultButton;
    AppCompatButton backHome, therapist1Btn, therapist2Btn;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    Calendar calendar;
    String currentDate;
    FirebaseAuth fAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_result_eating_disorder);



        evaluationResult = findViewById(R.id.evalResult);
        evalDes = findViewById(R.id.evalDesc);
        score = findViewById(R.id.score);
        totalQuestion = findViewById(R.id.questionTotal);
        resultButton = findViewById(R.id.resultBtn);
        therapist1Btn = findViewById(R.id.therapist1Btn);
        therapist2Btn = findViewById(R.id.therapist2Btn);

        fAuth = FirebaseAuth.getInstance();

        calendar = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        backHome = findViewById(R.id.homeBtn);

        //Getting list from Depression Test Activity
        questionListEatingDisorders = (List<QuestionList_EatingDisorder>) getIntent().getSerializableExtra("eatingDisorderOption");

        totalQuestion.setText("out of " + 30);
        score.setText(getTotalScore() + "");

        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizResult_EatingDisorder.this, Result_History_EatingDisorder.class));
            }
        });

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizResult_EatingDisorder.this, MainActivity.class));
            }
        });

        therapist1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl("https://dfsconsultingph.com/");
            }
        });

        therapist2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl("https://www.lifechangerecoverycenter.com/our-mission");
            }
        });

    }
    private void goToUrl(String s){
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));

    }

    private int getTotalScore(){
        int equivalentScore = 0;
        String evalResult;
        int evalScore;

        for(int i = 0; i < questionListEatingDisorders.size(); i++) {

            int getUserAnswerEquivalent = questionListEatingDisorders.get(i).getUserSelectedAnswerEquivalent();
            equivalentScore += getUserAnswerEquivalent;

            if(equivalentScore>=20){
                evaluationResult.setText("High Risk");
                evalDes.setText("Your results indicate that you are at high risk for Eating Disorder. Scores in this range show signs of severe Eating Disorder which, based on your answers, may be seriously interfering with your everyday life. These results do not mean that you have Eating Disorder or any other type of eating disorder, but it may be time to start a conversation with your doctor or a mental health professional. Finding the right treatment plan and working with your doctor or healthcare provider can help you to manage your symptoms.");
            }
            else if(equivalentScore<21&&equivalentScore>9){
                evaluationResult.setText("Moderate Risk");
                evalDes.setText("Your results indicate that you are at moderate risk of Eating Disorder. Scores in this range show signs of moderate Eating Disorder which, based on your answers, may be interfering with your everyday life. These results do not mean that you have Eating Disorder or any other type of eating disorder, but it may be time to start a conversation with your doctor or a mental health professional. Finding the right treatment plan and working with your doctor or healthcare provider can help you to manage your symptoms.");
            }
            else if(equivalentScore<11){
                evaluationResult.setText("Low Risk");
                evalDes.setText("Your results indicate that you have no, or very few symptoms of Eating Disorder. Scores in this range show a low evidence of binging behavior. If you notice that your symptoms aren't improving, you may want to bring them up with your doctor, a mental health professional, or someone who is supporting you.");
            }



        }

        if(equivalentScore>=20){
            evalResult = "High Risk";
            evalScore = equivalentScore;
            getResult(evalResult, evalScore);
        }
        else if(equivalentScore<21&&equivalentScore>9){
            evalResult = "Moderate Risk";
            evalScore = equivalentScore;
            getResult(evalResult, evalScore);
        }
        else if(equivalentScore<11){
            evalResult = "Low Risk";
            evalScore = equivalentScore;
            getResult(evalResult, evalScore);
        }


        return equivalentScore;

    }

    public void getResult(String evalResult, int evalScore){
        DateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy, h:mm a");
        String currentDateandTime = df.format(Calendar.getInstance().getTime());
        userID = fAuth.getCurrentUser().getUid();
        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("result", evalResult);
        resultMap.put("score", String.valueOf(evalScore));
        resultMap.put("date", currentDateandTime);
        fStore.collection("users/"+userID+"/eatingDisorderResult").add(resultMap);
    }

}