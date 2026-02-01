package com.example.uhf.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.uhf.R;
import com.google.android.material.chip.Chip;

public class CustomChip extends Chip {

    public CustomChip(Context context) {
        this(context, null);
    }

    public CustomChip(Context context, AttributeSet attrs) {
        // Use the custom style by default
        this(context, attrs, R.attr.chipStyle);
    }

    public CustomChip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // You can add more custom methods here in the future if needed
}
