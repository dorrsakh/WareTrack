package com.example.uhf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uhf.R;

public class ProductEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_entry);

        // --- Header Setup ---
        ImageView ivActionIcon = findViewById(R.id.iv_action_icon);
        TextView tvAppName = findViewById(R.id.tv_app_name);

        // Change icon to 'back' and set its click listener
        ivActionIcon.setImageResource(R.drawable.ic_back);
        ivActionIcon.setOnClickListener(v -> finish());

        // Set the header title
        tvAppName.setText("ثبت کالای جدید");

        // --- Button Setup ---
        Button btnScan = findViewById(R.id.btnScan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductEntryActivity.this, UHFMainActivity.class);
                intent.putExtra("AUTO_START_SCAN", true);
                intent.putExtra("POWER_SETTING", 1);
                startActivity(intent);
            }
        });
    }
}