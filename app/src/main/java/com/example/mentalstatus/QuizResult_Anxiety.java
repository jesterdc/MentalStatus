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

public class QuizResult_Anxiety extends AppCompatActivity {

    private List<QuestionList_Anxiety> anxietyQuestionLists = new ArrayList<>();


    TextView evaluationResult, evalDes, score, totalQuestion, resultButton;
    AppCompatButton backHome, therapist1Btn, therapist2Btn;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_result_anxiety);


        evaluationResult = findViewById(R.id.evalResult);
        evalDes = findViewById(R.id.evalDesc);
        score = findViewById(R.id.score);
        totalQuestion = findViewById(R.id.questionTotal);
        resultButton = findViewById(R.id.resultBtn);
        therapist1Btn = findViewById(R.id.therapist1Btn);
        therapist2Btn = findViewById(R.id.therapist2Btn);

        fAuth = FirebaseAuth.getInstance();



        backHome = findViewById(R.id.homeBtn);

        //Getting list from Depression Test Activity
        anxietyQuestionLists = (List<QuestionList_Anxiety>) getIntent().getSerializableExtra("anxietyOption");

        totalQuestion.setText("out of " + 21);
        score.setText(getTotalScore() + "");

        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizResult_Anxiety.this, Result_History_Anxiety.class));
            }
        });

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizResult_Anxiety.this, MainActivity.class));
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
                goToUrl("https://prescriptionpsychiatrists.com.ph/");
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

        for(int i = 0; i < anxietyQuestionLists.size(); i++) {

            int getUserAnswerEquivalent = anxietyQuestionLists.get(i).getUserSelectedAnswerEquivalent();
            equivalentScore += getUserAnswerEquivalent;
            if(equivalentScore>=15){
                evaluationResult.setText("Severe Anxiety");
                evalDes.setText("Your results indicate that you may be experiencing symptoms of severe anxiety. Based on your answers, these symptoms seem to be greatly interfering with your relationships and the tasks of everyday life. These result do not mean that you have depression, but we recommend you start a conversation with a mental health professional.");
            }
            else if(equivalentScore<15&&equivalentScore>9){
                evaluationResult.setText("Moderate Anxiety");
                evalDes.setText("Your results indicate that you may be experiencing symptoms of moderate depression. Based on your answers, living with these symptoms could be causing difficulty managing relationships and even the task of everyday life. These result do not mean that you have anxiety, but it may be time to start a conversation with a mental health professional");
            }
            else if(equivalentScore<10&&equivalentScore>4){
                evaluationResult.setText("Mild Anxiety");
                evalDes.setText("Your results indicate that you may be experiencing symptoms of mild anxiety. While your symptoms are not likely having a major impact on your life, it is important to monitor them. These result do not mean that you have depression, but it may be time to start a conversation with a mental health professional");
            }
            else if(equivalentScore<5){
                evaluationResult.setText("Minimal Anxiety");
                evalDes.setText("Your results indicate that you have none, or vey few symptoms of anxiety. If you notice that your symptoms aren't improving, you may want to bring them up with a mental health professional or someone who is supporting you");
            }
        }

        if(equivalentScore>=15){
            evalResult = "Severe Anxiety";
            evalScore = equivalentScore;
            getResult(evalResult, evalScore);
        }
        else if(equivalentScore<15&&equivalentScore>9){
            evalResult = "Moderate Anxiety";
            evalScore = equivalentScore;
            getResult(evalResult, evalScore);
        }
        else if(equivalentScore<10&&equivalentScore>4){
            evalResult = "Mild Anxiety";
            evalScore = equivalentScore;
            getResult(evalResult, evalScore);
        }
        else if(equivalentScore<5){
            evalResult = "Minimal Anxiety";
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
        fStore.collection("users/"+userID+"/anxietyResult").add(resultMap);
    }

}