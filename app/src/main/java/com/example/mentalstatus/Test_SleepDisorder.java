package com.example.mentalstatus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Test_SleepDisorder extends AppCompatActivity {

    private final List<QuestionList_SleepDisorder> sleepDisorderquestionLists = new ArrayList<>();

    private RelativeLayout option1Layout, option2Layout, option3Layout, option4Layout;
    private TextView option1TV, option2TV, option3TV, option4TV;
    private ImageView option1Icon, option2Icon, option3Icon, option4Icon;

    private TextView questionTV;
    private TextView totalQuestionTV;
    private TextView currentQuestionTV;

    //Database reference from URL
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mentalstatus-2e712-default-rtdb.firebaseio.com/");

    //Current question position
    private int currentQuestionPosition = 0;

    //Selected option number. Value must be between 1-4 (We have 4 options). 0 means no option is selected
    private int selectedOption = 0;
    private int scoreEquivalent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_disorder_test);

        //Option Layout
        option1Layout = findViewById(R.id.option1Layout);
        option2Layout = findViewById(R.id.option2Layout);
        option3Layout = findViewById(R.id.option3Layout);
        option4Layout = findViewById(R.id.option4Layout);


        //Options Text View
        option1TV = findViewById(R.id.option1TV);
        option2TV = findViewById(R.id.option2TV);
        option3TV = findViewById(R.id.option3TV);
        option4TV = findViewById(R.id.option4TV);


        //Option Radio Icon
        option1Icon = findViewById(R.id.option1Icon);
        option2Icon = findViewById(R.id.option2Icon);
        option3Icon = findViewById(R.id.option3Icon);
        option4Icon = findViewById(R.id.option4Icon);

        questionTV = findViewById(R.id.questionTV);
        totalQuestionTV = findViewById(R.id.totalQuestionTV);
        currentQuestionTV = findViewById(R.id.currentQuestionTV);

        final AppCompatButton nextBtn = findViewById(R.id.nextQuestion);

        //Show agreement dialog
        Agreement_Dialog agreementDialog = new Agreement_Dialog(Test_SleepDisorder.this);
        agreementDialog.setCancelable(false);
        agreementDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        agreementDialog.show();




        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot questions : snapshot.child("sleepDisorderOption").getChildren()){

                    String  getQuestion = questions.child("question").getValue(String.class);
                    String getOption1 = questions.child("option1").getValue(String.class);
                    String getOption2 = questions.child("option2").getValue(String.class);
                    String getOption3 = questions.child("option3").getValue(String.class);
                    String getOption4 = questions.child("option4").getValue(String.class);
                    int getEquivalent = Integer.parseInt(questions.child("equivalent").getValue(String.class));



                    //Creating question list object and add details
                    QuestionList_SleepDisorder sleepDisorderQuestionList = new QuestionList_SleepDisorder(getQuestion, getOption1, getOption2, getOption3, getOption4, getEquivalent);

                    //Adding depressionquestionlist object into list
                    sleepDisorderquestionLists.add(sleepDisorderQuestionList);
                }

                //Setting total questions to Text View
                totalQuestionTV.setText("/"+sleepDisorderquestionLists.size());

                //Select first question to TextView
                selectQuestion(currentQuestionPosition);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Test_SleepDisorder.this, "Failed to get data from database", Toast.LENGTH_SHORT).show();
            }
        });

        option1Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Assign 1 as first option is selected
                selectedOption = 1;
                scoreEquivalent = 0;

                //Select option
                selectOption(option1Layout, option1Icon);
            }
        });
        option2Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Assign 2 as second option is selected
                selectedOption = 2;
                scoreEquivalent = 1;

                //Select option
                selectOption(option2Layout, option2Icon);
            }
        });
        option3Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Assign 3 as third option is selected
                selectedOption = 3;
                scoreEquivalent = 2;

                //Select option
                selectOption(option3Layout, option3Icon);
            }
        });
        option4Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Assign 4 as fourth option is selected
                selectedOption = 4;
                scoreEquivalent = 3;

                //Select option
                selectOption(option4Layout, option4Icon);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check is user has selected an option or not
                if(selectedOption != 0){

                    //Set user selected answer
                    sleepDisorderquestionLists.get(currentQuestionPosition).setAnswerEquivalent(scoreEquivalent);

                    //Reset selected option to default value(0)
                    selectedOption = 0;
                    currentQuestionPosition++; //Increase current question value to getting next question

                    //Check if list has more questions
                    if(currentQuestionPosition<sleepDisorderquestionLists.size()){
                        selectQuestion(currentQuestionPosition); //Select question, next question
                    }
                    else{
                        //List has no questions left so finish the quiz
                        finishQuiz();
                    }
                }
                else {
                    Toast.makeText(Test_SleepDisorder.this, "Please select an Option", Toast.LENGTH_SHORT).show();
                }
            }
        });



        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if (bundle.getString("some") != null){
                Toast.makeText(getApplicationContext(), "" + bundle.getString(""), Toast.LENGTH_SHORT);
            }
        }
    }

    private void finishQuiz(){
        //Creating intent to open Result Activity
        Intent intent = new Intent(Test_SleepDisorder.this, QuizResult_SleepDisorder.class);

        //Bundle to pass QuestionList
        Bundle bundle = new Bundle();
        bundle.putSerializable("sleepDisorderOption", (Serializable) sleepDisorderquestionLists);

        //Add bundle to intent
        intent.putExtras(bundle);

        //Start activity
        startActivity(intent);

        //Destroy current Activity
        finish();
    }

    private void selectQuestion(int questionListPosition){
        //Reset options for new question
        resetOptions();

        //Getting question details and set to TextViews
        questionTV.setText(sleepDisorderquestionLists.get(questionListPosition).getQuestion());
        option1TV.setText(sleepDisorderquestionLists.get(questionListPosition).getOption1());
        option2TV.setText(sleepDisorderquestionLists.get(questionListPosition).getOption2());
        option3TV.setText(sleepDisorderquestionLists.get(questionListPosition).getOption3());
        option4TV.setText(sleepDisorderquestionLists.get(questionListPosition).getOption4());

        //Setting current question number to TextView
        currentQuestionTV.setText("Question "+(questionListPosition+1));

    }

    private void resetOptions(){
        option1Layout.setBackgroundResource(R.drawable.round_border);
        option2Layout.setBackgroundResource(R.drawable.round_border);
        option3Layout.setBackgroundResource(R.drawable.round_border);
        option4Layout.setBackgroundResource(R.drawable.round_border);

        option1Icon.setImageResource(R.drawable.round_border2);
        option2Icon.setImageResource(R.drawable.round_border2);
        option3Icon.setImageResource(R.drawable.round_border2);
        option4Icon.setImageResource(R.drawable.round_border2);
    }

    private void selectOption(RelativeLayout selectedOptionLayout, ImageView selectedOptionIcon){
        //Reset options to select new option
        resetOptions();

        selectedOptionIcon.setImageResource(R.drawable.check);
        selectedOptionLayout.setBackgroundResource(R.drawable.round_border2);
    }

}