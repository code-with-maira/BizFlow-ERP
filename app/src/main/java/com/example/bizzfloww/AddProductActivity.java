package com.example.bizzfloww;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddProductActivity extends AppCompatActivity {

    public static final String EXTRA_PROD_NAME = "extra_prod_name";
    public static final String EXTRA_PROD_ID = "extra_prod_id";
    public static final String EXTRA_PROD_QTY = "extra_prod_qty";
    public static final String EXTRA_PROD_CATEGORY = "extra_prod_category";

    private TextInputEditText etName, etId, etQty;
    private TextInputLayout tilName, tilId, tilQty;
    private Spinner spinnerCategory;
    private MaterialButton btnSubmit;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        initViews();
        setupSpinner();
        setupListeners();
    }

    private void initViews() {
        etName = findViewById(R.id.et_add_name);
        etId = findViewById(R.id.et_add_id);
        etQty = findViewById(R.id.et_add_qty);
        tilName = findViewById(R.id.til_add_name);
        tilId = findViewById(R.id.til_add_id);
        tilQty = findViewById(R.id.til_add_qty);
        spinnerCategory = findViewById(R.id.spinner_add_category);
        btnSubmit = findViewById(R.id.btn_submit_add_product);
        btnBack = findViewById(R.id.btn_add_prod_back);
    }

    private void setupSpinner() {
        String[] categories = new String[]{"Raw Material", "Finished Goods", "Packaging"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerCategory.setAdapter(adapter);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnSubmit.setOnClickListener(v -> submitProduct());
    }

    private void submitProduct() {
        String name = etName.getText() != null ? etName.getText().toString().trim() : "";
        String productId = etId.getText() != null ? etId.getText().toString().trim() : "";
        String qtyStr = etQty.getText() != null ? etQty.getText().toString().trim() : "";
        String category = spinnerCategory.getSelectedItem() != null ? spinnerCategory.getSelectedItem().toString() : "Raw Material";

        tilName.setError(null);
        tilId.setError(null);
        tilQty.setError(null);

        if (TextUtils.isEmpty(name)) {
            tilName.setError("Please enter product name");
            return;
        }

        if (TextUtils.isEmpty(productId)) {
            tilId.setError("Please enter product ID");
            return;
        }

        if (TextUtils.isEmpty(qtyStr)) {
            tilQty.setError("Please enter quantity");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(qtyStr);
        } catch (NumberFormatException e) {
            tilQty.setError("Please enter a valid integer");
            return;
        }

        // Return Data to InventoryFragment via Intent Result (Task 3)
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_PROD_NAME, name);
        resultIntent.putExtra(EXTRA_PROD_ID, productId);
        resultIntent.putExtra(EXTRA_PROD_QTY, quantity);
        resultIntent.putExtra(EXTRA_PROD_CATEGORY, category);

        setResult(RESULT_OK, resultIntent);
        Toast.makeText(this, "Product added! Returning to Inventory...", Toast.LENGTH_SHORT).show();
        finish();
    }
}
