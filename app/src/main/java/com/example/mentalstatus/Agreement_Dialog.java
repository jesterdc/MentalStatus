package com.example.mentalstatus;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

public class Agreement_Dialog extends Dialog {
    public Agreement_Dialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agreement_dialog);

        final AppCompatButton agreeBtn = findViewById(R.id.agreeBtn);
        final TextView agreementTV = findViewById(R.id.agreementTV);



        setAgreement(agreementTV, "This test is for adults only. By clicking \"I Agree\" below, you acknowledge that this is not a diagnostic instrument and is only to be used by you if you are 18 years or older. Furthermore you agree that this application is information purposes only and is not intended to replace a consultation with your doctor or a mental health professional.");

        agreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void setAgreement(TextView agreementTV, String agreementPoint){
            agreementTV.setText(agreementPoint);
    }

}
