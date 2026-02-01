package com.example.uhf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.uhf.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // --- Header Setup ---
        TextView tvAppName = findViewById(R.id.tv_app_name);
        tvAppName.setText("انباردار");

        // --- Footer Setup ---
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    // Already on the dashboard, do nothing
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    // TODO: Navigate to Profile screen
                    return true;
                } else if (itemId == R.id.navigation_report) {
                    // TODO: Navigate to Report screen
                    return true;
                }
                return false;
            }
        });

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