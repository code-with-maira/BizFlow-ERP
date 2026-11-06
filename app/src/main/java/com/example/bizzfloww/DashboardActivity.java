package com.example.bizzfloww;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class DashboardActivity extends AppCompatActivity {

    private MaterialButton btnNavInventory, btnNavProduction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnNavInventory = findViewById(R.id.btn_nav_inventory);
        btnNavProduction = findViewById(R.id.btn_nav_production);

        btnNavInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Explicit Intent Navigation to InventoryActivity
                Intent intent = new Intent(DashboardActivity.this, InventoryActivity.class);
                startActivity(intent);
            }
        });

        btnNavProduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Explicit Intent Navigation to ProductionActivity
                Intent intent = new Intent(DashboardActivity.this, ProductionActivity.class);
                startActivity(intent);
            }
        });
    }
}
