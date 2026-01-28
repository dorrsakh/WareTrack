package com.example.uhf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.uhf.R;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvTotalBalance;
    private Button btnSubmitEntry, btnSubmitExit, btnInventoryCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvTotalBalance = findViewById(R.id.tvTotalBalance);
        btnSubmitEntry = findViewById(R.id.btnSubmitEntry);
        btnSubmitExit = findViewById(R.id.btnSubmitExit);
        btnInventoryCount = findViewById(R.id.btnInventoryCount);

        // Set the total balance (for now, a static value)
        tvTotalBalance.setText("موجودی کل: 1,250");

        btnSubmitEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, UHFMainActivity.class);
                startActivity(intent);
            }
        });

        btnSubmitExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Functionality for submitting exit will be implemented later
            }
        });

        btnInventoryCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Functionality for inventory count will be implemented later
            }
        });
    }
}