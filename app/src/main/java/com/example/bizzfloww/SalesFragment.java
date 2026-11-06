package com.example.bizzfloww;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SalesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generic, container, false);

        ImageView icon = view.findViewById(R.id.iv_generic_icon);
        TextView title = view.findViewById(R.id.tv_generic_title);
        TextView desc = view.findViewById(R.id.tv_generic_description);

        icon.setImageResource(R.drawable.ic_sales);
        title.setText("Sales & Orders Management");
        desc.setText("Track wholesale customer orders, dispatches, and sales invoices.");

        return view;
    }
}
