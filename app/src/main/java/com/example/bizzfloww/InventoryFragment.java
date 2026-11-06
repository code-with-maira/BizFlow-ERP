package com.example.bizzfloww;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class InventoryFragment extends Fragment implements ProductAdapter.OnProductClickListener {

    private SearchView searchView;
    private Spinner spinnerFilter;
    private ImageButton btnLaunchAddActivity;
    private MaterialButton btnPassToProduction;
    private RecyclerView recyclerView;

    private ProductAdapter adapter;
    private List<Product> productList;

    // Task 3: ActivityResultLauncher to handle data returned from AddProductActivity
    private final ActivityResultLauncher<Intent> addProductLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    String name = data.getStringExtra(AddProductActivity.EXTRA_PROD_NAME);
                    String id = data.getStringExtra(AddProductActivity.EXTRA_PROD_ID);
                    int qty = data.getIntExtra(AddProductActivity.EXTRA_PROD_QTY, 0);
                    String category = data.getStringExtra(AddProductActivity.EXTRA_PROD_CATEGORY);

                    // Add new product to list and notify adapter (Task 3)
                    Product newProduct = new Product(
                            String.valueOf(productList.size() + 1),
                            name,
                            id,
                            "BC-" + System.currentTimeMillis() % 10000,
                            qty,
                            category,
                            false
                    );
                    productList.add(0, newProduct);
                    adapter.updateList(productList);
                    recyclerView.scrollToPosition(0);

                    Toast.makeText(requireContext(), "Received new product from AddProductActivity: " + name, Toast.LENGTH_LONG).show();
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);

        initViews(view);
        setupData();
        setupSpinnerFilter();
        setupSearchView();
        setupListeners();

        return view;
    }

    private void initViews(View view) {
        searchView = view.findViewById(R.id.sv_inventory);
        spinnerFilter = view.findViewById(R.id.spinner_filter_category);
        btnLaunchAddActivity = view.findViewById(R.id.btn_launch_add_activity);
        btnPassToProduction = view.findViewById(R.id.btn_pass_to_production);
        recyclerView = view.findViewById(R.id.rv_inventory_fragment);
    }

    private void setupData() {
        productList = new ArrayList<>();
        productList.add(new Product("1", "Steel Rod 10mm", "PRD-1021", "890123456789", 250, "Raw Material", true));
        productList.add(new Product("2", "Hydraulic Pump Assembly", "PRD-4482", "890987654321", 45, "Finished Goods", false));
        productList.add(new Product("3", "Corrugated Shipping Box", "PRD-8800", "890777666555", 1200, "Packaging", true));
        productList.add(new Product("4", "Aluminum Plate 5mm", "PRD-3310", "890555444333", 180, "Raw Material", false));
        productList.add(new Product("5", "Industrial Lubricant 5L", "PRD-9912", "890222111000", 60, "Raw Material", true));

        adapter = new ProductAdapter(productList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
    }

    private void setupSpinnerFilter() {
        // Task 5 Component 6: Spinner
        String[] filterOptions = new String[]{"All Categories", "Raw Material", "Finished Goods", "Packaging"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, filterOptions);
        spinnerFilter.setAdapter(spinnerAdapter);

        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = filterOptions[position];
                if (selectedCategory.equals("All Categories")) {
                    adapter.updateList(productList);
                } else {
                    List<Product> filtered = new ArrayList<>();
                    for (Product p : productList) {
                        if (p.getCategory().equalsIgnoreCase(selectedCategory)) {
                            filtered.add(p);
                        }
                    }
                    adapter.updateList(filtered);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupSearchView() {
        // Task 5 Component 1: SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void setupListeners() {
        // Task 5 Component 4: ImageButton launching AddProductActivity (Task 3)
        btnLaunchAddActivity.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AddProductActivity.class);
            addProductLauncher.launch(intent);
        });

        // Task 2: Fragment-to-Fragment Data Passing
        btnPassToProduction.setOnClickListener(v -> {
            if (!productList.isEmpty()) {
                Product selected = productList.get(0); // Take first stock item to send to production
                ProductionFragment productionFragment = ProductionFragment.newInstance(
                        selected.getName(),
                        selected.getProductId(),
                        selected.getQuantity()
                );

                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToFragment(productionFragment, R.id.nav_production);
                    Toast.makeText(requireContext(), "Passed [" + selected.getName() + "] data to ProductionFragment!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Task 5 Component 3: CustomAlertDialog (Product Details & Edit)
    @Override
    public void onItemClick(Product product, int position) {
        showCustomDetailsDialog(product, position);
    }

    // Task 5 Component 2: AlertDialog (Delete Confirmation)
    @Override
    public void onDeleteClick(Product product, int position) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Product Confirmation")
                .setMessage("Are you sure you want to delete product '" + product.getName() + "' (ID: " + product.getProductId() + ") from inventory?")
                .setIcon(R.drawable.ic_alert)
                .setPositiveButton("Delete", (dialog, which) -> {
                    productList.remove(product);
                    adapter.updateList(productList);
                    Toast.makeText(requireContext(), "Product deleted successfully!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // Task 5 Component 5: CheckBox Listener
    @Override
    public void onVerifyChanged(Product product, boolean isChecked) {
        String status = isChecked ? "Verified" : "Unverified";
        Toast.makeText(requireContext(), product.getName() + " marked as " + status, Toast.LENGTH_SHORT).show();
    }

    private void showCustomDetailsDialog(Product product, int position) {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_product_details, null);

        com.google.android.material.textfield.TextInputEditText etName = dialogView.findViewById(R.id.dialog_et_name);
        com.google.android.material.textfield.TextInputEditText etId = dialogView.findViewById(R.id.dialog_et_id);
        com.google.android.material.textfield.TextInputEditText etQty = dialogView.findViewById(R.id.dialog_et_qty);
        Spinner spinnerCat = dialogView.findViewById(R.id.dialog_spinner_category);
        MaterialButton btnCancel = dialogView.findViewById(R.id.dialog_btn_cancel);
        MaterialButton btnSave = dialogView.findViewById(R.id.dialog_btn_save);

        String[] catArray = new String[]{"Raw Material", "Finished Goods", "Packaging"};
        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, catArray);
        spinnerCat.setAdapter(catAdapter);

        etName.setText(product.getName());
        etId.setText(product.getProductId());
        etQty.setText(String.valueOf(product.getQuantity()));

        for (int i = 0; i < catArray.length; i++) {
            if (catArray[i].equalsIgnoreCase(product.getCategory())) {
                spinnerCat.setSelection(i);
                break;
            }
        }

        AlertDialog customDialog = new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create();

        btnCancel.setOnClickListener(v -> customDialog.dismiss());

        btnSave.setOnClickListener(v -> {
            String newName = etName.getText() != null ? etName.getText().toString().trim() : "";
            String newQtyStr = etQty.getText() != null ? etQty.getText().toString().trim() : "";
            String newCat = spinnerCat.getSelectedItem() != null ? spinnerCat.getSelectedItem().toString() : product.getCategory();

            if (!TextUtils.isEmpty(newName) && !TextUtils.isEmpty(newQtyStr)) {
                product.setName(newName);
                product.setQuantity(Integer.parseInt(newQtyStr));
                product.setCategory(newCat);
                adapter.updateList(productList);
                Toast.makeText(requireContext(), "Product details updated!", Toast.LENGTH_SHORT).show();
                customDialog.dismiss();
            }
        });

        customDialog.show();
    }
}
