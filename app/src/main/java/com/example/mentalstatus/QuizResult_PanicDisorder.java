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

public class QuizResult_PanicDisorder extends AppCompatActivity {

    private List<QuestionList_PanicDisorder> questionListsPanicDisorder = new ArrayList<>();


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
        setContentView(R.layout.quiz_result_panic_disorder);



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
        questionListsPanicDisorder = (List<QuestionList_PanicDisorder>) getIntent().getSerializableExtra("panicDisorderOption");

        totalQuestion.setText("out of " + 28);
        score.setText(getTotalScore() + "");

        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizResult_PanicDisorder.this, Result_History_PanicDisorder.class));
            }
        });

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizResult_PanicDisorder.this, MainActivity.class));
            }
        });

        therapist1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl("https://mindinstitute.ph/");
            }
        });

        therapist2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl("http://bettersteps.org/");
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

        for(int i = 0; i < questionListsPanicDisorder.size(); i++) {

            int getUserAnswerEquivalent = questionListsPanicDisorder.get(i).getUserSelectedAnswerEquivalent();
            equivalentScore += getUserAnswerEquivalent;

            if(equivalentScore>=14) {
                evaluationResult.setText("Severe Symptoms");
                evalDes.setText("Your results indicate that you are at severe risk for Panic Disorder. Your symptoms are likely making it extremely difficult for you to function normally and carry out your day-to-day activities. These results do not mean that you have panic disorder, but it may be time to start a conversation with a mental health professional. Finding the right treatment plan and working with a healthcare provider or a support person can help you manage your symptoms.");
            }
            else if(equivalentScore<14&&equivalentScore>8){
                evaluationResult.setText("Moderate Risk");
                evalDes.setText("Your results indicate that you have moderate risk of having Panic Disorder. Your symptoms are likely making it difficult for you to function normally and carry out your day-to-day activities. These results do not mean that you have panic disorder, but it may be time to start a conversation with a mental health professional. Finding the right treatment plan and working with a healthcare provider or a support person can help you manage your symptoms.");
            }
            else if(equivalentScore<9&&equivalentScore>5){
                evaluationResult.setText("Mild Risk");
                evalDes.setText("Your results indicate that you are at mild risk of having Panic Disorder. While your symptoms are not likely having a major impact on your life, it is important to monitor them. If you notice that your symptoms aren't improving, you may want to bring them up with a mental health professional or someone who is supporting you.");
            }
            else if(equivalentScore<6&&equivalentScore>1){
                evaluationResult.setText("Minimal Risk");
                evalDes.setText("Your results indicate that you are at minimal risk of having Panic Disorder. If you notice that your symptoms aren't improving, you may want to bring them up with a mental health professional or someone who is supporting you.");
            }
            else if(equivalentScore<2){
                evaluationResult.setText("No Symptoms");
                evalDes.setText("If you notice that your symptoms aren't improving, you may want to bring them up with your doctor or someone who is supporting you. This evaluation is not meant to be a diagnosis, or the elimination of a diagnosis. Only a trained medical or mental health professional can diagnose panic disorder.");
            }



        }

        if(equivalentScore>=14){
            evalResult = "Severe Symptoms";
            evalScore = equivalentScore;
            getResult(evalResult, evalScore);
        }
        else if(equivalentScore<14&&equivalentScore>8){
            evalResult = "Moderate Risk";
            evalScore = equivalentScore;
            getResult(evalResult, evalScore);
        }
        else if(equivalentScore<9&&equivalentScore>5){
            evalResult = "Mild Risk";
            evalScore = equivalentScore;
            getResult(evalResult, evalScore);
        }
        else if(equivalentScore<6&&equivalentScore>1){
            evalResult = "Minimal Riskk";
            evalScore = equivalentScore;
            getResult(evalResult, evalScore);
        }
        else if(equivalentScore<2){
            evalResult = "No Symptoms";
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
        fStore.collection("users/"+userID+"/panicDisorderResult").add(resultMap);
    }

}