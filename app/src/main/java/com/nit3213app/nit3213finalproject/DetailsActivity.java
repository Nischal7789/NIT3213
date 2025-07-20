package com.nit3213app.nit3213finalproject;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView techniqueText = findViewById(R.id.detailTechnique);
        TextView equipmentText = findViewById(R.id.detailEquipment);
        TextView subjectText = findViewById(R.id.detailSubject);
        TextView photographerText = findViewById(R.id.detailPhotographer);
        TextView yearText = findViewById(R.id.detailYear);
        TextView descriptionText = findViewById(R.id.detailDescription);

        techniqueText.setText(getIntent().getStringExtra("technique"));
        equipmentText.setText(getIntent().getStringExtra("equipment"));
        subjectText.setText(getIntent().getStringExtra("subject"));
        photographerText.setText(getIntent().getStringExtra("photographer"));
        yearText.setText(String.valueOf(getIntent().getIntExtra("year", 0)));
        descriptionText.setText(getIntent().getStringExtra("description"));
    }
}