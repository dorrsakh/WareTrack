package com.example.uhf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.uhf.R;
import com.google.android.material.card.MaterialCardView;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // --- Header Setup ---
        TextView tvAppName = findViewById(R.id.tv_app_name);
        tvAppName.setText("انباردار");

        // --- Button Setup ---
//        Button btnSubmitEntry = findViewById(R.id.btnSubmitEntry);
//        btnSubmitEntry.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DashboardActivity.this, ProductEntryActivity.class);
//                startActivity(intent);
//            }
//        });
        // --- Action Cards ---
        MaterialCardView cardSubmitEntry = findViewById(R.id.cardSubmitEntry);
        MaterialCardView cardSubmitExit = findViewById(R.id.cardSubmitExit);
        MaterialCardView cardInventoryCount = findViewById(R.id.cardInventoryCount);

        cardSubmitEntry.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ProductEntryActivity.class);
            startActivity(intent);
        });

        cardSubmitExit.setOnClickListener(v -> {
            // TODO: پیاده‌سازی ثبت خروج
        });

        cardInventoryCount.setOnClickListener(v -> {
            // TODO: پیاده‌سازی انبارگردانی
        });

        // Other buttons can be set up here if needed
    }
}