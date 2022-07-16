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

public class QuizResult_SleepDisorder extends AppCompatActivity {

    private List<QuestionList_SleepDisorder> questionListsSleepDisorder = new ArrayList<>();


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
        setContentView(R.layout.quiz_result_sleep_disorder);



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
        questionListsSleepDisorder = (List<QuestionList_SleepDisorder>) getIntent().getSerializableExtra("sleepDisorderOption");

        totalQuestion.setText("out of " + 30);
        score.setText(getTotalScore() + "");

        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizResult_SleepDisorder.this, Result_History_SleepDisorder.class));
            }
        });

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizResult_SleepDisorder.this, MainActivity.class));
            }
        });

        therapist1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl("https://www.facebook.com/neurosleepcenter.ph/");
            }
        });

        therapist2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl("https://www.stlukes.com.ph/health-specialties-and-services/institutes-departments-centers-and-services/comprehensive-sleep-disorders-center");
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

        for(int i = 0; i < questionListsSleepDisorder.size(); i++) {

            int getUserAnswerEquivalent = questionListsSleepDisorder.get(i).getUserSelectedAnswerEquivalent();
            equivalentScore += getUserAnswerEquivalent;

            if(equivalentScore>=25){
                evaluationResult.setText("Severe Risk");
                evalDes.setText("Your results indicate that you may be experiencing a severe sleep disorder. Based on your answers, living with these symptoms could be causing difficulty with tasks of everyday life. These result do not mean that you have a sleep disorder, but it may be time to start a conversation with a mental health professional.");
            }
            else if(equivalentScore<25&&equivalentScore>10){
                evaluationResult.setText("Moderate Risk");
                evalDes.setText("Your results indicate that you may be experiencing moderate sleep disorder. Based on your answers, living with these symptoms could be causing difficulty with tasks of everyday life. These result do not mean that you have sleep disorder, but it may be time to start a conversation with a mental health professional");
            }
            else if(equivalentScore<11){
                evaluationResult.setText("Low Risk");
                evalDes.setText("Your results indicate that you have no, or very few signs of sleep disorder. If you notice that your symptoms aren't improving, you may want to bring them up with mental health professional or someone who is supporting you.");
            }



        }

        if(equivalentScore>=25){
            evalResult = "Severe Risk";
            evalScore = equivalentScore;
            getResult(evalResult, evalScore);
        }
        else if(equivalentScore<25&&equivalentScore>10){
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
        fStore.collection("users/"+userID+"/sleepDisorderResult").add(resultMap);
    }

}