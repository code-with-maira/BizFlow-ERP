package com.example.bizzfloww;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class InventoryActivity extends AppCompatActivity {

    private TextInputEditText etProductName, etProductId, etBarcodeNumber, etStockQuantity;
    private MaterialButton btnAddInventory;
    private ImageButton btnBack;
    private RecyclerView rvInventoryList;

    private InventoryAdapter adapter;
    private List<InventoryItem> inventoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        initViews();
        setupRecyclerView();
        setupListeners();
    }

    private void initViews() {
        etProductName = findViewById(R.id.et_product_name);
        etProductId = findViewById(R.id.et_product_id);
        etBarcodeNumber = findViewById(R.id.et_barcode_number);
        etStockQuantity = findViewById(R.id.et_stock_quantity);
        btnAddInventory = findViewById(R.id.btn_add_inventory);
        btnBack = findViewById(R.id.btn_back);
        rvInventoryList = findViewById(R.id.rv_inventory_list);
    }

    private void setupRecyclerView() {
        inventoryList = new ArrayList<>();
        
        // Initial sample data for demonstration
        inventoryList.add(new InventoryItem("1", "Steel Bearing 50mm", "PRD-1021", "890123456789", 250));
        inventoryList.add(new InventoryItem("2", "Hydraulic Pump Motor", "PRD-4482", "890987654321", 45));
        inventoryList.add(new InventoryItem("3", "Industrial Rubber Seal", "PRD-3310", "890555444333", 800));

        adapter = new InventoryAdapter(inventoryList);
        rvInventoryList.setLayoutManager(new LinearLayoutManager(this));
        rvInventoryList.setAdapter(adapter);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Back to Dashboard
            }
        });

        btnAddInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInventoryItem();
            }
        });
    }

    private void addInventoryItem() {
        String name = etProductName.getText() != null ? etProductName.getText().toString().trim() : "";
        String productId = etProductId.getText() != null ? etProductId.getText().toString().trim() : "";
        String barcode = etBarcodeNumber.getText() != null ? etBarcodeNumber.getText().toString().trim() : "";
        String quantityStr = etStockQuantity.getText() != null ? etStockQuantity.getText().toString().trim() : "";

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(productId) ||
                TextUtils.isEmpty(barcode) || TextUtils.isEmpty(quantityStr)) {
            Toast.makeText(this, R.string.err_all_fields_required, Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid stock quantity number", Toast.LENGTH_SHORT).show();
            return;
        }

        String newId = String.valueOf(inventoryList.size() + 1);
        InventoryItem newItem = new InventoryItem(newId, name, productId, barcode, quantity);
        inventoryList.add(0, newItem);
        adapter.notifyItemInserted(0);
        rvInventoryList.scrollToPosition(0);

        // Clear Form Fields
        etProductName.setText("");
        etProductId.setText("");
        etBarcodeNumber.setText("");
        etStockQuantity.setText("");

        Toast.makeText(this, R.string.msg_inventory_added, Toast.LENGTH_SHORT).show();
    }
}
