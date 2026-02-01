package com.example.uhf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uhf.R;

import java.util.ArrayList;

public class NameProductActivity extends AppCompatActivity {

    private EditText etProductName;
    private Button btnSave, btnPrevious;
    private ArrayList<String> scannedTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_product);

        // --- Header Setup (Icon Only) ---
        ImageView ivActionIcon = findViewById(R.id.iv_action_icon);
        ivActionIcon.setImageResource(R.drawable.ic_back);
        ivActionIcon.setOnClickListener(v -> finish()); // Just go back

        // --- Views Setup ---
        etProductName = findViewById(R.id.etProductName);
        btnSave = findViewById(R.id.btnSave);
        btnPrevious = findViewById(R.id.btnPrevious);

        scannedTags = getIntent().getStringArrayListExtra("SCANNED_TAGS");
        if (scannedTags == null) {
            scannedTags = new ArrayList<>();
        }

        btnPrevious.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            String productName = etProductName.getText().toString().trim();
            Toast.makeText(this, "'" + productName + "' برای " + scannedTags.size() + " کالا ذخیره شد.", Toast.LENGTH_LONG).show();

            // Go back to the dashboard
            Intent intent = new Intent(NameProductActivity.this, DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Disable save button initially
        btnSave.setEnabled(false);

        etProductName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnSave.setEnabled(s.toString().trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}