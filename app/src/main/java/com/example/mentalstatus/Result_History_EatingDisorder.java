package com.example.mentalstatus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class Result_History_EatingDisorder extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ResultClass_EatingDisorder> resultList;
    Adapter_EatingDisorder myAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    FirebaseAuth fAuth;
    ImageView backButton;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_history_eating_disorder);


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data..");
        progressDialog.show();

        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        resultList = new ArrayList<ResultClass_EatingDisorder>();
        myAdapter = new Adapter_EatingDisorder(Result_History_EatingDisorder.this, resultList);
        backButton = findViewById(R.id.backBtn);
        recyclerView.setAdapter(myAdapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        EventChaneListener();
    }

    private void EventChaneListener(){
        db.collection("users/"+userID+"/eatingDisorderResult").orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null){
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType()==DocumentChange.Type.ADDED){
                                resultList.add(dc.getDocument().toObject(ResultClass_EatingDisorder.class));
                            }

                            myAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });
    }
}