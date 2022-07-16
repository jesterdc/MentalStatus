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

public class Adapter_Anxiety extends RecyclerView.Adapter<Adapter_Anxiety.MyViewHolder> {

    Context context;
    ArrayList<ResultClass_Anxiety> resultArrayList;

    public Adapter_Anxiety(Context context, ArrayList<ResultClass_Anxiety> resultArrayList) {
        this.context = context;
        this.resultArrayList = resultArrayList;
    }

    @NonNull
    @Override
    public Adapter_Anxiety.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_result_depression,parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Anxiety.MyViewHolder holder, int position) {

        ResultClass_Anxiety resultList = resultArrayList.get(position);

        holder.date.setText(resultList.date);
        holder.score.setText(resultList.score+"/21");
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
