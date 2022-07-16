package com.example.mentalstatus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class Adapter_Depression extends RecyclerView.Adapter<Adapter_Depression.MyViewHolder> {

    Context context;
    ArrayList<ResultClass_Depression> resultArrayList;

    public Adapter_Depression(Context context, ArrayList<ResultClass_Depression> resultArrayList) {
        this.context = context;
        this.resultArrayList = resultArrayList;
    }

    @NonNull
    @Override
    public Adapter_Depression.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_result_depression,parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Depression.MyViewHolder holder, int position) {

        ResultClass_Depression resultList = resultArrayList.get(position);

        holder.date.setText(resultList.date);
        holder.score.setText(resultList.score+"/30");
        holder.result.setText(resultList.result);


    }

    @Override
    public int getItemCount() {
        return resultArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView date, score, result;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.dateTV);
            score = itemView.findViewById(R.id.scoreTV);
            result = itemView.findViewById(R.id.resultTV);
        }
    }
}
