package com.example.uhf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.uhf.R;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // --- Header Setup ---
        TextView tvAppName = findViewById(R.id.tv_app_name);
        tvAppName.setText("انباردار");

        // --- Button Setup ---
        Button btnSubmitEntry = findViewById(R.id.btnSubmitEntry);
        btnSubmitEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ProductEntryActivity.class);
                startActivity(intent);
            }
        });

        // Other buttons can be set up here if needed
    }
}