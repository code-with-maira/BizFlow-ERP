package com.example.bizzfloww;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

public class DashboardFragment extends Fragment {

    private View containerDashboard;
    private ImageView ivLogo;
    private MaterialButton btnOpenInventory, btnOpenProduction, btnAddProductActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        containerDashboard = view.findViewById(R.id.container_dashboard);
        ivLogo = view.findViewById(R.id.iv_dashboard_logo);
        btnOpenInventory = view.findViewById(R.id.btn_dash_open_inventory);
        btnOpenProduction = view.findViewById(R.id.btn_dash_open_production);
        btnAddProductActivity = view.findViewById(R.id.btn_dash_add_product_activity);

        // Task 6: Apply Fade-In and Scale Animations
        Animation fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in);
        Animation scaleUp = AnimationUtils.loadAnimation(requireContext(), R.anim.scale_up);
        
        containerDashboard.startAnimation(fadeIn);
        ivLogo.startAnimation(scaleUp);

        btnOpenInventory.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new InventoryFragment(), R.id.nav_inventory);
            }
        });

        btnOpenProduction.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new ProductionFragment(), R.id.nav_production);
            }
        });

        btnAddProductActivity.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), AddProductActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
