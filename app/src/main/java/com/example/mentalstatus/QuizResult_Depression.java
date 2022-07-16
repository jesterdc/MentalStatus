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

public class QuizResult_Depression extends AppCompatActivity {

    private List<QuestionList_Depression> questionListDepressions = new ArrayList<>();


    TextView evaluationResult, evalDes, score, totalQuestion, resultButton;
    AppCompatButton backHome, psyConsultBtn, oneLifeBtn;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    Calendar calendar;
    String currentDate;
    FirebaseAuth fAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_result_depression);



        evaluationResult = findViewById(R.id.evalResult);
        evalDes = findViewById(R.id.evalDesc);
        score = findViewById(R.id.score);
        totalQuestion = findViewById(R.id.questionTotal);
        resultButton = findViewById(R.id.resultBtn);
        psyConsultBtn = findViewById(R.id.psyConsultBtn);
        oneLifeBtn = findViewById(R.id.oneLifeBtn);

        fAuth = FirebaseAuth.getInstance();

        calendar = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        backHome = findViewById(R.id.homeBtn);

        //Getting list from Depression Test Activity
        questionListDepressions = (List<QuestionList_Depression>) getIntent().getSerializableExtra("questions");

        totalQuestion.setText("out of " + 30);
        score.setText(getTotalScore() + "");

        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizResult_Depression.this, Result_History_Depression.class));
            }
        });

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizResult_Depression.this, MainActivity.class));
            }
        });

        psyConsultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl("https://www.psychconsult.com.ph/");
            }
        });

        oneLifeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl("https://www.opencounseling.com/philippines/quezon-city/counseling-agency/one-life-only-counseling-services-244585");
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

        for(int i = 0; i < questionListDepressions.size(); i++) {

            int getUserAnswerEquivalent = questionListDepressions.get(i).getUserSelectedAnswerEquivalent();
            equivalentScore += getUserAnswerEquivalent;

            if(equivalentScore>=20){
                evaluationResult.setText("High Risk");
                evalDes.setText("Your results indicate that you may be experiencing symptoms of severe depression. Based on your answers, these symptoms seem to be greatly interfering with your relationships and the tasks of everyday life. These result do not mean that you have depression, but we recommend you start a conversation with a mental health professional.");
            }
            else if(equivalentScore<21&&equivalentScore>9){
                evaluationResult.setText("Moderate Risk");
                evalDes.setText("Your results indicate that you may be experiencing symptoms of moderate depression. Based on your answers, living with these symptoms could be causing difficulty managing relationships and even the task of everyday life. These result do not mean that you have depression, but it may be time to start a conversation with a mental health professional");
            }
            else if(equivalentScore<11&&equivalentScore==3){
                evaluationResult.setText("Low Risk");
                evalDes.setText("Your results indicate that you may be experiencing symptoms of mild depression. While your symptoms are not likely having a major impact on your life, it is important to monitor them. These result do not mean that you have depression, but it may be time to start a conversation with a mental health professional");
            }
            else if(equivalentScore<3){
                evaluationResult.setText("Very Low Risk");
                evalDes.setText("Your results indicate that you have none, or vey few symptoms of depression. If you notice that your symptoms aren't improving, you may want to bring them up with a mental health professional or someone who is supporting you");
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
        else if(equivalentScore<11&&equivalentScore>2){
            evalResult = "Low Risk";
            evalScore = equivalentScore;
            getResult(evalResult, evalScore);
        }
        else if(equivalentScore<3){
            evalResult = "Very Low Risk";
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
        fStore.collection("users/"+userID+"/depressionResult").add(resultMap);
    }

}