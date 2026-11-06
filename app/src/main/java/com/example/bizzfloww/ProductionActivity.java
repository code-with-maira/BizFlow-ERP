package com.example.bizzfloww;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;
import java.util.Locale;

public class ProductionActivity extends AppCompatActivity {

    private TextInputEditText etProdName, etProductionQuantity, etWastageQuantity, etProductionDate;
    private MaterialButton btnSaveProduction;
    private ImageButton btnBack;
    private RecyclerView rvProductionList;

    private ProductionAdapter adapter;
    private List<ProductionItem> productionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production);

        initViews();
        setupRecyclerView();
        setupListeners();
        setDefaultDate();
    }

    private void initViews() {
        etProdName = findViewById(R.id.et_prod_name);
        etProductionQuantity = findViewById(R.id.et_production_quantity);
        etWastageQuantity = findViewById(R.id.et_wastage_quantity);
        etProductionDate = findViewById(R.id.et_production_date);
        btnSaveProduction = findViewById(R.id.btn_save_production);
        btnBack = findViewById(R.id.btn_back);
        rvProductionList = findViewById(R.id.rv_production_list);
    }

    private void setupRecyclerView() {
        productionList = new ArrayList<>();
        
        // Initial sample production log data
        productionList.add(new ProductionItem("1", "Aluminum Housing Batch #4", 500, 5, "2025-02-23"));
        productionList.add(new ProductionItem("2", "Steel Shaft 20mm Batch #2", 300, 2, "2025-02-22"));
        productionList.add(new ProductionItem("3", "Copper Coil Assembly", 150, 0, "2025-02-21"));

        adapter = new ProductionAdapter(productionList);
        rvProductionList.setLayoutManager(new LinearLayoutManager(this));
        rvProductionList.setAdapter(adapter);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Navigate back to Dashboard
            }
        });

        etProductionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btnSaveProduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProductionEntry();
            }
        });
    }

    private void setDefaultDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        etProductionDate.setText(String.format(Locale.US, "%04d-%02d-%02d", year, month, day));
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year1, int monthOfYear, int dayOfMonth) {
                        String selectedDate = String.format(Locale.US, "%04d-%02d-%02d", year1, monthOfYear + 1, dayOfMonth);
                        etProductionDate.setText(selectedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void saveProductionEntry() {
        String prodName = etProdName.getText() != null ? etProdName.getText().toString().trim() : "";
        String prodQtyStr = etProductionQuantity.getText() != null ? etProductionQuantity.getText().toString().trim() : "";
        String wastageQtyStr = etWastageQuantity.getText() != null ? etWastageQuantity.getText().toString().trim() : "";
        String dateStr = etProductionDate.getText() != null ? etProductionDate.getText().toString().trim() : "";

        if (TextUtils.isEmpty(prodName) || TextUtils.isEmpty(prodQtyStr) ||
                TextUtils.isEmpty(wastageQtyStr) || TextUtils.isEmpty(dateStr)) {
            Toast.makeText(this, R.string.err_all_fields_required, Toast.LENGTH_SHORT).show();
            return;
        }

        int prodQty, wastageQty;
        try {
            prodQty = Integer.parseInt(prodQtyStr);
            wastageQty = Integer.parseInt(wastageQtyStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers for quantity and wastage", Toast.LENGTH_SHORT).show();
            return;
        }

        String newId = String.valueOf(productionList.size() + 1);
        ProductionItem newItem = new ProductionItem(newId, prodName, prodQty, wastageQty, dateStr);
        productionList.add(0, newItem);
        adapter.notifyItemInserted(0);
        rvProductionList.scrollToPosition(0);

        // Reset inputs
        etProdName.setText("");
        etProductionQuantity.setText("");
        etWastageQuantity.setText("");
        setDefaultDate();

        Toast.makeText(this, R.string.msg_production_saved, Toast.LENGTH_SHORT).show();
    }
}
