package com.android.primaitech.siprima.Penjualan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.android.primaitech.siprima.Dashboard.Dashboard;
import com.android.primaitech.siprima.R;
import com.githang.stepview.StepView;

import java.util.Arrays;
import java.util.List;

public class Form_Pembayaran extends AppCompatActivity {
    StepView mStepView;
    Button next, prev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pembayaran);
        mStepView = (StepView) findViewById(R.id.step_view);
        List<String> steps = Arrays.asList(new String[]{"Data Pembeli", "Data Kavling", "Pembayaran", "Konfirmasi"});
        mStepView.setSteps(steps);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }
        int nextStep = mStepView.getCurrentStep() + 2;
        if (nextStep > mStepView.getStepCount()) {
            nextStep = 2;
        }
        mStepView.selectedStep(nextStep);
        next = (Button)findViewById(R.id.next);
        prev = (Button)findViewById(R.id.prev);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Form_Pembayaran.this, Form_Konfirmasi.class));
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Form_Pembayaran.this, Form_Data_Kavling.class));
            }
        });
    }
}
