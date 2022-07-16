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

public class Adapter_PanicDisorder extends RecyclerView.Adapter<Adapter_PanicDisorder.MyViewHolder> {

    Context context;
    ArrayList<ResultClass_PanicDisorder> resultArrayList;

    public Adapter_PanicDisorder(Context context, ArrayList<ResultClass_PanicDisorder> resultArrayList) {
        this.context = context;
        this.resultArrayList = resultArrayList;
    }

    @NonNull
    @Override
    public Adapter_PanicDisorder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_result_depression,parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_PanicDisorder.MyViewHolder holder, int position) {

        ResultClass_PanicDisorder resultList = resultArrayList.get(position);

        holder.date.setText(resultList.date);
        holder.score.setText(resultList.score+"/28");
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
