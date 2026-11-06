package com.example.bizzfloww;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class ProductionFragment extends Fragment {

    // Argument keys for Task 2 Fragment Data Passing
    private static final String ARG_PROD_NAME = "arg_prod_name";
    private static final String ARG_PROD_ID = "arg_prod_id";
    private static final String ARG_PROD_QTY = "arg_prod_qty";

    private MaterialCardView cardReceivedData;
    private TextView tvReceivedDetails;
    private TextInputEditText etName, etQty, etWastage;
    private MaterialButton btnSave;
    private RecyclerView recyclerView;

    private ProductionAdapter adapter;
    private List<ProductionItem> productionList;

    public static ProductionFragment newInstance(String productName, String productId, int quantity) {
        ProductionFragment fragment = new ProductionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PROD_NAME, productName);
        args.putString(ARG_PROD_ID, productId);
        args.putInt(ARG_PROD_QTY, quantity);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_production, container, false);

        initViews(view);
        setupRecyclerView();
        checkPassedData(); // Task 2: Process data passed from InventoryFragment
        setupListeners();

        return view;
    }

    private void initViews(View view) {
        cardReceivedData = view.findViewById(R.id.card_received_data);
        tvReceivedDetails = view.findViewById(R.id.tv_received_details);
        etName = view.findViewById(R.id.et_frag_prod_name);
        etQty = view.findViewById(R.id.et_frag_prod_qty);
        etWastage = view.findViewById(R.id.et_frag_wastage_qty);
        btnSave = view.findViewById(R.id.btn_frag_save_production);
        recyclerView = view.findViewById(R.id.rv_frag_production);
    }

    private void setupRecyclerView() {
        productionList = new ArrayList<>();
        productionList.add(new ProductionItem("1", "Aluminum Housing Batch #4", 500, 5, "2025-02-23"));
        productionList.add(new ProductionItem("2", "Steel Shaft 20mm Batch #2", 300, 2, "2025-02-22"));
        productionList.add(new ProductionItem("3", "Copper Coil Assembly", 150, 0, "2025-02-21"));

        adapter = new ProductionAdapter(productionList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
    }

    private void checkPassedData() {
        // Task 2: Receive & display passed Bundle arguments
        if (getArguments() != null && getArguments().containsKey(ARG_PROD_NAME)) {
            String passedName = getArguments().getString(ARG_PROD_NAME, "");
            String passedId = getArguments().getString(ARG_PROD_ID, "");
            int passedQty = getArguments().getInt(ARG_PROD_QTY, 0);

            cardReceivedData.setVisibility(View.VISIBLE);
            tvReceivedDetails.setText("Product: " + passedName + " (ID: " + passedId + ") • Target Qty: " + passedQty);

            // Pre-fill fields automatically
            etName.setText(passedName);
            etQty.setText(String.valueOf(passedQty));
            etWastage.setText("0");

            Toast.makeText(requireContext(), "Received data from InventoryFragment for: " + passedName, Toast.LENGTH_LONG).show();
        }
    }

    private void setupListeners() {
        btnSave.setOnClickListener(v -> saveProduction());
    }

    private void saveProduction() {
        String name = etName.getText() != null ? etName.getText().toString().trim() : "";
        String qtyStr = etQty.getText() != null ? etQty.getText().toString().trim() : "";
        String wastageStr = etWastage.getText() != null ? etWastage.getText().toString().trim() : "";

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(qtyStr) || TextUtils.isEmpty(wastageStr)) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int qty = Integer.parseInt(qtyStr);
        int wastage = Integer.parseInt(wastageStr);

        ProductionItem newItem = new ProductionItem(
                String.valueOf(productionList.size() + 1),
                name,
                qty,
                wastage,
                "2025-02-23"
        );

        productionList.add(0, newItem);
        adapter.notifyItemInserted(0);
        recyclerView.scrollToPosition(0);

        etName.setText("");
        etQty.setText("");
        etWastage.setText("");

        Toast.makeText(requireContext(), "Production log saved successfully!", Toast.LENGTH_SHORT).show();
    }
}
