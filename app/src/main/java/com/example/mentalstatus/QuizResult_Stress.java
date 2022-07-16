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

public class QuizResult_Stress extends AppCompatActivity {

    private List<QuestionList_Stress> questionListStresses= new ArrayList<>();


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
        setContentView(R.layout.quiz_result_stress);



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
        questionListStresses = (List<QuestionList_Stress>) getIntent().getSerializableExtra("stressOption");

        totalQuestion.setText("out of " + 30);
        score.setText(getTotalScore() + "");

        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizResult_Stress.this, Result_History_Stress.class));
            }
        });

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizResult_Stress.this, MainActivity.class));
            }
        });

        therapist1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl("https://www.facebook.com/amaracenterph/");
            }
        });

        therapist2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl("https://www.facebook.com/bloomclinicph/");
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

        for(int i = 0; i < questionListStresses.size(); i++) {

            int getUserAnswerEquivalent = questionListStresses.get(i).getUserSelectedAnswerEquivalent();
            equivalentScore += getUserAnswerEquivalent;

            if(equivalentScore>=20){
                evaluationResult.setText("High Risk");
                evalDes.setText("Scores in this range show a large amount of life change in the past year and a high susceptibility to stress-induced health issues if the sources of stress are not addressed or managed. These results do not mean that you will get a stress-related condition, but it may be time to start a conversation with your doctor or a mental health professional. Finding the right treatment plan and working with your doctor or healthcare provider can help you to manage your symptoms.");
            }
            else if(equivalentScore<25&&equivalentScore>10){
                evaluationResult.setText("Moderate Risk");
                evalDes.setText("Scores in this range show a reasonable amount of life change in the past year and a moderate susceptibility to stress-induced health issues if the sources of stress are not addressed or managed. These results do not mean that you will get a stress-related condition, but it may be time to start a conversation with your doctor or a mental health professional. Finding the right treatment plan and working with your doctor or healthcare provider can help you to manage your symptoms.");
            }
            else if(equivalentScore<11){
                evaluationResult.setText("Low Risk");
                evalDes.setText("Scores in this range show a relatively low amount of life change and low susceptibility to stress-induced health issues. If you notice that your symptoms aren't improving, you may want to bring them up with your doctor, a mental health professional, or someone who is supporting you.");
            }



        }

        if(equivalentScore>=20){
            evalResult = "High Risk";
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
        fStore.collection("users/"+userID+"/stressResult").add(resultMap);
    }

}