package com.example.mentalstatus;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class HomeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        CardView depressionTest = (CardView) view.findViewById(R.id.DepressionTest);
        depressionTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Test_Depression.class);
                intent.putExtra("some", "some data");
                startActivity(intent);
            }
        });

        CardView anxietyTest = (CardView) view.findViewById(R.id.AnxietyTest);
        anxietyTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Test_Anxiety.class);
                intent.putExtra("some", "some data");
                startActivity(intent);
            }
        });

        CardView stressTest = (CardView) view.findViewById(R.id.StressTest);
        stressTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Test_Stress.class);
                intent.putExtra("some", "some data");
                startActivity(intent);
            }
        });

        CardView sdTest = (CardView) view.findViewById(R.id.SleepDisorderTest);
        sdTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Test_SleepDisorder.class);
                intent.putExtra("some", "some data");
                startActivity(intent);
            }
        });

        CardView pdTest = (CardView) view.findViewById(R.id.PanicDisorderTest);
        pdTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Test_PanicDisorder.class);
                intent.putExtra("some", "some data");
                startActivity(intent);
            }
        });

        CardView edTest = (CardView) view.findViewById(R.id.EatingDisorderTest);
        edTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Test_EatingDisorder.class);
                intent.putExtra("some", "some data");
                startActivity(intent);
            }
        });

        return view;
    }
}